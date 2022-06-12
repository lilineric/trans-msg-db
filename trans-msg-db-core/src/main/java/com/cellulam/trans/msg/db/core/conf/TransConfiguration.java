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

    private int messageSendThreadPoolSize = 10;
}
