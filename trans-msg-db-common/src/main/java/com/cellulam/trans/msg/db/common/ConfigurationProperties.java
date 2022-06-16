package com.cellulam.trans.msg.db.common;

import lombok.Data;

/**
 * @author eric.li
 * @date 2022-06-15 18:40
 */
@Data
public abstract class ConfigurationProperties<T> {
    private T transMsg;
}
