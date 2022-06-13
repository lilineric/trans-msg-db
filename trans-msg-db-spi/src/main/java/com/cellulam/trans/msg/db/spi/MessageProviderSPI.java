package com.cellulam.trans.msg.db.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.MessageProcessor;
import com.cellulam.trans.msg.db.spi.contract.MessageSender;
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
     * register message processor of consumer
     * @param processor
     */
    void registerMessageConsumerProcessor(MessageProcessor processor);

    /**
     * register message processor of producer
     * @param processor
     */
    void registerMessageProducerProcessor(MessageProcessor processor);

    /**
     * get message sender of producer
     * @return
     */
    MessageSender getProducerMsgSender();

    /**
     * get message sender of consumer
     * @return
     */
    MessageSender getConsumerMsgSender();

    /**
     * start
     * start executes the internal tasks of the provider, such as timed tasks, etc.
     */
    void start();
}
