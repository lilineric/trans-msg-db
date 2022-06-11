package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-12 00:26
 */
@Slf4j
public class TransMessageProcessor implements MessageProcessor {
    @Override
    public <T extends Serializable> boolean process(TransMessage<T> message) {
        log.info("process message: {}", message);
        return true;
    }
}
