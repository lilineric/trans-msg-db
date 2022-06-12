package com.cellulam.trans.msg.db.core.message;

import lombok.extern.slf4j.Slf4j;

/**
 * @author eric.li
 * @date 2022-06-12 00:26
 */
@Slf4j
public class TransMessageProcessor implements MessageProcessor {

    @Override
    public boolean process(String message) {
        log.info("process message: {}", message);
        return true;
    }

    @Override
    public boolean receiveACK(String transType, String transId) {
        log.info("receive ack");
        return true;
    }
}
