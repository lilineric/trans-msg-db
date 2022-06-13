package com.cellulam.trans.msg.db.core.test.spi;

import com.cellulam.trans.msg.db.spi.DynamicConfigSPI;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author eric.li
 * @date 2022-06-12 23:49
 */
public class TestDynamicConfigSPI implements DynamicConfigSPI {
    @Override
    public List<String> getConsumers(String transType, String producer) {
        return Lists.newArrayList("coupon");
    }

    @Override
    public String getType() {
        return "test";
    }
}
