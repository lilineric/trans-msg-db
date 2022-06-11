package com.cellulam.trans.msg.db.core.coordinator;

import com.cellulam.trans.msg.db.core.exceptions.TransMessageSendException;
import com.cellulam.trans.msg.db.core.message.MessageSender;
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
    private TransCoordinator() {

    }

    private ExecutorService messageSendThreadPool = Executors.newFixedThreadPool(5);

    public final static TransCoordinator instance = new TransCoordinator();

    public <T extends Serializable> void asyncCommit(MessageSender sender, String transId, T body) {
        messageSendThreadPool.execute(() -> this.commit(sender, transId, body));
    }

    private <T extends Serializable> void commit(MessageSender sender, String transId, T body) {
        try {
            TransMessage message = new TransMessage();

            TransMessageHeader header = new TransMessageHeader();
            header.setTransId(transId);

            message.setHeader(header);
            message.setBody(body);

            this.sendMessage(sender, message);
        } catch (Exception e) {
            log.error(String.format("Failed to commit, sender: %s, transId: %s", sender.toString(), transId), e);
        }
    }

    private void sendMessage(MessageSender sender, TransMessage message) {
        try {
            sender.send(message);
        } catch (Exception e) {
            throw new TransMessageSendException("Failed to send message: " + message, e);
        }
    }
}
