package com.cellulam.trans.msg.db.core.message;

import lombok.extern.slf4j.Slf4j;

/**
 * process message by consumer
 * @author eric.li
 * @date 2022-06-12 00:26
 */
@Slf4j
public class MessageConsumerReceiveProcessor implements MessageProcessor {

    @Override
    public boolean process(String message) {
        log.info("process message: {}", message);
        return true;
    }
}
