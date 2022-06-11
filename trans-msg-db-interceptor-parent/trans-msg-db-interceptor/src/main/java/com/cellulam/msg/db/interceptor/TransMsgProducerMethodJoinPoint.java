package com.cellulam.msg.db.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author eric.li
 * @date 2022-06-10 22:43
 */
public class TransMsgProducerMethodJoinPoint extends AbstractTransMsgMethodJoinPoint{
    public TransMsgProducerMethodJoinPoint(ProceedingJoinPoint pjp) {
        super(pjp);
    }
}
