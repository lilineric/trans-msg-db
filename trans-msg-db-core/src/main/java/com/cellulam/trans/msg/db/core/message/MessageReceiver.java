package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.message.model.TransMessage;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-11 19:19
 */
public interface MessageReceiver {
    <T extends Serializable> Boolean receive(TransMessage<T> message);
}
