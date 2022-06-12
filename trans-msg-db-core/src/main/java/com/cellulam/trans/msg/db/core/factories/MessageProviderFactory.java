package com.cellulam.trans.msg.db.core.factories;

import com.cellulam.trans.msg.db.core.spi.MessageProviderSPI;
import com.cellulam.trans.msg.db.spi.SingletonTypeSPIInstanceFactory;

import java.util.Collection;

/**
 * message provider factory
 *
 * @author eric.li
 * @date 2022-06-11 00:13
 */
public abstract class MessageProviderFactory {

    static {
        SingletonTypeSPIInstanceFactory.register(MessageProviderSPI.class);
    }

    /**
     * Get instance of MessageProviderSPI.
     *
     * @param type
     * @return instance
     */
    public static MessageProviderSPI getInstance(final String type) {
        return SingletonTypeSPIInstanceFactory.getInstance(type, MessageProviderSPI.class);
    }

    /**
     * Get instances of MessageProviderSPI.
     *
     * @return instances
     */
    public static Collection<MessageProviderSPI> getInstances() {
        return SingletonTypeSPIInstanceFactory.getInstances(MessageProviderSPI.class);
    }
}
