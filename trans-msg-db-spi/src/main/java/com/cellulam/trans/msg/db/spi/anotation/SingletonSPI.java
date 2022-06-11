package com.cellulam.trans.msg.db.spi.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * singleton SPI
 * @author eric.li
 * @date 2022-06-10 23:39
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SingletonSPI {
}
