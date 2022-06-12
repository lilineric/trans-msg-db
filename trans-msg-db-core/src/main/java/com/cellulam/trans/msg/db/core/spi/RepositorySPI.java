package com.cellulam.trans.msg.db.core.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;

/**
 * @author eric.li
 * @date 2022-06-12 10:42
 */
@SingletonSPI
public interface RepositorySPI extends TypeSPI {

    String insertTransMessage(String producer, String body);
}
