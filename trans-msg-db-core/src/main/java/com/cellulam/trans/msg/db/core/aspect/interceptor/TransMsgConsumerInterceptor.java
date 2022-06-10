package com.cellulam.trans.msg.db.core.aspect.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * consumer interceptor
 *
 * @author eric.li
 * @date 2022-06-09 21:53
 */
@Aspect
public abstract class TransMsgConsumerInterceptor {
    private Logger log = LoggerFactory.getLogger(TransMsgConsumerInterceptor.class);

    @Pointcut("@annotation(com.cellulam.trans.msg.db.facade.anotation.TransMsgConsumer)")
    public void transMsgConsumerPointcut() {
    }

    @Around("transMsgConsumerPointcut()")
    public Object interceptTransMsgConsumer(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        return result;
    }
}
