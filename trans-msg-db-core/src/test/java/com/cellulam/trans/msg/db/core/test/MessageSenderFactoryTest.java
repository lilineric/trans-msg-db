package com.cellulam.trans.msg.db.core.test;

import com.cellulam.trans.msg.db.core.factories.MessageProviderFactory;
import com.cellulam.trans.msg.db.core.factories.MessageSenderFactory;
import com.cellulam.trans.msg.db.spi.MessageProviderSPI;
import com.cellulam.trans.msg.db.spi.contract.MessageSender;
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

        MessageSender messageSender = MessageSenderFactory.getConsumerSender(type);
        MessageSender messageSender2 = MessageSenderFactory.getConsumerSender(type);

        MessageSender messageProducerSender = MessageSenderFactory.getProducerSender(type);
        MessageSender messageProducerSender2 = MessageSenderFactory.getProducerSender(type);

        Assert.assertEquals(messageSender, messageSender2);
        Assert.assertEquals(messageProducerSender, messageProducerSender2);
        Assert.assertNotEquals(messageSender, messageProducerSender);
    }
}
