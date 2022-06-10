package com.cellulam.trans.msg.db.sample.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.cellulam.trans.msg.db.sample.order.dao")
@EnableAspectJAutoProxy
public class OrderStarter {
    public static void main(String[] args) {
        SpringApplication.run(OrderStarter.class);
    }
}
