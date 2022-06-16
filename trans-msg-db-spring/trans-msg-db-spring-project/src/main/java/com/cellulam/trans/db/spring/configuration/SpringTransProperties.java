package com.cellulam.trans.db.spring.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author eric.li
 * @date 2022-06-12 00:00
 */
@Data
@ConfigurationProperties(prefix = "spring.trans.msg")
public class SpringTransProperties {

    /**
     * app name
     * default same as spring.application.name
     */
    private String appName;

    /**
     * message provider type
     * see {@link com.cellulam.trans.msg.db.spi.MessageProviderSPI}
     */
    private String messageProviderType = "kafka";

    /**
     * repository type
     * see {@link com.cellulam.trans.msg.db.spi.RepositorySPI}
     */
    private String repositoryType = "mysql";

    /**
     * serialize type
     * see {@link com.cellulam.trans.msg.db.spi.SerializeSPI}
     */
    private String serializeType = "json";

    /**
     * dynamic config type
     * see {@link com.cellulam.trans.msg.db.spi.DynamicConfigSPI}
     */
    private String dynamicConfigType = "properties";


    /**
     * uid generator type
     * see {@link com.cellulam.trans.msg.db.spi.UidGeneratorSPI}
     */
    private String uidGeneratorType = "uuid";

    /**
     * thread pool size for message sending
     */
    private int messageSendThreadPoolSize = 10;
    /**
     * Recover the frequency of execution
     */
    private long recoverExecPeriodSeconds = 30;
    /**
     * Recover the frequency of fixing trying
     */
    private long recoverFixPeriodSeconds = 120;
    /**
     * The timeout for transaction retry execution.
     * If this time is exceeded and no ACK is received, the state is reset and retried on the next retry period.
     */
    private long transTryTimeoutSeconds = 600;
}
