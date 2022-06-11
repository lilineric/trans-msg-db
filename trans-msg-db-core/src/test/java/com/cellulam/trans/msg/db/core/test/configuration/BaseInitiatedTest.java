package com.cellulam.trans.msg.db.core.test.configuration;

import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.conf.TransMsgInitializer;
import org.junit.BeforeClass;

/**
 * @author eric.li
 * @date 2022-06-12 02:38
 */
public abstract class BaseInitiatedTest {
    @BeforeClass
    public static void baseBeforeClass() {
        TransMsgInitializer.init(TransConfiguration.builder()
                .messageProviderType("test")
                .messageSendThreadPoolSize(1)
                .build());
    }
}
