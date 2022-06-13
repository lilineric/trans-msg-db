package com.cellulam.trans.msg.db.spi.loader;

import com.cellulam.trans.msg.db.spi.contract.TypeSPI;
import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.exceptions.ServiceProviderNotFoundException;
import com.cellulam.trans.msg.db.spi.exceptions.ServiceProviderRegisterException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * instance factory for {@link TypeSPI}
 * only for {@link SingletonSPI}
 *
 * @author eric.li
 * @date 2022-06-12 01:17
 */
@Slf4j
public abstract class SingletonTypeSPIInstanceFactory {

    private final static Map<String, Map<String, TypeSPI>> MAP = Maps.newHashMap();


    /**
     * register
     *
     * @param serviceInterface
     */
    public static void register(final Class<? extends TypeSPI> serviceInterface) {
        if (serviceInterface.getAnnotation(SingletonSPI.class) == null) {
            throw new ServiceProviderRegisterException(serviceInterface.getName() + " is not singleton SPI");
        }
        InstanceServiceLoader.register(serviceInterface);

        for (TypeSPI instance : InstanceServiceLoader.getServiceInstances(serviceInterface)) {
            log.info("TypeSPI registered, type:{}, serviceInterface: {}", instance.getType(), instance);
            MAP.computeIfAbsent(serviceInterface.getName(), x -> Maps.newHashMap())
                    .put(instance.getType(), instance);
        }
    }

    /**
     * Get instance
     *
     * @param type
     * @return instance
     */
    public static <T extends TypeSPI> T getInstance(final String type, final Class<T> serviceInterface) {
        Map<String, TypeSPI> map = MAP.get(serviceInterface.getName());
        if (MapUtils.isEmpty(map)) {
            throw new ServiceProviderNotFoundException(serviceInterface, type);
        }
        TypeSPI provider = map.get(type);
        if (provider == null) {
            throw new ServiceProviderNotFoundException(serviceInterface, type);
        }
        return (T) provider;
    }

    /**
     * Get instances
     *
     * @return instances
     */
    public static <T extends TypeSPI> Collection<T> getInstances(final Class<T> serviceInterface) {
        return InstanceServiceLoader.getServiceInstances(serviceInterface);
    }
}
