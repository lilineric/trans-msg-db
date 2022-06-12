package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.coordinator.TransCoordinator;
import com.cellulam.trans.msg.db.core.repository.TransRepository;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-11 19:51
 */
public class TransMessageSender {
    public <T extends Serializable> String send(T body) {
        String transId = TransRepository.instance.insertTransMessage(TransContext.getConfiguration().getAppName(), body);
        TransCoordinator.instance.asyncCommit(transId, body);
        return transId;
    }
}
