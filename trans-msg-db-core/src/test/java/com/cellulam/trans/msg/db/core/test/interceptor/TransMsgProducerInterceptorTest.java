package com.cellulam.trans.msg.db.core.test.interceptor;

import com.cellulam.trans.msg.db.core.test.interceptor.service.OrderService;
import org.junit.Test;

/**
 * @author eric.li
 * @date 2022-06-09 22:56
 */
public class TransMsgProducerInterceptorTest {

    @Test
    public void testInterceptTransMsgProducer() {
        new OrderService().test();
    }
}
