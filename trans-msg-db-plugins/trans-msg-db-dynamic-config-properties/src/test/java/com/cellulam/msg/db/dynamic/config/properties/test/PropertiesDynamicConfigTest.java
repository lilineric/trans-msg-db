package com.cellulam.msg.db.dynamic.config.properties.test;

import com.cellulam.msg.db.dynamic.config.properties.PropertiesDynamicConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author eric.li
 * @date 2022-06-12 23:33
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.cellulam.msg.db.dynamic.config.properties")
public class PropertiesDynamicConfigTest {
    @Autowired
    private PropertiesDynamicConfig dynamicConfig;

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
}
