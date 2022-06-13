package com.trans.msg.db.uuid;

import com.cellulam.trans.msg.db.spi.UidGeneratorSPI;

import java.util.UUID;

/**
 * @author eric.li
 * @date 2022-06-13 22:05
 */
public class UUIDGenerator implements UidGeneratorSPI {
    @Override
    public String nextId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    @Override
    public String getType() {
        return "uuid";
    }
}
