package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.coordinator.TransCoordinator;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author eric.li
 * @date 2022-06-11 19:51
 */
public class TransMessageSender {
    public <T extends Serializable> String send(T body) {
        String transId = UUID.randomUUID().toString();
        //TODO insert message_trans to DB
        TransCoordinator.instance.asyncCommit(transId, body);
        return transId;
    }
}
