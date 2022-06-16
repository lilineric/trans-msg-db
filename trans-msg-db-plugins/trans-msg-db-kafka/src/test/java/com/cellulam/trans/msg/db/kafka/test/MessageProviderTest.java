package com.cellulam.trans.msg.db.kafka.test;

import com.cellulam.trans.msg.db.kafka.KafkaMQMessageProvider;
import com.cellulam.trans.msg.db.kafka.exceptions.MessageRoleException;
import com.google.common.collect.Lists;
import com.trans.db.facade.ConsumerRegister;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Callable;

/**
 * @author eric.li
 * @date 2022-06-12 23:33
 */
public class MessageProviderTest {


    private KafkaMQMessageProvider producerProvider;

    private KafkaMQMessageProvider consumerProvider;

    private final Object messageLock = new Object();
    private final Object ackLock = new Object();

    private volatile String message;
    private volatile String ackMessage;

    public MessageProviderTest() {
        producerProvider = new KafkaMQMessageProvider();
        producerProvider.init("test-producer", null, Lists.newArrayList("success"));
        producerProvider.registerMessageProducerProcessor(x -> {
            synchronized (ackLock) {
                ackMessage = x;
            }
            return true;
        });
        producerProvider.start();

        ConsumerRegister consumerRegister = new ConsumerRegister();
        consumerRegister.setConsumer("test-consumer");
        consumerRegister.setProducer("test-producer");
        consumerRegister.setTransType("success");

        consumerProvider = new KafkaMQMessageProvider();
        consumerProvider.init("test-consumer", Lists.newArrayList(consumerRegister), null);
        consumerProvider.registerMessageConsumerProcessor(x -> {
            synchronized (messageLock) {
                message = x;
            }
            return true;
        });
        consumerProvider.start();
    }

    @Test(expected = MessageRoleException.class)
    public void testNonConsumer() {
        producerProvider.getConsumerMsgSender();
    }

    @Test(expected = MessageRoleException.class)
    public void testNonProducer() {
        consumerProvider.getProducerMsgSender();
    }

    @Test
    public void testSend() throws Exception {
        Thread.sleep(100);
        String msg = "test message from producer " + System.currentTimeMillis();
        producerProvider.getProducerMsgSender().send("test-producer", "success", msg);
        waitNotNull(() -> {
            synchronized (messageLock) {
                return message != null;
            }
        });
        Assert.assertEquals(msg, message);
    }

    @Test
    public void testSendAck() throws Exception {
        Thread.sleep(100);
        String msg = "test ack from consumer " + System.currentTimeMillis();
        consumerProvider.getConsumerMsgSender().send("test-producer", "success", msg);
        waitNotNull(() -> {
            synchronized (ackLock) {
                return ackMessage != null;
            }
        });
        Assert.assertEquals(msg, ackMessage);
    }

    private void waitNotNull(Callable<Boolean> callable) throws Exception {
        long start = System.currentTimeMillis();
        while (!callable.call() && System.currentTimeMillis() - start < (30 * 1000)) {
            Thread.sleep(300);
        }
    }

}
