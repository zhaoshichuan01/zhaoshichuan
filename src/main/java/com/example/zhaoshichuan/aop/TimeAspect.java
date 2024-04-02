package com.example.zhaoshichuan.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect //aop类
@Slf4j
public class TimeAspect {

    @Around("execution(* com.example.zhaoshichuan.service.*.*(..))") //aop切入点表达式
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable{

        long startTime = System.currentTimeMillis();
        Object result =  joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info(joinPoint.getSignature() + ", 执行时间："+(endTime-startTime));
        return result;
    }

}
