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

    private static boolean initiated = false;

    private TransContext() {

    }

    private TransConfiguration properties;

    public final static TransContext context = new TransContext();

    public static boolean isInitiated() {
        return initiated;
    }

    public TransConfiguration getProperties() {
        checkStatus();
        return properties;
    }

    private void checkStatus() {
        if (!initiated) {
            throw new TransMessageConfigurationException("TransContext uninitialized");
        }
    }

    public synchronized static void init(TransConfiguration transProperties) {
        if (initiated) {
            throw new TransMessageConfigurationException("TransContext already initiated");
        }
        context.properties = transProperties;
        log.info("Loaded properties: {}", transProperties);

        initiated = true;
    }
}
