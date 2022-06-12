package com.cellulam.trans.msg.db.core.message;

/**
 * @author eric.li
 * @date 2022-06-11 19:19
 */
public interface MessageProcessor {
    boolean process(String message);
}
