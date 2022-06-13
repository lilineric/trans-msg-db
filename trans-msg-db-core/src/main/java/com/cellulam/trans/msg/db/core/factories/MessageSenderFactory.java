package com.cellulam.trans.msg.db.core.factories;


import com.cellulam.trans.msg.db.spi.contract.MessageSender;

/**
 * message sender factory
 *
 * @author eric.li
 * @date 2022-06-11 19:40
 */
public abstract class MessageSenderFactory {
    public static MessageSender getConsumerSender(String type) {
        return MessageProviderFactory.getInstance(type).getConsumerMsgSender();
    }
    public static MessageSender getProducerSender(String type) {
        return MessageProviderFactory.getInstance(type).getProducerMsgSender();
    }
}
