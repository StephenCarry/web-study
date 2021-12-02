package com.yaoo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(public * com.yaoo.serivce.UserService.*(..))")
    public void check() {
        System.err.println("[Before]do check...");
    }

    @Around("execution(public * com.yaoo.serivce.UserService.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        System.err.println("[Around]start "+pjp.getSignature());
        Object resultVal = pjp.proceed();
        System.err.println("[Around]end "+pjp.getSignature());
        return resultVal;
    }

    @Around("@annotation(Logging)")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        System.err.println("[Around]log "+pjp.getSignature());
        Object resultVal = pjp.proceed();
        System.err.println("[Around]log "+pjp.getSignature());
        return resultVal;
    }
}
