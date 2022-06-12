package com.cellulam.msg.db.serialize.json;

import com.cellulam.trans.msg.db.core.spi.SerializeSPI;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-12 11:33
 */
public class JsonSerialize implements SerializeSPI {
    @Override
    public <T extends Serializable> String serialize(T object) {
        return JacksonUtils.toJson(object);
    }

    @Override
    public <T extends Serializable> T deserialize(String str, Class<T> clz) {
        return JacksonUtils.toObj(str, clz);
    }

    @Override
    public <T extends Serializable> T deserializeByNestedClass(String str, Class<T> objClass, Class<?>... nestParameterizedClasses) {
        return JacksonUtils.toObjByNestRef(str, objClass, nestParameterizedClasses);
    }

    @Override
    public String getType() {
        return "json";
    }
}
