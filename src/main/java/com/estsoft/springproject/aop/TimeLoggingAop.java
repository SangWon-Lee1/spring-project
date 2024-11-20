package com.estsoft.springproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeLoggingAop {
    // 특정 method() 호출했을때, method의 수행 시간 측정
    @Around("execution(* com.estsoft.springproject.book..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimeMs = System.currentTimeMillis();
        log.info("START: {}", joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finishTimeMs = System.currentTimeMillis();
            long timeMs = finishTimeMs - startTimeMs;
            log.info("END: {} {}ms", joinPoint, timeMs);
        }
    }
}
