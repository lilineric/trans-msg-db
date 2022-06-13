package com.cellulam.trans.msg.db.core.conf;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import javax.sql.DataSource;

/**
 * @author eric.li
 * @date 2022-06-11 23:11
 */
@Data
@ToString
@Builder
public class TransConfiguration {
    @NonNull
    private String appName;
    @NonNull
    private String messageProviderType;
    @NonNull
    private String repositoryType;
    @NonNull
    private String serializeType;
    @NonNull
    private String dynamicConfigType;
    @NonNull
    private DataSource dataSource;

    @Builder.Default
    private String uidGeneratorType;

    @Builder.Default
    private int messageSendThreadPoolSize = 10;
    /**
     * Recover the frequency of execution
     */
    @Builder.Default
    private long recoverExecPeriodSeconds = 30;
    /**
     * Recover the frequency of fixing trying
     */
    @Builder.Default
    private long recoverFixPeriodSeconds = 120;
    /**
     * The timeout for transaction retry execution.
     * If this time is exceeded and no ACK is received, the state is reset and retried on the next retry period.
     */
    @Builder.Default
    private long transTryTimeoutSeconds = 600;
}
