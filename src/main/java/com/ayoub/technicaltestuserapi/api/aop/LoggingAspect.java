package com.ayoub.technicaltestuserapi.api.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("within(com.ayoub.technicaltestuserapi.api..*)")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        log.info("IN  {} args={}", pjp.getSignature().toShortString(), pjp.getArgs());

        try {
            Object result = pjp.proceed();
            long ms = System.currentTimeMillis() - start;
            log.info("OUT {} result={} ({} ms)", pjp.getSignature().toShortString(), result, ms);
            return result;
        } catch (Exception e) {
            long ms = System.currentTimeMillis() - start;
            log.error("ERR {} ({} ms) - {}", pjp.getSignature().toShortString(), ms, e.getMessage());
            throw e;
        }
    }
}
