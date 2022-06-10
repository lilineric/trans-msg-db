package com.cellulam.trans.msg.db.core.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * producer interceptor
 *
 * @author eric.li
 * @date 2022-06-09 21:53
 */
@Aspect
public class TransMsgProducerInterceptor {
    private Logger log = LoggerFactory.getLogger(TransMsgProducerInterceptor.class);

    @Pointcut("@annotation(com.cellulam.trans.msg.db.facade.anotation.TransMsgProducer)")
    public void transMsgProducerPointcut() {
    }

    @Around("transMsgProducerPointcut()")
    public Object interceptTransMsgProducer(ProceedingJoinPoint pjp) throws Throwable {
        log.info("interceptTransMsgProducer");
        return pjp.proceed();
    }
}
