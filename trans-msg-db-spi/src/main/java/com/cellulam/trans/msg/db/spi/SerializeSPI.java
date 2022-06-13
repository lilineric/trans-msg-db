package com.cellulam.trans.msg.db.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;

import java.io.Serializable;

/**
 * Serialize SPI
 * @author eric.li
 * @date 2022-06-12 11:23
 */
@SingletonSPI
public interface SerializeSPI extends TypeSPI {
    /**
     * serialize the object to string
     * @param object
     * @param <T>
     * @return
     */
    <T extends Serializable> String serialize(T object);

    /**
     * deserialize from string
     * @param str
     * @param clz
     * @param <T>
     * @return
     */
    <T extends Serializable> T deserialize(String str, Class<T> clz);

    /**
     * deserialize from string by nested classes
     * @param str
     * @param objClass
     * @param nestParameterizedClasses
     * @param <T>
     * @return
     */
    <T extends Serializable> T deserializeByNestedClass(String str, Class<T> objClass, Class<?>... nestParameterizedClasses);
}
