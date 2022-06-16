package com.cellulam.trans.db.spring.configuration;

import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.message.TransMessageSender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.sql.DataSource;

/**
 * trans configuration
 *
 * @author eric.li
 * @date 2022-06-10 16:12
 */
@Configuration
@ComponentScan("com.cellulam.trans.db.spring.configuration")
@EnableConfigurationProperties(SpringTransProperties.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TransMsgConfiguration {

    private DataSource dataSource;

    @Value("spring.application.name")
    private String appName;

    public TransMsgConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private TransConfiguration buildTransConfiguration(SpringTransProperties properties) {
        TransConfiguration configuration = new TransConfiguration();
        configuration.setAppName(StringUtils.isEmpty(properties.getAppName()) ? appName : properties.getAppName());
        configuration.setMessageProviderType(properties.getMessageProviderType());
        configuration.setRepositoryType(properties.getRepositoryType());
        configuration.setSerializeType(properties.getSerializeType());
        configuration.setDynamicConfigType(properties.getDynamicConfigType());
        configuration.setUidGeneratorType(properties.getUidGeneratorType());
        configuration.setMessageSendThreadPoolSize(properties.getMessageSendThreadPoolSize());
        configuration.setRecoverExecPeriodSeconds(properties.getRecoverExecPeriodSeconds());
        configuration.setRecoverFixPeriodSeconds(properties.getRecoverFixPeriodSeconds());
        configuration.setTransTryTimeoutSeconds(properties.getTransTryTimeoutSeconds());

        configuration.setDataSource(dataSource);

        return configuration;
    }

    @Bean
    @ConditionalOnMissingBean
    public TransConfiguration transConfiguration(SpringTransProperties properties) {
        return this.buildTransConfiguration(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public TransMessageSender transMessageSender() {
        return new TransMessageSender();
    }
}
