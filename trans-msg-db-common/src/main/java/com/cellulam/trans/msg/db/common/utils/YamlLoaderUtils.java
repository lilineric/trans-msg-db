package com.cellulam.trans.msg.db.common.utils;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.InputStream;

/**
 * @author eric.li
 * @date 2022-06-15 19:06
 */
public abstract class YamlLoaderUtils {
    private final static PropertyUtils propertyUtils;

    static {
        propertyUtils = newPropertyUtils();
    }

    public static <T> T loadAs(InputStream resource, Class<T> type) {
        Constructor constructor = new Constructor(type);
        constructor.setPropertyUtils(propertyUtils);
        Yaml yaml = new Yaml(constructor);
        return yaml.loadAs(resource, type);

    }

    private static PropertyUtils newPropertyUtils() {
        PropertyUtils property = new PropertyUtils() {
            @Override
            public Property getProperty(Class<? extends Object> type, String name) {
                if (name.indexOf('-') > -1) {
                    name = camelize(name);
                }
                return super.getProperty(type, name);
            }
        };
        property.setSkipMissingProperties(true);
        return property;
    }

    private static String camelize(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.substring(i, i + 1).equals("-")) {
                input.replace("-", "");
                input = input.substring(0, i) + input.substring(i + 1, i + 2).toUpperCase() + input.substring(i + 2);
            }
            if (input.substring(i, i + 1).equals(" ")) {
                input.replace(" ", "");
                input = input.substring(0, i) + input.substring(i + 1, i + 2).toUpperCase() + input.substring(i + 2);
            }
        }
        return input;
    }
}
