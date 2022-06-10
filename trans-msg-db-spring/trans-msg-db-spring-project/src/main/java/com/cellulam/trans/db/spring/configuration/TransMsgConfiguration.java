package com.cellulam.trans.db.spring.configuration;

import com.cellulam.trans.db.spring.aspect.SpringTransMsgConsumerAspect;
import com.cellulam.trans.db.spring.aspect.SpringTransMsgProducerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * trans configuration
 * Set the order for spring transaction management.
 * It should be executed after {@link SpringTransMsgConsumerAspect} and before {@link SpringTransMsgProducerAspect}
 * @author eric.li
 * @date 2022-06-10 16:12
 */
@Configuration
@EnableTransactionManagement(order = Ordered.HIGHEST_PRECEDENCE + 20)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TransMsgConfiguration {
    @Bean
    public SpringTransMsgProducerAspect transMsgProducerAspect() {
        return new SpringTransMsgProducerAspect();
    }

    @Bean
    public SpringTransMsgConsumerAspect transMsgConsumerAspect() {
        return new SpringTransMsgConsumerAspect();
    }
}
