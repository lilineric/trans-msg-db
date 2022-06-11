package com.cellulam.trans.msg.db.core.message.spi;

import com.cellulam.trans.msg.db.core.message.MessageProcessor;
import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;

/**
 * message provider SPI
 *
 * @author eric.li
 * @date 2022-06-11 13:51
 */
@SingletonSPI
public interface MessageProviderSPI extends TypeSPI {
    /**
     * register message processor
     * @param receiver
     */
    void registerMessageProcessor(MessageProcessor receiver);

    /**
     * get message sender
     * @return
     */
    MessageSender getMessageSender();

    /**
     * start
     */
    void start();
}
