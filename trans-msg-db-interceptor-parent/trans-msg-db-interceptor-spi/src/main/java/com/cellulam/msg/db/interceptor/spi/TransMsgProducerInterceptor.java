package com.cellulam.msg.db.interceptor.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author eric.li
 * @date 2022-06-11 00:01
 */
@SingletonSPI
public interface TransMsgProducerInterceptor {
    void transMsgProducerPointcut();

    Object interceptTransMsgProducer(ProceedingJoinPoint pjp) throws Throwable;

    String getType();
}
