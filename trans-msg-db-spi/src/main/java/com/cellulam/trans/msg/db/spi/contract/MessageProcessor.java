package com.cellulam.trans.msg.db.spi.contract;

/**
 * @author eric.li
 * @date 2022-06-11 19:19
 */
public interface MessageProcessor {
    /**
     * process message
     * @param message
     * @return
     */
    boolean process(String message);
}
