package com.cellulam.trans.msg.db.core.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;

/**
 * @author eric.li
 * @date 2022-06-12 21:41
 */
@SingletonSPI
public interface UidGeneratorSPI extends TypeSPI {
    String nextId();
}
