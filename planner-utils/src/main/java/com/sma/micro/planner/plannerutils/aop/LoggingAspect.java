package com.sma.micro.planner.plannerutils.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.sma.micro.planner.todo.controller..*(..))")
    public Object controllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        var methodSignature = (MethodSignature) joinPoint.getSignature();
        var className = methodSignature.getDeclaringType().getSimpleName();
        var methodName = methodSignature.getName();

        log.info("---------- Executing {}.{}() --------------", className, methodName);

        var watch = new StopWatch();
        watch.start();
        var result =  joinPoint.proceed();
        watch.stop();

        log.info("Execution time of {}.{}() is {} ms", className, methodName, watch.getTotalTimeMillis());

        return result;
    }
}
