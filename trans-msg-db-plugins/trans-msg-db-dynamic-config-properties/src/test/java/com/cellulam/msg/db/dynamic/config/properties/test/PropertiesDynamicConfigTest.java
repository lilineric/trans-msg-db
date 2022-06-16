package com.cellulam.msg.db.dynamic.config.properties.test;

import com.cellulam.msg.db.dynamic.config.properties.PropertiesDynamicConfig;
import com.trans.db.facade.ConsumerRegister;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author eric.li
 * @date 2022-06-12 23:33
 */
public class PropertiesDynamicConfigTest {
    private PropertiesDynamicConfig dynamicConfig = new PropertiesDynamicConfig();

    @Test
    public void testGetConsumers() {
        List<String> consumers = dynamicConfig.getConsumers("order-success", "order");
        Assert.assertEquals("coupon", consumers.get(0));
        Assert.assertEquals("member", consumers.get(1));
        System.out.println(consumers);

         consumers = dynamicConfig.getConsumers("order-cancel", "Order");
        Assert.assertEquals("inventory", consumers.get(0));
        Assert.assertEquals("member", consumers.get(1));
        System.out.println(consumers);
    }

    @Test
    public void testGetRegisters() {
        List<ConsumerRegister> coupons = dynamicConfig.getRegistersByConsumer("coupon");
        List<ConsumerRegister> inventories = dynamicConfig.getRegistersByConsumer("inventory");
        List<ConsumerRegister> members = dynamicConfig.getRegistersByConsumer("member");

        Assert.assertEquals(1, coupons.size());
        Assert.assertEquals(1, inventories.size());
        Assert.assertEquals(2, members.size());

        Assert.assertEquals("order", coupons.get(0).getProducer());
        Assert.assertEquals("order", inventories.get(0).getProducer());
        Assert.assertEquals("order", members.get(0).getProducer());
        Assert.assertEquals("order", members.get(1).getProducer());

        Assert.assertEquals("order-success", coupons.get(0).getTransType());
        Assert.assertEquals("order-cancel", inventories.get(0).getTransType());
        Assert.assertEquals("order-success", members.get(0).getTransType());
        Assert.assertEquals("order-cancel", members.get(1).getTransType());
    }

    @Test
    public void testGetTransTypes() {
        List<String> orders = dynamicConfig.getTransTypes("order");
        Assert.assertEquals(2, orders.size());
        Assert.assertEquals("order-success", orders.get(0));
        Assert.assertEquals("order-cancel", orders.get(1));
    }
}
