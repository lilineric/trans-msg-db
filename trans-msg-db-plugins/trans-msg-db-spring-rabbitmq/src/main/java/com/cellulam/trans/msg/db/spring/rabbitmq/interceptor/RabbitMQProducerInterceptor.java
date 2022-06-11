package com.cellulam.trans.msg.db.spring.rabbitmq.interceptor;

import com.cellulam.msg.db.interceptor.TransMsgProducerMethodJoinPoint;
import com.cellulam.msg.db.interceptor.spi.TransMsgProducerInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * trans producer interceptor for RabbitMQ
 *
 * @author eric.li
 * @date 2022-06-11 00:32
 */
@Aspect
public class RabbitMQProducerInterceptor implements TransMsgProducerInterceptor {
    @Override
    @Pointcut("execution(* java.math.BigDecimal.add(..))")
    public void transMsgProducerPointcut() {
    }

    @Override
    @Around("transMsgProducerPointcut()")
    public Object interceptTransMsgProducer(ProceedingJoinPoint pjp) throws Throwable {
        return new TransMsgProducerMethodJoinPoint(pjp).interceptTransMethod();
    }

    @Override
    public String getType() {
        return "rabbitMQ";
    }
}
