package com.cellulam.trans.msg.db.spi.loader;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.exceptions.SPINewInstanceException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author eric.li
 * @date 2022-06-10 23:38
 */
public abstract class InstanceServiceLoader {

    private static final Map<Class<?>, Collection<Object>> SERVICES = new ConcurrentHashMap<>();

    /**
     * Register service.
     *
     * @param serviceInterface service interface
     */
    public static void register(final Class<?> serviceInterface) {
        if (!SERVICES.containsKey(serviceInterface)) {
            SERVICES.put(serviceInterface, load(serviceInterface));
        }
    }

    private static <T> Collection<Object> load(final Class<T> serviceInterface) {
        Collection<Object> result = new LinkedList<>();
        for (T each : ServiceLoader.load(serviceInterface)) {
            result.add(each);
        }
        return result;
    }

    /**
     * Get service instances.
     *
     * @param serviceInterface service interface
     * @param <T>              type of service
     * @return service instances
     */
    public static <T> Collection<T> getServiceInstances(final Class<T> serviceInterface) {
        return null == serviceInterface.getAnnotation(SingletonSPI.class) ? createNewServiceInstances(serviceInterface) : getSingletonServiceInstances(serviceInterface);
    }

    @SuppressWarnings("unchecked")
    private static <T> Collection<T> createNewServiceInstances(final Class<T> serviceInterface) {
        try {
            if (!SERVICES.containsKey(serviceInterface)) {
                return Collections.emptyList();
            }
            Collection<Object> services = SERVICES.get(serviceInterface);
            if (services.isEmpty()) {
                return Collections.emptyList();
            }
            Collection<T> result = new LinkedList<>();
            for (Object each : services) {
                result.add((T) each.getClass().getDeclaredConstructor().newInstance());
            }
            return result;
        } catch (Exception e) {
            throw new SPINewInstanceException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Collection<T> getSingletonServiceInstances(final Class<T> serviceInterface) {
        return (Collection<T>) SERVICES.getOrDefault(serviceInterface, Collections.emptyList());
    }
}
