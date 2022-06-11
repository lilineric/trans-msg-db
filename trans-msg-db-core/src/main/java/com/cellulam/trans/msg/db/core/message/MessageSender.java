package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.message.model.TransMessage;

/**
 * message sender
 *
 * @author eric.li
 * @date 2022-06-11 19:13
 */
public interface MessageSender {
    String send(TransMessage message);
}
