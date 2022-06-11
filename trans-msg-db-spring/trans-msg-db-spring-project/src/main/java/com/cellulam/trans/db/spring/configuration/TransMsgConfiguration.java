package com.cellulam.trans.db.spring.configuration;

import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.conf.TransMsgInitializer;
import com.cellulam.trans.msg.db.core.message.TransMessageSender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * trans configuration
 *
 * @author eric.li
 * @date 2022-06-10 16:12
 */
@Configuration
@EnableConfigurationProperties(SpringTransProperties.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TransMsgConfiguration implements InitializingBean {

    private SpringTransProperties properties;

    private TransConfiguration transConfiguration;

    public TransMsgConfiguration(SpringTransProperties properties) {
        this.properties = properties;
        this.initTransConfiguration(properties);
    }

    private void initTransConfiguration(SpringTransProperties properties) {
        this.transConfiguration = TransConfiguration.builder()
                .messageProviderType(properties.getMessage().getProviderType())
                .messageSendThreadPoolSize(properties.getMessage().getSendThreadPoolSize())
                .build();
    }

    @Bean
    public TransMessageSender transMessageSender() {
        return new TransMessageSender();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TransMsgInitializer.init(this.transConfiguration);
    }
}
