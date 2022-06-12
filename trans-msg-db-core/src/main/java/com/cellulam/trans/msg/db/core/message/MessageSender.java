package com.cellulam.trans.msg.db.core.message;

/**
 * message sender
 *
 * @author eric.li
 * @date 2022-06-11 19:13
 */
public interface MessageSender {
    /**
     * send message from producer
     *
     * @param transType
     * @param message
     */
    void send(String transType, String message);

    /**
     * send message from producer
     *
     * @param transType
     * @param message
     */
    void send(String transType, byte[] message);

    /**
     * ACK from consumer
     * @param transType
     * @param transId
     */
    void ack(String transType, String transId);
}
