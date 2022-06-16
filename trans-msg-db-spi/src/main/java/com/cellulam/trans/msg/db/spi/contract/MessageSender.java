package com.cellulam.trans.msg.db.spi.contract;

/**
 * message sender
 *
 * @author eric.li
 * @date 2022-06-11 19:13
 */
public interface MessageSender {
    /**
     * send message
     *
     * @param rootSource
     * @param transType
     * @param message
     */
    void send(String rootSource, String transType, String message);
}
