package com.cellulam.trans.msg.db.core.test.configuration;

import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.conf.TransMsgInitializer;
import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.test.mock.storage.MockDB;
import com.trans.db.facade.AbstractTransMessageProcessor;
import com.trans.db.facade.enums.TransProcessResult;
import org.junit.BeforeClass;

import java.lang.reflect.Field;

/**
 * @author eric.li
 * @date 2022-06-12 02:38
 */
public abstract class BaseInitiatedTest {
    @BeforeClass
    public static void baseBeforeClass() throws NoSuchFieldException, IllegalAccessException {
        Field field = TransContext.getContext().getClass().getDeclaredField("initiated");
        field.setAccessible(true);
        field.set(TransContext.getContext(), false);

        TransMsgInitializer.init(TransConfiguration.builder()
                .appName("test-app")
                .messageProviderType("test")
                .serializeType("json")
                .repositoryType("test")
                .messageSendThreadPoolSize(1)
                .dynamicConfigType("test")
                .uidGeneratorType("uuid")
                .dataSource(MockDB.dataSource)
                .build());

        registerConsumerProcessor();

        TransMsgInitializer.start();
    }

    public static void registerConsumerProcessor() {
        TransMsgInitializer.registerConsumerProcessor(new AbstractTransMessageProcessor<String>() {
            @Override
            public String getProducer() {
                return "test-app";
            }

            @Override
            public String getTransType() {
                return "order-success";
            }

            @Override
            public TransProcessResult process(String body) {
                return TransProcessResult.SUCCESS;
            }
        });
    }
}
