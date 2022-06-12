package com.cellulam.trans.msg.db.core.factories;

import com.cellulam.trans.msg.db.core.spi.UidGeneratorSPI;
import com.cellulam.trans.msg.db.spi.SingletonTypeSPIInstanceFactory;

/**
 * @author eric.li
 * @date 2022-06-12 10:45
 */
public abstract class UidGeneratorFactory {
    static {
        SingletonTypeSPIInstanceFactory.register(UidGeneratorSPI.class);
    }

    public static UidGeneratorSPI getInstance(final String type) {
        return SingletonTypeSPIInstanceFactory.getInstance(type, UidGeneratorSPI.class);
    }
}
