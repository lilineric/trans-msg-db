package com.cellulam.msg.db.serialize.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LittleType implements ParameterizedType {

    private final Type[] actualTypeArguments;
    private final Class<?> rawType;
    private final Type ownerType;

    public static LittleType tiny(Type actualTypeArguments, Class<?> rawType) {
        return new LittleType(new Type[]{actualTypeArguments}, rawType, null);
    }

}
