package com.cellulam.trans.msg.db.sample.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cellulam.trans.msg.db.sample.order.dao")
public class OrderStarter {
    public static void main(String[] args) {
        SpringApplication.run(OrderStarter.class);
    }
}
