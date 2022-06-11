package com.cellulam.trans.msg.db.core.conf;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.message.TransMessageProcessor;
import com.cellulam.trans.msg.db.core.message.factories.MessageProviderFactory;
import com.cellulam.trans.msg.db.core.message.spi.MessageProviderSPI;

/**
 * initializer
 *
 * @author eric.li
 * @date 2022-06-12 00:12
 */
public abstract class TransMsgInitializer {

    /**
     * init
     *
     * @param configuration
     */
    public static void init(TransConfiguration configuration) {
        TransContext.init(configuration);

        start(configuration);
    }

    private static void start(TransConfiguration configuration) {
        // start message provider
        MessageProviderSPI messageProvider = MessageProviderFactory.getInstance(configuration.getMessageProviderType());
        messageProvider.registerMessageProcessor(new TransMessageProcessor());
        messageProvider.start();
    }
}
