package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.exceptions.TransMessageProcessException;
import com.cellulam.trans.msg.db.core.factories.SerializeFactory;
import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import com.cellulam.trans.msg.db.spi.SerializeSPI;
import com.cellulam.trans.msg.db.spi.contract.MessageProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * process message by consumer
 *
 * @author eric.li
 * @date 2022-06-12 00:26
 */
@Slf4j
public abstract class AbstractMessageReceiveProcessor implements MessageProcessor {

    private final SerializeSPI serializeSPI;

    private final String source = TransContext.getConfiguration().getAppName();

    public AbstractMessageReceiveProcessor() {
        this.serializeSPI = SerializeFactory.getInstance(TransContext.getConfiguration().getSerializeType());
    }

    @Override
    public boolean process(String message) {
        try {
            log.debug("[{}]process message: {}", this.getClass().getName(), message);
            TransMessage transMessage = serializeSPI.deserialize(message, TransMessage.class);
            if (transMessage == null || transMessage.getHeader() == null) {
                throw new TransMessageProcessException("Message format is incorrect: " + message);
            }
            return this.process(transMessage);
        } catch (Exception e) {
            log.error("Failed to process message: " + message, e);
            return false;
        }
    }

    protected SerializeSPI getSerializeSPI() {
        return this.serializeSPI;
    }

    protected String getSource() {
        return this.source;
    }

    protected abstract boolean process(TransMessage transMessage);


}
