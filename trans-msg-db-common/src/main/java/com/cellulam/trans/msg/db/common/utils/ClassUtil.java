package com.cellulam.trans.msg.db.common.utils;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class ClassUtil {

    private static final Map<Class<?>, Class<?>> primitiveMap = new HashMap<>(9);


    static {
        primitiveMap.put(String.class, String.class);
        primitiveMap.put(Boolean.class, boolean.class);
        primitiveMap.put(Byte.class, byte.class);
        primitiveMap.put(Character.class, char.class);
        primitiveMap.put(Double.class, double.class);
        primitiveMap.put(Float.class, float.class);
        primitiveMap.put(Integer.class, int.class);
        primitiveMap.put(Long.class, long.class);
        primitiveMap.put(Short.class, short.class);
        primitiveMap.put(Date.class, Date.class);

        primitiveMap.put(LocalDate.class, LocalDate.class);
        primitiveMap.put(LocalDateTime.class, LocalDateTime.class);

        primitiveMap.put(Class.class, Class.class);
        primitiveMap.put(Locale.class, Locale.class);
        primitiveMap.put(URI.class, URI.class);
        primitiveMap.put(URL.class, URL.class);

        primitiveMap.put(BigDecimal.class, BigDecimal.class);

    }


    /**
     * Get the actual Class of the parent class' generic type
     *
     * @param clazz
     * @return
     */
    public static Class<?>[] getSuperClassGenericType(final Class<?> clazz) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return new Class[]{Object.class};
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class[] classes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            classes[i] = (Class<?>) params[i];
        }
        return classes;
    }

    /**
     * Get the actual Class of the parent class' generic type
     *
     * @param clazz
     * @param index
     * @return
     */
    public static Class getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class<?>) params[index];
    }

    private static boolean _isSimpleValueType(Class<?> clazz) {
        return (ClassUtils.isPrimitiveOrWrapper(clazz) || clazz.isEnum() ||
                CharSequence.class.isAssignableFrom(clazz) ||
                Number.class.isAssignableFrom(clazz) ||
                Date.class.isAssignableFrom(clazz) ||
                URI.class == clazz || URL.class == clazz ||
                Locale.class == clazz || Class.class == clazz);
    }


    public static boolean isSimpleValueType(Class<?> clazz) {
        return _isSimpleValueType(clazz);
    }


    /**
     * @param clazz
     * @return boolean
     * @description is primitive class
     */
    public static boolean isPrimitive(Class<?> clazz) {
        if (primitiveMap.containsKey(clazz)) {
            return true;
        }
        return clazz.isEnum() || clazz.isPrimitive() || _isSimpleValueType(clazz);
    }

    /**
     * get primitive class
     *
     * @param clzz
     * @return
     */
    public static Class<?> getPrimitive(Class<?> clzz) {
        return primitiveMap.get(clzz);
    }

    /**
     * @param target
     * @param fieldName
     * @return Class<?>
     * @description get element type
     */
    public static Class<?> getElementType(Class<?> target, String fieldName) {
        Class<?> elementTypeClass = null;
        try {
            Type type = target.getDeclaredField(fieldName).getGenericType();
            ParameterizedType t = (ParameterizedType) type;
            String classStr = t.getActualTypeArguments()[0].toString().replace("class ", "");
            elementTypeClass = Thread.currentThread().getContextClassLoader().loadClass(classStr);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
            try {
                elementTypeClass = getElementTypeUppercase(target, fieldName);
            } catch (Exception e2) {
                throw new RuntimeException("get fieldName[" + fieldName + "] error", e2);
            }
        }
        return elementTypeClass;
    }

    public static Class<?> getElementTypeUppercase(Class<?> target, String fieldName) {
        Class<?> elementTypeClass = null;
        try {
            Type type = target.getDeclaredField(fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)).getGenericType();
            ParameterizedType t = (ParameterizedType) type;
            String classStr = t.getActualTypeArguments()[0].toString().replace("class ", "");
            elementTypeClass = Thread.currentThread().getContextClassLoader().loadClass(classStr);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("get fieldName[" + fieldName + "] error", e);
        }
        return elementTypeClass;
    }

    public static boolean hasInterface(Class<?> target, Class<?> interfaceClass) {
        Class<?>[] interfaces = target.getInterfaces();
        if (interfaces == null || interfaces.length <= 0) {
            return false;
        }

        for (Class<?> tClass : interfaces) {
            if (tClass.equals(interfaceClass)) {
                return true;
            }
        }
        return false;
    }
}
