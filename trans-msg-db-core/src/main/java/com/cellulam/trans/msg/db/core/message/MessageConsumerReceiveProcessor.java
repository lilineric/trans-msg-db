package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.exceptions.TransMessageProcessException;
import com.cellulam.trans.msg.db.core.factories.MessageSenderFactory;
import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import com.cellulam.trans.msg.db.core.message.model.TransMessageHeader;
import com.cellulam.trans.msg.db.spi.contract.MessageProcessor;
import com.cellulam.trans.msg.db.spi.contract.MessageSender;
import com.trans.db.facade.enums.TransProcessResult;
import com.trans.db.facade.enums.TransStage;
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
public class MessageConsumerReceiveProcessor extends AbstractMessageReceiveProcessor implements MessageProcessor {

    private final MessageSender consumerMessageSender;

    private final Map<String, ConsumerProcessorWrap<? extends Serializable>> processors;

    public MessageConsumerReceiveProcessor(Map<String, ConsumerProcessorWrap<? extends Serializable>> processors) {
        super();
        this.processors = processors;
        this.consumerMessageSender = MessageSenderFactory.getConsumerSender(TransContext.getConfiguration().getMessageProviderType());
    }

    @Override
    protected boolean process(TransMessage transMessage) {
        log.debug("Process {}", transMessage);
        String processorKey = ConsumerProcessorWrap.getKey(transMessage.getHeader().getSource(),
                transMessage.getHeader().getTransType());
        ConsumerProcessorWrap processor = this.processors.get(processorKey);

        if (processor == null) {
            throw new TransMessageProcessException(String.format("Cannot find processor: %s, message: %s",
                    processorKey, transMessage));
        }
        TransProcessResult result = TransProcessResult.FAILED;
        try {
            result = processor.getProcessor().process(this.getSerializeSPI().deserialize(transMessage.getBody(), processor.getBodyClass()));
        } catch (Exception e) {
            log.error(String.format("[transId=%s] Process fail", transMessage.getHeader().getTransId()), e);
        }
        log.debug("[transId={}] Process Result: {}", transMessage.getHeader().getTransId(), result);

        //ACK
        this.consumerMessageSender.send(transMessage.getHeader().getSource(),
                transMessage.getHeader().getTransType(),
                this.buildAckMessage(transMessage, result));

        return result == TransProcessResult.SUCCESS;
    }

    private String buildAckMessage(TransMessage transMessage, TransProcessResult result) {
        TransMessageHeader ackHeader = new TransMessageHeader();
        ackHeader.setTransId(transMessage.getHeader().getTransId());
        ackHeader.setSource(this.getSource());
        ackHeader.setTransType(transMessage.getHeader().getTransType());
        ackHeader.setStage(TransStage.ACK.name());

        TransMessage ackMessage = new TransMessage();
        ackMessage.setHeader(ackHeader);
        ackMessage.setBody(result.name());
        return this.getSerializeSPI().serialize(ackMessage);
    }
}
