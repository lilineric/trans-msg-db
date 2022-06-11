package com.cellulam.trans.msg.db.core.conf;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.message.TransMessageProcessor;

/**
 * initializer
 *
 * @author eric.li
 * @date 2022-06-12 00:12
 */
public abstract class TransMsgInitializer {

    /**
     * init
     * @param configuration
     */
    public static void init(TransConfiguration configuration) {
        TransContext.init(configuration);
        registerMessageProcessor();
        TransContext.context.getMessageProviderSPI().start();
    }

    private static void registerMessageProcessor() {
        TransContext.context.getMessageProviderSPI().registerMessageProcessor(new TransMessageProcessor());
    }
}
