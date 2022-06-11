package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.message.model.TransMessage;

import java.io.Serializable;

/**
 * message sender
 *
 * @author eric.li
 * @date 2022-06-11 19:13
 */
public interface MessageSender {
    <T extends Serializable> String send(TransMessage<T> message);
}
