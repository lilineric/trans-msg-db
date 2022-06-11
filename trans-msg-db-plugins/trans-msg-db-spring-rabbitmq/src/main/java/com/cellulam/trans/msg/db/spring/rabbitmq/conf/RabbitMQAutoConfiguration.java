package com.cellulam.trans.msg.db.spring.rabbitmq.conf;

import com.cellulam.trans.msg.db.spring.rabbitmq.interceptor.RabbitMQProducerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author eric.li
 * @date 2022-06-11 01:59
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class RabbitMQAutoConfiguration {
//    @Bean
//    public RabbitMQProducerInterceptor rabbitMQProducerInterceptor() {
//        return new RabbitMQProducerInterceptor();
//    }
}
