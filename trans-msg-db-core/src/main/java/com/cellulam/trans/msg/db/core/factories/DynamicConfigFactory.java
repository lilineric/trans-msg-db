package com.cellulam.trans.msg.db.core.factories;

import com.cellulam.trans.msg.db.core.spi.DynamicConfigSPI;
import com.cellulam.trans.msg.db.spi.SingletonTypeSPIInstanceFactory;

/**
 * @author eric.li
 * @date 2022-06-12 10:45
 */
public abstract class DynamicConfigFactory {
    static {
        SingletonTypeSPIInstanceFactory.register(DynamicConfigSPI.class);
    }

    public static DynamicConfigSPI getInstance(final String type) {
        return SingletonTypeSPIInstanceFactory.getInstance(type, DynamicConfigSPI.class);
    }
}
