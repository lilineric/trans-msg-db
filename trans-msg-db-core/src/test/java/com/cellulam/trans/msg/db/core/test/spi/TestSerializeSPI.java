package com.cellulam.trans.msg.db.core.test.spi;

import com.cellulam.trans.msg.db.core.spi.SerializeSPI;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-12 12:17
 */
public class TestSerializeSPI implements SerializeSPI {
    @Override
    public <T extends Serializable> String serialize(T object) {
        return object.toString();
    }

    @Override
    public <T extends Serializable> T deserialize(String str, Class<T> clz) {
        try {
            return clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T extends Serializable> T deserializeByNestedClass(String str, Class<T> objClass, Class<?>... nestParameterizedClasses) {
        return deserialize(str, objClass);
    }

    @Override
    public String getType() {
        return "test";
    }
}
