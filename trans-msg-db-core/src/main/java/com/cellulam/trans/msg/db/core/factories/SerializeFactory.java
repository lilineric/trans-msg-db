package com.cellulam.trans.msg.db.core.factories;

import com.cellulam.trans.msg.db.core.spi.SerializeSPI;
import com.cellulam.trans.msg.db.spi.SingletonTypeSPIInstanceFactory;

/**
 * @author eric.li
 * @date 2022-06-12 10:45
 */
public abstract class SerializeFactory {
    static {
        SingletonTypeSPIInstanceFactory.register(SerializeSPI.class);
    }

    public static SerializeSPI getInstance(final String type) {
        return SingletonTypeSPIInstanceFactory.getInstance(type, SerializeSPI.class);
    }
}
