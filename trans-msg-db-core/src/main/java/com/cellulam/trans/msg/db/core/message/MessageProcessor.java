package com.cellulam.trans.msg.db.core.message;

/**
 * @author eric.li
 * @date 2022-06-11 19:19
 */
public interface MessageProcessor {
    /**
     * process message by consumer
     *
     * @param message
     * @return
     */
    boolean process(String message);
}
