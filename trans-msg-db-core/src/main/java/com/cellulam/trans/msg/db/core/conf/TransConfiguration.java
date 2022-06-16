package com.cellulam.trans.msg.db.core.conf;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.sql.DataSource;

/**
 * @author eric.li
 * @date 2022-06-11 23:11
 */
@Data
@ToString
@NoArgsConstructor
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
    @NonNull
    private String uidGeneratorType;

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
