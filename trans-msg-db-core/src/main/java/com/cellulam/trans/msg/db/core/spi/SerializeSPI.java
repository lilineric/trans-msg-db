package com.cellulam.trans.msg.db.core.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-12 11:23
 */
@SingletonSPI
public interface SerializeSPI extends TypeSPI {
    <T extends Serializable> String serialize(T object);

    <T extends Serializable> T deserialize(String str, Class<T> clz);

    <T extends Serializable> T deserializeByNestedClass(String str, Class<T> objClass, Class<?>... nestParameterizedClasses);
}
