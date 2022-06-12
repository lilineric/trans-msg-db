package com.cellulam.trans.msg.db.core.message;

/**
 * message sender
 *
 * @author eric.li
 * @date 2022-06-11 19:13
 */
public interface MessageSender {
    void send(String message);

    void send(byte[] message);
}
