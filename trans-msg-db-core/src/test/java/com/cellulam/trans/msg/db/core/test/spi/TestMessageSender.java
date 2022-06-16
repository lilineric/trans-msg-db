package com.cellulam.trans.msg.db.core.test.spi;


import com.cellulam.trans.msg.db.core.test.mock.storage.MockMQ;
import com.cellulam.trans.msg.db.spi.contract.MessageSender;
import com.trans.db.facade.enums.TransStage;

/**
 * @author eric.li
 * @date 2022-06-12 22:06
 */
public class TestMessageSender implements MessageSender {
    private TransStage transStage;

    public TestMessageSender(TransStage transStage) {
        this.transStage = transStage;
    }

    @Override
    public void send(String rootSource, String transType, String message) {
        if (transStage == TransStage.COMMIT) {
            MockMQ.queue.offer(message);
        } else if (transStage == TransStage.ACK) {
            MockMQ.ackQueue.offer(message);
        }
    }

}
