package com.cellulam.trans.msg.db.core.test.spi;

import com.cellulam.trans.msg.db.core.message.MessageSender;

/**
 * @author eric.li
 * @date 2022-06-12 22:06
 */
public class TestMessageSender implements MessageSender {
    @Override
    public void send(String transType, String message) {
        System.out.println(transType + " send message " + message);
    }

    @Override
    public void send(String transType, byte[] message) {
        System.out.println(transType + " send message " + message);
    }
}
