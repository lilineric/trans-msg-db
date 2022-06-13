package com.cellulam.trans.msg.db.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;

/**
 * Unique ID generator SPI
 * @author eric.li
 * @date 2022-06-12 21:41
 */
@SingletonSPI
public interface UidGeneratorSPI extends TypeSPI {
    /**
     * get unique ID
     * @return
     */
    String nextId();
}
