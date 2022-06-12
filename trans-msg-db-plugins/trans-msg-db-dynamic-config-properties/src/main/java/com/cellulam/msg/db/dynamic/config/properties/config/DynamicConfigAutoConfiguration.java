package com.cellulam.msg.db.dynamic.config.properties.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author eric.li
 * @date 2022-06-12 23:25
 */
@Configuration
@EnableConfigurationProperties({ConsumerProperties.class})
public class DynamicConfigAutoConfiguration {

}
