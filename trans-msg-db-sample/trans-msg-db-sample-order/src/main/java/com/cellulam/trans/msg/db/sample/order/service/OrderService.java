package com.cellulam.trans.msg.db.sample.order.service;

import com.cellulam.trans.db.sample.common.enums.OrderStatus;
import com.cellulam.trans.db.sample.common.utils.ThreadUtils;
import com.cellulam.trans.db.sample.common.utils.UUIDUtils;
import com.cellulam.trans.msg.db.sample.order.dao.OrderDao;
import com.cellulam.trans.msg.db.sample.order.po.OrderBean;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author eric.li
 * @date 2022-06-10 00:41
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    @Transactional
    public void placeOrder() {
        OrderBean order = new OrderBean();
        order.setUid(RandomUtils.nextLong());
        order.setTitle("title-" + UUIDUtils.randomUUID32());
        order.setStatus(OrderStatus.INIT.name());
        order.setAmount(RandomUtils.nextLong(100, 50000));

        new BigDecimal(12).add(BigDecimal.TEN);

        orderDao.insertOrder(order);
        ThreadUtils.sleep(1000);
        orderDao.updateStatus(order.getId(), OrderStatus.SUCCESS.name());
    }
}
