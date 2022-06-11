package com.cellulam.trans.msg.db.core.test;

import com.cellulam.trans.msg.db.core.message.TransMessageSender;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author eric.li
 * @date 2022-06-12 02:58
 */
public class TransMessageSenderTest {
    private TransMessageSender messageSender = new TransMessageSender();

    @Test
    public void testSend() {
        String transId = messageSender.send("Test1");
        String transId2 = messageSender.send("Test2");
        Assert.assertNotEquals(transId, transId2);
    }
}
