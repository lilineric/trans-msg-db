package com.cellulam.trans.msg.db.core.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;

import java.util.List;

/**
 * @author eric.li
 * @date 2022-06-12 22:24
 */
@SingletonSPI
public interface DynamicConfigSPI extends TypeSPI {
    List<String> getConsumers(String transType, String producer);
}
