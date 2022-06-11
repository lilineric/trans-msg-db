package com.cellulam.trans.msg.db.core.message.factories;

import com.cellulam.trans.msg.db.core.message.spi.MessageProviderSPI;
import com.cellulam.trans.msg.db.spi.InstanceServiceLoader;
import com.cellulam.trans.msg.db.spi.exceptions.ServiceProviderNotFoundException;

import java.util.Collection;

/**
 * message provider factory
 *
 * @author eric.li
 * @date 2022-06-11 00:13
 */
public abstract class MessageProviderFactory {
    static {
        InstanceServiceLoader.register(MessageProviderSPI.class);
    }

    /**
     * Get instance of MessageProviderSPI.
     *
     * @param type
     * @return instance
     */
    public static MessageProviderSPI getInstance(final String type) {
        return getRegisteredService(type);
    }

    private static MessageProviderSPI getRegisteredService(String type) {
        for (MessageProviderSPI instance : InstanceServiceLoader.getServiceInstances(MessageProviderSPI.class)) {
            if (instance.getType().equalsIgnoreCase(type)) {
                return instance;
            }
        }
        throw new ServiceProviderNotFoundException(MessageProviderSPI.class, type);
    }

    /**
     * Get instances of MessageProviderSPI.
     *
     * @return instances
     */
    public static Collection<MessageProviderSPI> getInstances() {
        return InstanceServiceLoader.getServiceInstances(MessageProviderSPI.class);
    }
}
