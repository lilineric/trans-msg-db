package com.cellulam.trans.msg.db.core.test;

import com.cellulam.trans.msg.db.core.message.TransMessageSender;
import com.cellulam.trans.msg.db.core.test.configuration.BaseInitiatedTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author eric.li
 * @date 2022-06-12 02:58
 */
public class TransMessageSenderTest extends BaseInitiatedTest {
    private TransMessageSender messageSender = new TransMessageSender();

    @Test
    public void testSend() {
        String transId = messageSender.send("order-success", "Test1");
        String transId2 = messageSender.send("order-success","Test2");
        Assert.assertNotEquals(transId, transId2);
    }
}
