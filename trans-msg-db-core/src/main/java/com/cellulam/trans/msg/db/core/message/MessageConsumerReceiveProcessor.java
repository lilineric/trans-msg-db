package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.exceptions.TransMessageProcessException;
import com.cellulam.trans.msg.db.core.factories.MessageSenderFactory;
import com.cellulam.trans.msg.db.core.factories.SerializeFactory;
import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import com.cellulam.trans.msg.db.spi.SerializeSPI;
import com.cellulam.trans.msg.db.spi.contract.MessageProcessor;
import com.cellulam.trans.msg.db.spi.contract.MessageSender;
import com.trans.db.facade.TransProcessResult;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;

/**
 * process message by consumer
 *
 * @author eric.li
 * @date 2022-06-12 00:26
 */
@Slf4j
public class MessageConsumerReceiveProcessor implements MessageProcessor {

    private final SerializeSPI serializeSPI;

    private final MessageSender consumerMessageSender;

    private final Map<String, ConsumerProcessorWrap<? extends Serializable>> processors;

    private final String source = TransContext.getConfiguration().getAppName();

    public MessageConsumerReceiveProcessor(Map<String, ConsumerProcessorWrap<? extends Serializable>> processors) {
        this.processors = processors;

        this.consumerMessageSender = MessageSenderFactory.getConsumerSender(TransContext.getConfiguration().getMessageProviderType());
        this.serializeSPI = SerializeFactory.getInstance(TransContext.getConfiguration().getSerializeType());
    }

    @Override
    public boolean process(String message) {
        try {
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

    private boolean process(TransMessage transMessage) {
        String processorKey = ConsumerProcessorWrap.getKey(transMessage.getHeader().getSource(),
                transMessage.getHeader().getTransType());
        ConsumerProcessorWrap processor = this.processors.get(processorKey);

        if (processor == null) {
            throw new TransMessageProcessException(String.format("Cannot find processor: %s, message: %s",
                    processorKey, transMessage));
        }

        TransProcessResult result = processor.getProcessor().process(serializeSPI.deserialize(transMessage.getBody(), processor.getBodyClass()));

        if (result == TransProcessResult.SUCCESS) {
            this.consumerMessageSender.send(source,
                    transMessage.getHeader().getTransType(),
                    transMessage.getHeader().getTransId());
            return true;
        }

        return false;
    }
}
