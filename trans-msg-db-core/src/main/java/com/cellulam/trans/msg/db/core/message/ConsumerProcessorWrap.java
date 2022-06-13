package com.cellulam.trans.msg.db.core.message;

import com.trans.db.facade.TransMessageProcessor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-13 12:40
 */
@Data
@Builder
public class ConsumerProcessorWrap<T extends Serializable> {
    @NonNull
    private String producer;
    @NonNull
    private String transType;
    @NonNull
    private Class<T> bodyClass;
    @NonNull
    private TransMessageProcessor<T> processor;

    public String getKey() {
        return this.getKey(this.producer, this.transType);
    }

    public static String getKey(String producer, String transType) {
        return producer + ":" + transType;
    }
}
