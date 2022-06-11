package com.cellulam.trans.msg.db.core.test;

import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.conf.TransMsgInitializer;
import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.exceptions.TransMessageConfigurationException;
import com.cellulam.trans.msg.db.spi.exceptions.ServiceProviderNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @author eric.li
 * @date 2022-06-12 01:55
 */
public class TransMsgInitializerTest {

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        Field field = TransContext.context.getClass().getDeclaredField("initiated");
        field.setAccessible(true);
        field.set(TransContext.context, false);
    }

    @Test
    public void testInit() {
        TransMsgInitializer.init(TransConfiguration.builder()
                .messageProviderType("test")
                .build());
    }

    @Test(expected = ServiceProviderNotFoundException.class)
    public void testInitException() {
        TransMsgInitializer.init(TransConfiguration.builder()
                .messageProviderType(UUID.randomUUID().toString())
                .build());
    }

    @Test(expected = TransMessageConfigurationException.class)
    public void testInitTransMessageConfigurationException() {
        TransMsgInitializer.init(TransConfiguration.builder()
                .messageProviderType("test")
                .build());
        TransMsgInitializer.init(TransConfiguration.builder()
                .messageProviderType("test")
                .build());
    }
}
