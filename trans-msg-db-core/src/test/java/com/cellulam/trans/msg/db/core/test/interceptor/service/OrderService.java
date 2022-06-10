package com.cellulam.trans.msg.db.core.test.interceptor.service;

import com.cellulam.trans.msg.db.facade.anotation.TransMsgProducer;

/**
 * @author eric.li
 * @date 2022-06-09 22:57
 */
public class OrderService {

    @TransMsgProducer
    public void test() {
        System.out.println("test");
    }
}
