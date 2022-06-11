package com.cellulam.trans.msg.db.sample.order.service;

import com.cellulam.trans.db.sample.common.enums.OrderStatus;
import com.cellulam.trans.db.sample.common.utils.ThreadUtils;
import com.cellulam.trans.db.sample.common.utils.UUIDUtils;
import com.cellulam.trans.msg.db.core.message.TransMessageSender;
import com.cellulam.trans.msg.db.sample.order.dao.OrderDao;
import com.cellulam.trans.msg.db.sample.order.po.OrderBean;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author eric.li
 * @date 2022-06-10 00:41
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private TransMessageSender transMessageSender;

    @Transactional
    public void placeOrder() {
        OrderBean order = new OrderBean();
        order.setUid(RandomUtils.nextLong());
        order.setTitle("title-" + UUIDUtils.randomUUID32());
        order.setStatus(OrderStatus.INIT.name());
        order.setAmount(RandomUtils.nextLong(100, 50000));

        orderDao.insertOrder(order);
        ThreadUtils.sleep(1000);
        orderDao.updateStatus(order.getId(), OrderStatus.SUCCESS.name());

        transMessageSender.send(order);
    }
}
