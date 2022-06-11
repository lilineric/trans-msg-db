package com.cellulam.trans.msg.db.core.message.spi;

import com.cellulam.trans.msg.db.core.message.MessageReceiver;
import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;

/**
 * message SPI
 *
 * @author eric.li
 * @date 2022-06-11 13:51
 */
@SingletonSPI
public interface MessageProviderSPI extends TypeSPI {
    void registerMessageReceiver(MessageReceiver receiver);
    MessageSender getMessageSender();
}
