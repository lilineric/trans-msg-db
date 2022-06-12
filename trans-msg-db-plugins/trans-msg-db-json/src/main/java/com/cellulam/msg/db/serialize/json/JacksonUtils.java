package com.cellulam.msg.db.serialize.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Jackson-Json Util
 *
 * @author eric.li
 */
public abstract class JacksonUtils {

    private static final Jacksonor jacksoner = Jacksonor.generic();

    /**
     * to json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return jacksoner.toJson(obj);
    }

    /**
     * to object
     *
     * @param jsonText
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T toObj(String jsonText, Class<T> clz) {
        return jacksoner.toObj(jsonText, clz);
    }

    /**
     * to object
     *
     * @param map
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T toObj(Map map, Class<T> clz) {
        return jacksoner.toObj(map, clz);
    }

    /**
     * to map
     *
     * @param jsonText
     * @return
     */
    public static Map toMap(String jsonText) {
        return toObj(jsonText, LinkedHashMap.class);
    }

    /**
     * to list
     *
     * @param jsonText
     * @return
     */
    public static List toList(String jsonText) {
        return toObj(jsonText, ArrayList.class);
    }

    /**
     * to array
     *
     * @param jsonText
     * @return
     */
    public static Object[] toArray(String jsonText) {
        return toObj(jsonText, Object[].class);
    }

    /**
     * @param jsonText
     * @param keyClass
     * @param valueClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> toMap(String jsonText, Class<K> keyClass, Class<V> valueClass) {
        return jacksoner.toMap(jsonText, keyClass, valueClass);
    }

    /**
     * @param jsonText
     * @param elementClass
     * @param <T>
     * @return
     */
    public static <T> T[] toArray(String jsonText, Class<T> elementClass) {
        return jacksoner.toArray(jsonText, elementClass);
    }

    /**
     * to list
     *
     * @param jsonText
     * @param elementClass
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String jsonText, Class<T> elementClass) {
        return jacksoner.toList(jsonText, elementClass);
    }

    /**
     * @param jsonText
     * @param refer
     * @param <T>
     * @return
     */
    public static <T> T toObjByReference(String jsonText, TypeReference<T> refer) {
        return jacksoner.toObjByReference(jsonText, refer);
    }

    /**
     * @param jsonText
     * @param objClass
     * @param nestParameterizedClasses
     * @param <T>
     * @return
     */
    public static <T> T toObjByNestRef(String jsonText, Class<T> objClass, Class<?>... nestParameterizedClasses) {
        return jacksoner.toObjByNestRef(jsonText, objClass, nestParameterizedClasses);
    }

    /**
     * @param jsonText
     * @param objClass
     * @param genericClasses
     * @param <T>
     * @return
     */
    public static <T> T toObjByGenericType(String jsonText, Class<T> objClass, Class<?>... genericClasses) {
        return jacksoner.toObjByGenericType(jsonText, objClass, genericClasses);
    }

    public static <T> T readByJavaType(String jsonText, JavaType javaType) {
        return jacksoner.readByJavaType(jsonText, javaType);
    }


}
