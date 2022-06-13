package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.spi.contract.MessageProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * process ACK message by producer
 * @author eric.li
 * @date 2022-06-12 00:26
 */
@Slf4j
public class MessageProducerReceiveProcessor implements MessageProcessor {

    @Override
    public boolean process(String message) {
        return false;
    }
}
