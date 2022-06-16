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

        TransConfiguration transConfiguration = new TransConfiguration();
        transConfiguration.setAppName("order");
        transConfiguration.setMessageProviderType("test");
        transConfiguration.setRepositoryType("test");
        transConfiguration.setSerializeType("json");
        transConfiguration.setDynamicConfigType("test");
        transConfiguration.setDataSource(MockDB.dataSource);
        transConfiguration.setUidGeneratorType("uuid");
        transConfiguration.setMessageSendThreadPoolSize(1);

        TransMsgInitializer.init(transConfiguration);

        registerConsumerProcessor();

        TransMsgInitializer.start();
    }

    public static void registerConsumerProcessor() {
        TransMsgInitializer.registerConsumerProcessor(new AbstractTransMessageProcessor<String>() {
            @Override
            public String getProducer() {
                return "order";
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
