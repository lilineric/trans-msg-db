package com.cellulam.trans.db.spring.configuration;

import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.conf.TransMsgInitializer;
import com.cellulam.trans.msg.db.core.message.TransMessageSender;
import com.trans.db.facade.TransMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

/**
 * trans configuration
 *
 * @author eric.li
 * @date 2022-06-10 16:12
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(SpringTransProperties.class)
public class TransMsgConfiguration {

    private DataSource dataSource;

    @Value("${spring.application.name}")
    private String appName;

    public TransMsgConfiguration(@Autowired(required = false) DataSource dataSource) {
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
    public TransMessageSender transMessageSender(TransConfiguration transConfiguration) {
        this.startTransMsg(transConfiguration);
        return new TransMessageSender();
    }

    private void startTransMsg(TransConfiguration transConfiguration) {
        TransMsgInitializer.init(transConfiguration);
        this.registerProcessors();
        TransMsgInitializer.start();
        log.info("TransMsgInitializer start");
    }

    private void registerProcessors() {
        List<TransMessageProcessor<?>> processors = ConsumerProcessorHolder.holder.getProcessors();

        log.info("consumer processor initializer and processor size is {}", processors.size());
        if (CollectionUtils.isEmpty(processors)) {
            return;
        }

        for (TransMessageProcessor<?> processor : processors) {
            TransMsgInitializer.registerConsumerProcessor(processor);
        }
    }
}
