package com.cellulam.msg.db.serialize.json;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.ParameterizedType;

@Getter
@Builder
@AllArgsConstructor
public class TypeRef extends TypeReference<Object> {

    private ParameterizedType type;
}
