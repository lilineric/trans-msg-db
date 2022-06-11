package com.cellulam.trans.msg.db.core.test;

import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.core.message.factories.MessageProviderFactory;
import com.cellulam.trans.msg.db.core.message.factories.MessageSenderFactory;
import com.cellulam.trans.msg.db.core.message.spi.MessageProviderSPI;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author eric.li
 * @date 2022-06-12 02:53
 */
public class MessageSenderFactoryTest {

    @Test
    public void testGetInstance() {
        String type = "test";

        MessageProviderSPI messageProviderSPI = MessageProviderFactory.getInstance(type);
        Assert.assertEquals(type, messageProviderSPI.getType());
        MessageProviderSPI messageProviderSPI2 = MessageProviderFactory.getInstance(type);
        Assert.assertEquals(messageProviderSPI, messageProviderSPI2);

        MessageSender messageSender = MessageSenderFactory.getInstance(type);
        MessageSender messageSender2 = MessageSenderFactory.getInstance(type);
        Assert.assertEquals(messageSender, messageSender2);
    }
}
