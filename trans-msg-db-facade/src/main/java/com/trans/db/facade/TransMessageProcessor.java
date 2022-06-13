package com.trans.db.facade;

import com.trans.db.facade.enums.TransProcessResult;

import java.io.Serializable;

/**
 * trans message consumer processor
 *
 * @author eric.li
 * @date 2022-06-13 11:36
 */
public interface TransMessageProcessor<T extends Serializable> {
    String getProducer();

    String getTransType();

    TransProcessResult process(T body);

    Class<T> getBodyClassType();
}
