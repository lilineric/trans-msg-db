package com.cellulam.trans.msg.db.core.coordinator;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.exceptions.TransMessageSendException;
import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.core.message.factories.MessageSenderFactory;
import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import com.cellulam.trans.msg.db.core.message.model.TransMessageHeader;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * transaction coordinator
 *
 * @author eric.li
 * @date 2022-06-11 21:32
 */
@Slf4j
public class TransCoordinator {

    private final MessageSender messageSender;

    private final ExecutorService messageSendThreadPool;

    private TransCoordinator() {
        this.messageSender = MessageSenderFactory.getInstance(TransContext.context.getProperties().getMessageProviderType());
        this.messageSendThreadPool = Executors.newFixedThreadPool(TransContext.context.getProperties().getMessageSendThreadPoolSize());
    }

    public final static TransCoordinator instance = new TransCoordinator();

    public <T extends Serializable> void asyncCommit(String transId, T body) {
        messageSendThreadPool.execute(() -> this.commit(transId, body));
    }

    private <T extends Serializable> void commit(String transId, T body) {
        try {
            TransMessage message = new TransMessage();

            TransMessageHeader header = new TransMessageHeader();
            header.setTransId(transId);

            message.setHeader(header);
            message.setBody(body);

            this.sendMessage(message);
        } catch (Exception e) {
            log.error(String.format("Failed to commit,  transId: %s", transId), e);
        }
    }

    private void sendMessage(TransMessage message) {
        try {
            this.messageSender.send(message);
        } catch (Exception e) {
            throw new TransMessageSendException("Failed to send message: " + message, e);
        }
    }
}
