package com.cellulam.trans.msg.db.sample.order.dao;

import com.cellulam.trans.msg.db.sample.order.po.OrderBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author eric.li
 * @date 2022-06-10 00:33
 */
@Repository
public interface OrderDao {
    int insertOrder(OrderBean order);

    void updateStatus(@Param("id") long id,
                      @Param("status") String status);
}
