package com.cellulam.trans.msg.db.core.test;

import com.cellulam.trans.msg.db.core.message.MessageProcessor;
import com.cellulam.trans.msg.db.core.message.MessageConsumerReceiveProcessor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author eric.li
 * @date 2022-06-12 02:56
 */
public class TransMessageProcessorTest {
    private MessageProcessor messageProcessor = new MessageConsumerReceiveProcessor();

    @Test
    public void testProcess() {
        boolean result = messageProcessor.process("test process message");
        Assert.assertTrue(result);
    }
}
