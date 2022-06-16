package com.cellulam.trans.msg.db.core.test.spi;

import com.cellulam.trans.msg.db.core.test.mock.storage.MockMQ;
import com.cellulam.trans.msg.db.spi.MessageProviderSPI;
import com.cellulam.trans.msg.db.spi.contract.MessageProcessor;
import com.cellulam.trans.msg.db.spi.contract.MessageSender;
import com.trans.db.facade.ConsumerRegister;
import com.trans.db.facade.enums.TransStage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author eric.li
 * @date 2022-06-12 01:57
 */
public class TestMessageProviderSPI implements MessageProviderSPI {
    private MessageProcessor messageConsumerProcessor;
    private MessageProcessor messageProducerProcessor;
    private MessageSender messageProducerSender = new TestMessageSender(TransStage.COMMIT);
    private MessageSender messageConsumerSender = new TestMessageSender(TransStage.ACK);

    @Override
    public void init(String appName, List<ConsumerRegister> consumerRegisters, List<String> transTypes) {

    }

    @Override
    public void registerMessageConsumerProcessor(MessageProcessor processor) {
        this.messageConsumerProcessor = processor;
    }

    @Override
    public void registerMessageProducerProcessor(MessageProcessor processor) {
        this.messageProducerProcessor = processor;
    }

    @Override
    public MessageSender getProducerMsgSender() {
        return this.messageProducerSender;
    }

    @Override
    public MessageSender getConsumerMsgSender() {
        return this.messageConsumerSender;
    }

    @Override
    public void start() {
        String consumerMessage = MockMQ.queue.poll();
        if (StringUtils.isNotEmpty(consumerMessage)) {
            this.messageConsumerProcessor.process(consumerMessage);
        }

        String producerMessage = MockMQ.ackQueue.poll();
        if (StringUtils.isNotEmpty(producerMessage)) {
            this.messageProducerProcessor.process(producerMessage);
        }
    }

    @Override
    public String getType() {
        return "test";
    }
}
