package com.cellulam.trans.msg.db.sample.order.service;

import com.cellulam.trans.db.sample.common.dto.OrderDto;
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
    public long placeOrder() {
        OrderBean order = new OrderBean();
        order.setUid(RandomUtils.nextLong());
        order.setTitle("title-" + UUIDUtils.randomUUID32());
        order.setStatus(OrderStatus.INIT.name());
        order.setAmount(RandomUtils.nextLong(100, 50000));

        orderDao.insertOrder(order);
        ThreadUtils.sleep(RandomUtils.nextLong(10, 1000));
        orderDao.updateStatus(order.getId(), OrderStatus.SUCCESS.name());

        transMessageSender.send("success", this.buildDto(order));
        return order.getId();
    }

    private OrderDto buildDto(OrderBean orderBean) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderBean.getId());
        orderDto.setUid(orderBean.getUid());
        orderDto.setTitle(orderBean.getTitle());
        orderDto.setStatus(orderBean.getStatus());
        orderDto.setAmount(orderBean.getAmount());
        orderDto.setCreated(orderBean.getCreated());
        orderDto.setModified(orderBean.getModified());
        return orderDto;
    }
}
