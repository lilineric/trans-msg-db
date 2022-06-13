package com.cellulam.trans.msg.db.core.test;

import com.cellulam.trans.msg.db.core.message.TransMessageSender;
import com.cellulam.trans.msg.db.core.test.configuration.BaseInitiatedTest;
import lombok.Data;
import org.junit.Test;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-13 15:28
 */
public class ProducerTest extends BaseInitiatedTest {

    @Test
    public void test() {
        TransMessageSender messageSender = new TransMessageSender();
        Order order = new Order();
        order.setId(1L);
        order.setUid(2L);
        order.setTitle("success-scenario");
        order.setStatus("success");
        order.setAmount(1234L);

        messageSender.send("order-success", order);
    }

    @Data
    public final static class Order implements Serializable {
        private Long id;
        private Long uid;
        private String title;
        private String status;
        private Long amount;
    }

}
