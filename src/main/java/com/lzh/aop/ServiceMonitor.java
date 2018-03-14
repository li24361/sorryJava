package com.lzh.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: lizhihao
 * @date: 2018/3/14 16:06
 */
@Aspect
@Component
public class ServiceMonitor {

    private Logger logger = LoggerFactory.getLogger(ServiceMonitor.class);


    @Around("execution(* com.lzh..*Controller.*(..))")
    public Object logServiceAccess(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();
        String className = pjp.getTarget().getClass().getName();
        String fullMethodName = className + "." + pjp.getSignature().getName();
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            logger.error(fullMethodName + "执行出错,详情:", e);
        }
        long end = System.currentTimeMillis();
        long elapsedMilliseconds = end - start;
        logger.info(fullMethodName + "执行耗时:" + elapsedMilliseconds + " 毫秒");
        return result;
    }

}
