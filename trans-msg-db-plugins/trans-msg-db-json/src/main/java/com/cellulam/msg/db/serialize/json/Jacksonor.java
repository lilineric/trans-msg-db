package com.cellulam.msg.db.serialize.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Jacksonor {

    private static final Logger logger = LoggerFactory.getLogger(Jacksonor.class);

    private static Jacksonor generic = new Jacksonor(createMapper());
    private static Jacksonor power;
    private static Jacksonor noNull;

    public static Jacksonor power() {
        if (power != null) {
            return power;
        }
        synchronized (logger) {
            if (power == null) {
                ObjectMapper mapper = createMapper();
                power = new Jacksonor(mapper);
            }
        }
        return power;
    }

    /**
     * ignore null
     *
     * @return
     */
    public static Jacksonor noNull() {
        if (noNull != null) {
            return noNull;
        }
        synchronized (logger) {
            if (noNull == null) {
                ObjectMapper mapper = createMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                noNull = new Jacksonor(mapper);
            }
        }
        return noNull;
    }

    public static Jacksonor generic() {
        return generic;
    }

    private ObjectMapper mapper;

    private Jacksonor(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * to json
     *
     * @param obj
     * @return
     */
    public String toJson(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * to obj
     *
     * @param jsonText
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T toObj(String jsonText, Class<T> clz) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }
        try {
            return mapper.readValue(jsonText, clz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * to object
     *
     * @param map
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T toObj(Map map, Class<T> clz) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            return mapper.convertValue(map, clz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * to map
     *
     * @param jsonText
     * @return
     */
    public Map toMap(String jsonText) {
        return toObj(jsonText, LinkedHashMap.class);
    }

    /**
     * to list
     *
     * @param jsonText
     * @return
     */
    public List toList(String jsonText) {
        return toObj(jsonText, ArrayList.class);
    }

    /**
     * to array
     *
     * @param jsonText
     * @return
     */
    public Object[] toArray(String jsonText) {
        return toObj(jsonText, Object[].class);
    }

    /**
     * to map
     *
     * @param jsonText   json string
     * @param keyClass   the class type of key
     * @param valueClass the class type of value
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> Map<K, V> toMap(String jsonText, Class<K> keyClass, Class<V> valueClass) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }
        JavaType type = mapper.getTypeFactory().constructMapLikeType(LinkedHashMap.class, keyClass, valueClass);
        return readByJavaType(jsonText, type);
    }

    /**
     * to array
     *
     * @param jsonText
     * @param elementClass the class type of elements
     * @param <T>
     * @return
     */
    public <T> T[] toArray(String jsonText, Class<T> elementClass) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }
        JavaType type = mapper.getTypeFactory().constructArrayType(elementClass);
        return readByJavaType(jsonText, type);
    }

    /**
     * to list
     *
     * @param jsonText
     * @param elementClass the class type of elements
     * @param <T>
     * @return
     */
    public <T> List<T> toList(String jsonText, Class<T> elementClass) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }
        JavaType type = mapper.getTypeFactory().constructCollectionLikeType(ArrayList.class, elementClass);
        return readByJavaType(jsonText, type);
    }

    /**
     * to object by reference
     *
     * @param jsonText
     * @param refer    type reference
     * @param <T>
     * @return
     */
    public <T> T toObjByReference(String jsonText, TypeReference<T> refer) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }
        try {
            return mapper.readValue(jsonText, refer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * to object by nested refrence
     * eg. DataResult&lt;List&lt;UserRequest&gt;&gt;
     *
     * @param jsonText
     * @param objClass
     * @param nestParameterizedClasses
     * @param <T>
     * @return
     */
    public <T> T toObjByNestRef(String jsonText, Class<T> objClass, Class<?>... nestParameterizedClasses) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }

        if (String.class.isAssignableFrom(objClass)) {
            return (T) jsonText;
        }

        try {
            LittleType type = null;

            if (ArrayUtils.isEmpty(nestParameterizedClasses)) {
                return toObj(jsonText, objClass);
            }

            if (nestParameterizedClasses.length == 1) {
                return toObjByGenericType(jsonText, objClass, nestParameterizedClasses[0]);
            }

            for (int i = nestParameterizedClasses.length - 1; i >= 0; i--) {
                if (type == null) {
                    type = LittleType.tiny(nestParameterizedClasses[i], nestParameterizedClasses[--i]);
                } else {
                    type = LittleType.tiny(type, nestParameterizedClasses[i]);
                }
            }
            type = LittleType.tiny(type, objClass);

            return mapper.readValue(jsonText, TypeRef.builder().type(type).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * to object
     *
     * @param jsonText       json string
     * @param objClass
     * @param genericClasses
     * @param <T>
     * @return
     */
    public <T> T toObjByGenericType(String jsonText, Class<T> objClass, Class<?>... genericClasses) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }
        if (String.class.isAssignableFrom(objClass)) {
            return (T) jsonText;
        }

        JavaType type = mapper.getTypeFactory().constructParametricType(objClass, genericClasses);
        return readByJavaType(jsonText, type);
    }

    public <T> T readByJavaType(String jsonText, JavaType javaType) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }
        try {
            return mapper.readValue(jsonText, javaType);
        } catch (Exception e) {
            logger.error("Parse Json by JavaType#{} error", javaType, e);
            return null;
        }
    }

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        mapper.configure(MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING, true);

        mapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.configure(MapperFeature.INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES, false);
        mapper.configure(MapperFeature.AUTO_DETECT_FIELDS, false);

        mapper.registerModule(new JodaModule());
        JavaTimeModule module = new JavaTimeModule();

        mapper.registerModule(module);

        return mapper;
    }
}
