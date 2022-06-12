package com.cellulam.trans.msg.db.core.test.spi;

import com.cellulam.trans.msg.db.core.spi.RepositorySPI;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author eric.li
 * @date 2022-06-12 12:23
 */
@Slf4j
public class TestRepositorySPI implements RepositorySPI {
    @Override
    public String insertTransMessage(String producer, String body) {
        log.info("insertTransMessage producer: {}, body: {}", producer, body);
        return UUID.randomUUID().toString();
    }

    @Override
    public String getType() {
        return "test";
    }
}
