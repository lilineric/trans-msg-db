package com.cellulam.trans.msg.db.core.test.configuration;

import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.conf.TransMsgInitializer;
import com.cellulam.trans.msg.db.core.context.TransContext;
import com.trans.db.facade.TransMessageProcessor;
import com.trans.db.facade.TransProcessResult;
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
                .serializeType("test")
                .repositoryType("test")
                .messageSendThreadPoolSize(1)
                .dynamicConfigType("test")
                .uidGeneratorType("uuid")
                .build());

        TransMsgInitializer.registerConsumerProcessor(new TransMessageProcessor<String>() {
            @Override
            public String getProducer() {
                return "test";
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

        TransMsgInitializer.start();
    }
}
