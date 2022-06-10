package com.cellulam.trans.msg.db.sample.order.runner;

import com.cellulam.trans.msg.db.sample.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author eric.li
 * @date 2022-06-10 00:46
 */
@Component
public class PlaceOrderStarter implements CommandLineRunner {
    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        orderService.placeOrder();
    }
}
