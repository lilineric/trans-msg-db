package com.cellulam.trans.db.spring.aspect;

import com.cellulam.trans.msg.db.core.aspect.interceptor.TransMsgProducerInterceptor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;

/**
 * producer aspect
 * should after before {@link org.springframework.transaction.interceptor.TransactionInterceptor}
 * Inserting a transaction message table must be placed in a business transaction, which should be an atomic operation.
 * @author eric.li
 * @date 2022-06-10 15:57
 */
@Aspect
public class SpringTransMsgProducerAspect extends TransMsgProducerInterceptor implements Ordered {
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 30;
    }
}
