package com.cellulam.trans.msg.db.facade.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * producer for trans message
 *
 * @author eric.li
 * @date 2022-06-09 21:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TransMsgProducer {
}
