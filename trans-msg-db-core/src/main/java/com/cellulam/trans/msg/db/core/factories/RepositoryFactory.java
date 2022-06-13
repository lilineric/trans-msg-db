package com.cellulam.trans.msg.db.core.factories;

import com.cellulam.trans.msg.db.spi.RepositorySPI;
import com.cellulam.trans.msg.db.spi.loader.SingletonTypeSPIInstanceFactory;

/**
 * @author eric.li
 * @date 2022-06-12 10:45
 */
public abstract class RepositoryFactory {
    static {
        SingletonTypeSPIInstanceFactory.register(RepositorySPI.class);
    }

    public static RepositorySPI getInstance(final String type) {
        return SingletonTypeSPIInstanceFactory.getInstance(type, RepositorySPI.class);
    }
}
