package com.cellulam.trans.msg.db.core.context;

import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.exceptions.TransMessageConfigurationException;
import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.core.message.factories.MessageProviderFactory;
import com.cellulam.trans.msg.db.core.message.spi.MessageProviderSPI;
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

    private MessageProviderSPI messageProviderSPI;
    private MessageSender messageSender;

    public final static TransContext context = new TransContext();

    public static boolean isInitiated() {
        return initiated;
    }

    public TransConfiguration getProperties() {
        checkStatus();
        return properties;
    }

    public MessageProviderSPI getMessageProviderSPI() {
        checkStatus();
        return messageProviderSPI;
    }

    public MessageSender getMessageSender() {
        checkStatus();
        return messageSender;
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

        context.messageProviderSPI = MessageProviderFactory.getInstance(transProperties.getMessageProviderType());
        log.info("Loaded messageProviderSPI: {}", context.messageProviderSPI.getClass().getName());

        context.messageSender = context.messageProviderSPI.getMessageSender();
        log.info("Loaded messageSender: {}", context.messageSender.getClass().getName());

        initiated = true;
    }
}
