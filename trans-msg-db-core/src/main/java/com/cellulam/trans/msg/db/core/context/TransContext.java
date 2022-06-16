package com.cellulam.trans.msg.db.core.context;

import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.exceptions.TransMessageConfigurationException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eric.li
 * @date 2022-06-11 22:56
 */
@Slf4j
public class TransContext {

    private static volatile boolean initiated = false;

    private TransContext() {

    }

    private TransConfiguration configuration;

    private final static TransContext context = new TransContext();

    public static boolean isInitiated() {
        return initiated;
    }

    public static TransContext getContext() {
        return context;
    }

    public static TransConfiguration getConfiguration() {
        checkStatus();
        return context.configuration;
    }

    private static void checkStatus() {
        if (!initiated) {
            throw new TransMessageConfigurationException("TransContext uninitialized");
        }
    }

    public synchronized static void init(TransConfiguration transProperties) {
        if (initiated) {
            throw new TransMessageConfigurationException("TransContext already initiated");
        }
        context.configuration = transProperties;
        log.info("Loaded properties: {}", transProperties);

        initiated = true;
    }
}
