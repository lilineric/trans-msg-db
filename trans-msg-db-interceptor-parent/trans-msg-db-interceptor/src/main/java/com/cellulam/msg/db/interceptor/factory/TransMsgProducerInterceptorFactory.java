package com.cellulam.msg.db.interceptor.factory;

import com.cellulam.msg.db.interceptor.spi.TransMsgProducerInterceptor;
import com.cellulam.trans.msg.db.spi.InstanceServiceLoader;
import com.cellulam.trans.msg.db.spi.exceptions.ServiceProviderNotFoundException;

import java.util.Collection;

/**
 * @author eric.li
 * @date 2022-06-11 00:13
 */
public abstract class TransMsgProducerInterceptorFactory {
    static {
        InstanceServiceLoader.register(TransMsgProducerInterceptor.class);
    }

    /**
     * Get instance of TransMsgProducerInterceptor.
     *
     * @param type
     * @return instance
     */
    public static TransMsgProducerInterceptor getInstance(final String type) {
        return getRegisteredService(type);
    }

    private static TransMsgProducerInterceptor getRegisteredService(String type) {
        for (TransMsgProducerInterceptor instance : InstanceServiceLoader.getServiceInstances(TransMsgProducerInterceptor.class)) {
            if (instance.getType().equalsIgnoreCase(type)) {
                return instance;
            }
        }
        throw new ServiceProviderNotFoundException(TransMsgProducerInterceptor.class, type);
    }

    /**
     * Get instances of TransMsgProducerInterceptor.
     *
     * @return instances
     */
    public static Collection<TransMsgProducerInterceptor> getInstances() {
        return InstanceServiceLoader.getServiceInstances(TransMsgProducerInterceptor.class);
    }
}
