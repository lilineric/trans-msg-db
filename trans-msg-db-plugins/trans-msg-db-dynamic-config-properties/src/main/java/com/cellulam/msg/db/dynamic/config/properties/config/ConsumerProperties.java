package com.cellulam.msg.db.dynamic.config.properties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author eric.li
 * @date 2022-06-12 23:16
 */
@ConfigurationProperties(prefix = "spring.trans")
@Data
public class ConsumerProperties {
    /**
     * consumers
     * <p>
     * eg. <pre>{@code
     * spring:
     *   trans:
     *     consumers:
     *       order:
     *         order-success:
     *           - coupon
     *           - member
     *         order-cancel:
     *           - coupon
     *           - member
     *
     * }</pre>
     */
    private Map<String, Map<String, List<String>>> consumers;
}
