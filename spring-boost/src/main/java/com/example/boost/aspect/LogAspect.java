package com.example.boost.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    public static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static final String POINTCUT = "execution(public * com.example.boost.action.*.*(..))";

    @Around(value = POINTCUT)
    public Object logging(ProceedingJoinPoint point) {
        logger.info("before");
        Object object = null;
        try {
            String name = Thread.currentThread().getName();
//            if (Integer.parseInt(name.substring(19)) > 5) threadLocal.set(name);
            threadLocal.set(name);
            object = point.proceed();
            logger.info("process success");
        } catch (Throwable throwable) {
            logger.info("e.message:" + throwable.getMessage());
            throwable.printStackTrace();
        } finally {
            threadLocal.remove();
        }
        logger.info("after");
        return object;
    }
}
