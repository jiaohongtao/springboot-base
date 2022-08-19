package com.hong.config;

import com.hong.bean.Result;
import com.hong.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/08/19
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BusinessException.class)
    public Result exceptionHandler(BusinessException e) {
        return Result.builder().success(false)
                .message(e.getMessage())
                .build();
    }
}
