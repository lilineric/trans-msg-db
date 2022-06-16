package com.trans.msg.db.sample.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author eric.li
 * @date 2022-06-16 15:24
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MemberStarter {
    public static void main(String[] args) {
        SpringApplication.run(MemberStarter.class);
    }
}
