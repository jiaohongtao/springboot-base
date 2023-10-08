package com.hong.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.hong.annotation.Log;
import com.hong.enums.JhtService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Log.class 拦截
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/08/30
 */
@Slf4j
@Aspect
@Component
public class LogInterceptor {

    @Pointcut("@annotation(com.hong.annotation.Log)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Long executeTime = System.currentTimeMillis() - beginTime; // 执行时长(毫秒)
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        String interfaceCode = method.getName();// 接口名称

        String interfaceParams = JSONObject.toJSONString(joinPoint.getArgs()); // 接口入参
        Object object = joinPoint.proceed(joinPoint.getArgs());
        String result = JSONObject.toJSONString(object);// 接口返回结果

        Method targetMethod = method.getMethod();
        Log logger = targetMethod.getAnnotation(Log.class); // 从接口注解中获取注解信息
        JhtService module = logger.module();// module
        String target = logger.target(); // 目标
        String operation = logger.operation(); // 操作

        // 建一张表，调用持久化服务存储日志，上述写的信息都可以存储到表中
        log.info("执行时长【{}】，接口名称【{}】，接口入参【{}】，接口返回【{}】",
                executeTime, interfaceCode, interfaceParams, result);
        log.info("执行module【{}】，执行目标【{}】，操作【{}】",
                module.getValue(), target, operation);

        return object;
    }

    @AfterThrowing(value = "pointCut()", throwing = "runtimeException")
    public void afterThrowingAdvice(JoinPoint pj, RuntimeException runtimeException) {
        // 接口调用后异常处理
        log.error("接口调用异常：【{}】，异常为【{}】", pj, runtimeException);
    }
}
