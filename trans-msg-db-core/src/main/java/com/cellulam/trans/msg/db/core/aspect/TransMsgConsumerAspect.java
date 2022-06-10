package com.cellulam.trans.msg.db.core.aspect;

import com.cellulam.trans.msg.db.core.aspect.interceptor.TransMsgConsumerInterceptor;
import com.cellulam.trans.msg.db.core.aspect.interceptor.TransMsgProducerInterceptor;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author eric.li
 * @date 2022-06-10 16:23
 */
@Aspect
public class TransMsgConsumerAspect extends TransMsgConsumerInterceptor {
}
