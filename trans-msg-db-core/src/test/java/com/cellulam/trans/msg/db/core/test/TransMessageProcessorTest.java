package com.cellulam.trans.msg.db.core.test;

import com.cellulam.trans.msg.db.core.message.MessageProcessor;
import com.cellulam.trans.msg.db.core.message.TransMessageProcessor;
import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import com.cellulam.trans.msg.db.core.message.model.TransMessageHeader;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * @author eric.li
 * @date 2022-06-12 02:56
 */
public class TransMessageProcessorTest {
    private MessageProcessor messageProcessor = new TransMessageProcessor();

    @Test
    public void testProcess() {
        TransMessage message = new TransMessage();

        TransMessageHeader header = new TransMessageHeader();
        header.setTransId(UUID.randomUUID().toString());

        message.setHeader(header);
        message.setBody("test process message");

        boolean result = messageProcessor.process(message);
        Assert.assertTrue(result);
    }
}
