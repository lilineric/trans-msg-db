package com.cellulam.trans.msg.db.sample.order;

import com.cellulam.trans.msg.db.sample.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eric.li
 * @date 2022-06-16 13:03
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/test/place-order")
    public String placeOrder() {
        long id = orderService.placeOrder();
        return "success, orderId: " + id;
    }
}
