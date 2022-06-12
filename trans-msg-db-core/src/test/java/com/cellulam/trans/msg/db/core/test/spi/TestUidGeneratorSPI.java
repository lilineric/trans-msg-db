package com.cellulam.trans.msg.db.core.test.spi;

import com.cellulam.trans.msg.db.core.spi.UidGeneratorSPI;

import java.util.UUID;

/**
 * @author eric.li
 * @date 2022-06-12 22:15
 */
public class TestUidGeneratorSPI implements UidGeneratorSPI {
    @Override
    public String nextId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getType() {
        return "uuid";
    }
}
