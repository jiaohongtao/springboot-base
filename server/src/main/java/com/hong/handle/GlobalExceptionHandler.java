package com.hong.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;

/**
 * controller全局异常处理
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020/10/24 15:15
 */
@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(ValidationException exception) {
        logger.error("请求异常：" + exception.getMessage());
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;

            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                //打印验证不通过的信息
                logger.error("请求异常：" + item.getMessage());
            }
        }
        return "请求异常： " + exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public String bindException(Exception e) {
        if (e instanceof BindException) {
            BindException exs = (BindException) e;
            List<FieldError> fieldErrors = exs.getFieldErrors();
            for (FieldError item : fieldErrors) {
                logger.error("请求异常：" + item.getDefaultMessage());
            }
        }
        logger.error("数据绑定异常：" + e.getMessage());
        return "数据绑定异常";
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String defaultException(Exception e) {
        logger.error("请求异常：" + e.getMessage());
        return "请求异常 " + e.getMessage();
    }

}
