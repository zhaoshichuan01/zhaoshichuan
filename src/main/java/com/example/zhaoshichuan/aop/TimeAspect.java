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

    //@Around("execution(* com.example.zhaoshichuan.service.*.*(..))") //aop切入点表达式
    @Around("@annotation(com.example.zhaoshichuan.aop.MyLog)") //注解形式切入点表达式，只匹配MyLog自定义注解标注的方法
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable{
        // 获取目标方法的类名
        String className = joinPoint.getSignature().getDeclaringTypeName();
         // 获取目标方法的方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取目标方法运行时传入的参数
        Object[] args = joinPoint.getArgs();

        log.info("className:{}, methodName:{}, args:{}", className, methodName, args);

        long startTime = System.currentTimeMillis();
        // 获取目标方法返回值
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info(joinPoint.getSignature() + ", 执行时间："+(endTime-startTime));
        return result;
    }

}
