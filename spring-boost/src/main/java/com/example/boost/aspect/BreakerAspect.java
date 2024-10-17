package com.example.boost.aspect;

import com.example.boost.circuitbreaker.GoogleBreaker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BreakerAspect {

    private static final String POINTCUT = "execution(public * com.example.boost.action.*.*(..))";

    private static final GoogleBreaker breaker = new GoogleBreaker();

    @Around(value = POINTCUT)
    public void autoBreaker(ProceedingJoinPoint point) {
        breaker.doRequest("test", point);
    }
}
