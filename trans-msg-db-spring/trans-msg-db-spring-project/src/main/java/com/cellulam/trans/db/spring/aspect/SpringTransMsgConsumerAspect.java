package com.cellulam.trans.db.spring.aspect;

import com.cellulam.trans.msg.db.core.aspect.interceptor.TransMsgConsumerInterceptor;
import com.cellulam.trans.msg.db.core.aspect.interceptor.TransMsgProducerInterceptor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;

/**
 * consumer aspect
 * should execute before {@link org.springframework.transaction.interceptor.TransactionInterceptor}
 * It is not necessary to put the consumer push ACK message in business transaction
 * because cannot let the failure of the push ACK message cause the business transaction to fail.
 * If the ACK message push fails, Recover will retry.
 * @author eric.li
 * @date 2022-06-10 15:57
 */
@Aspect
public class SpringTransMsgConsumerAspect extends TransMsgConsumerInterceptor implements Ordered {
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}
