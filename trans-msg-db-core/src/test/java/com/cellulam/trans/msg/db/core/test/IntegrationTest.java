package com.cellulam.trans.msg.db.core.test;

import com.cellulam.msg.db.test.utils.TestUtils;
import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.conf.TransMsgInitializer;
import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.coordinator.TransCoordinator;
import com.cellulam.trans.msg.db.core.factories.MessageProviderFactory;
import com.cellulam.trans.msg.db.core.factories.MessageSenderFactory;
import com.cellulam.trans.msg.db.core.factories.RepositoryFactory;
import com.cellulam.trans.msg.db.core.message.ConsumerProcessorWrap;
import com.cellulam.trans.msg.db.core.message.TransMessageSender;
import com.cellulam.trans.msg.db.core.test.mock.storage.MockDB;
import com.cellulam.trans.msg.db.core.test.mock.storage.MockMQ;
import com.cellulam.trans.msg.db.core.test.spi.TestMessageProviderSPI;
import com.cellulam.trans.msg.db.core.test.spi.TestMessageSender;
import com.cellulam.trans.msg.db.spi.RepositorySPI;
import com.cellulam.trans.msg.db.spi.contract.MessageSender;
import com.trans.db.facade.AbstractTransMessageProcessor;
import com.trans.db.facade.TransMessageProcessor;
import com.trans.db.facade.Transaction;
import com.trans.db.facade.enums.TransProcessResult;
import com.trans.db.facade.enums.TransStage;
import com.trans.db.facade.enums.TransStatus;
import lombok.Data;
import lombok.ToString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author eric.li
 * @date 2022-06-13 15:28
 */
public class IntegrationTest {
    private RepositorySPI repositorySPI = RepositoryFactory.getInstance("mysql");

    @Before
    public void before() {
        MockMQ.clear();
    }

    void reset(String appName) throws NoSuchFieldException, IllegalAccessException {
        reset(appName, 30, 120);
    }

    void reset(String appName, long recoverExecPeriodSeconds, long recoverFixPeriodSeconds) throws NoSuchFieldException, IllegalAccessException {
        Field field = TransContext.getContext().getClass().getDeclaredField("initiated");
        field.setAccessible(true);
        field.set(TransContext.getContext(), false);

        TransConfiguration transConfiguration = new TransConfiguration();
        transConfiguration.setAppName(appName);
        transConfiguration.setMessageProviderType("test");
        transConfiguration.setRepositoryType("mysql");
        transConfiguration.setSerializeType("json");
        transConfiguration.setDynamicConfigType("properties");
        transConfiguration.setDataSource(MockDB.dataSource);
        transConfiguration.setUidGeneratorType("uuid");
        transConfiguration.setMessageSendThreadPoolSize(1);
        transConfiguration.setRecoverExecPeriodSeconds(recoverExecPeriodSeconds);
        transConfiguration.setRecoverFixPeriodSeconds(recoverFixPeriodSeconds);

        TransMsgInitializer.init(transConfiguration);

        registerConsumerProcessor();

        TransMsgInitializer.start();
    }

    public static void registerConsumerProcessor() {
        TransMsgInitializer.registerConsumerProcessor(new OrderTransMessageProcessor());
    }

    @Test
    public void testUniqueIgnore() throws Exception {
        reset("order");

        Transaction transaction = TestUtils.randomBean(Transaction.class);
        repositorySPI.registerBranchTrans("test123", "test", transaction);
        repositorySPI.registerBranchTrans("test123", "test", transaction);
        repositorySPI.registerBranchTrans("test123", "test12", transaction);
    }

    @Test
    public void testConsumerProcessorWrap() throws NoSuchFieldException, IllegalAccessException {
        reset("order");

        OrderTransMessageProcessor orderTransMessageProcessor = new OrderTransMessageProcessor();
        ConsumerProcessorWrap<Order> orderConsumerProcessorWrap = TransMsgInitializer.registerConsumerProcessor(orderTransMessageProcessor);
        Assert.assertEquals(Order.class, orderConsumerProcessorWrap.getBodyClass());
        Assert.assertEquals("order", orderConsumerProcessorWrap.getProducer());
        Assert.assertEquals("order-success", orderConsumerProcessorWrap.getTransType());
        Assert.assertEquals(orderTransMessageProcessor, orderConsumerProcessorWrap.getProcessor());
    }

    @Test
    public void test() throws Exception {
        reset("order");
        String transId = this.sendMessage("order-success");

        reset("coupon");
        MessageProviderFactory.getInstance("test")
                .start();

        Transaction transaction = repositorySPI.getTrans(transId);
        Assert.assertNull(transaction);

    }

    @Test
    public void testRecover() throws Exception {
        reset("order", 1, 120);

        MessageSender mockMessageSender = Mockito.mock(MessageSender.class);

        Mockito.doThrow(new RuntimeException("test recover"))
                .when(mockMessageSender)
                .send(Mockito.eq("order"), Mockito.anyString(), Mockito.anyString());

        Field producerMessageSender = TransCoordinator.instance.getClass().getDeclaredField("producerMessageSender");
        producerMessageSender.setAccessible(true);
        producerMessageSender.set(TransCoordinator.instance, mockMessageSender);


        // send message fail, recover retry to send again
        String transId = this.sendMessage("order-success");

        // restore message sender
        producerMessageSender.set(TransCoordinator.instance, MessageSenderFactory.getProducerSender(TransContext.getConfiguration().getMessageProviderType()));
        Thread.sleep(1100);

        reset("coupon");
        MessageProviderFactory.getInstance("test")
                .start();

        Transaction transaction = repositorySPI.getTrans(transId);
        Assert.assertNull(transaction);

    }

    @Test
    public void testACKTimeout() throws Exception {
        reset("order", 1, 1);

        MessageSender mockMessageSender = Mockito.mock(TestMessageSender.class);

        Mockito.doThrow(new RuntimeException("test ack failed"))
                .when(mockMessageSender)
                .send(Mockito.eq("coupon"), Mockito.anyString(), Mockito.anyString());

        Field messageConsumerSender = TestMessageProviderSPI.class.getDeclaredField("messageConsumerSender");
        messageConsumerSender.setAccessible(true);
        messageConsumerSender.set(MessageProviderFactory.getInstance("test"), mockMessageSender);


        // send message fail, recover retry to send again
        String transId = this.sendMessage("order-success");

        // restore message sender
        messageConsumerSender.set(MessageProviderFactory.getInstance("test"), new TestMessageSender(TransStage.ACK));
        Thread.sleep(1100);

        reset("coupon");
        MessageProviderFactory.getInstance("test")
                .start();

        Transaction transaction = repositorySPI.getTrans(transId);
        Assert.assertNull(transaction);

    }

    private String sendMessage(String transType) {
        TransMessageSender messageSender = new TransMessageSender();
        Order order = new Order();
        order.setId(1L);
        order.setUid(2L);
        order.setTitle("success-scenario");
        order.setStatus("success");
        order.setAmount(1234L);

        String transId = messageSender.send(transType, order);
        Transaction transaction = repositorySPI.getTrans(transId);
        if(transaction != null) {
            Assert.assertEquals(transId, transaction.getTransId());
            Assert.assertEquals(TransStatus.SENDING.name(), transaction.getStatus());
        }
        System.out.println(transaction);
        return transId;
    }

    public final static class OrderTransMessageProcessor extends AbstractTransMessageProcessor<Order> implements TransMessageProcessor<Order> {
        @Override
        public String getProducer() {
            return "order";
        }

        @Override
        public String getTransType() {
            return "order-success";
        }

        @Override
        public TransProcessResult process(Order body) {
            System.out.println(body);
            return TransProcessResult.SUCCESS;
        }
    }

    @Data
    @ToString
    public final static class Order implements Serializable {
        private Long id;
        private Long uid;
        private String title;
        private String status;
        private Long amount;
    }

}
