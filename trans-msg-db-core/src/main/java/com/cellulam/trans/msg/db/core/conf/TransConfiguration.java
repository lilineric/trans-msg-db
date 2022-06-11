package com.cellulam.trans.msg.db.core.conf;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author eric.li
 * @date 2022-06-11 23:11
 */
@Data
@ToString
@Builder
public class TransConfiguration {
    private int messageSendThreadPoolSize;
    private String messageProviderType;
}
