package com.cellulam.msg.db.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author eric.li
 * @date 2022-06-10 22:46
 */
public abstract class AbstractTransMsgMethodJoinPoint {
    private Logger logger = LoggerFactory.getLogger(AbstractTransMsgMethodJoinPoint.class);

    private ProceedingJoinPoint pjp;

    public AbstractTransMsgMethodJoinPoint(ProceedingJoinPoint pjp) {
        this.pjp = pjp;
    }

    public Object interceptTransMethod() throws Throwable {
        logger.info("intercept trans class: {}, method: {}",
                this.getClass().getName(),
                this.pjp.getSignature().getName());
        return this.pjp.proceed();
    }
}
