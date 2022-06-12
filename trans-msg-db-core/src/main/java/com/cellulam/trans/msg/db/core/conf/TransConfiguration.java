package com.cellulam.trans.msg.db.core.conf;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

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

    @Builder.Default
    private String uidGeneratorType = "uuid";

    @Builder.Default
    private int messageSendThreadPoolSize = 10;
    /**
     * Recover the frequency of execution
     */
    @Builder.Default
    private long recoverPeriodSeconds = 30;
    /**
     * The timeout for transaction retry execution.
     * If this time is exceeded and no ACK is received, the state is reset and retried on the next retry period.
     */
    @Builder.Default
    private long transTryTimeout = 300;
}
