package com.cellulam.trans.msg.db.spring.autoconfigure;

import com.cellulam.trans.db.spring.configuration.TransMsgConfiguration;
import com.cellulam.trans.db.spring.configuration.TransMsgInitializerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author eric.li
 * @date 2022-06-10 16:13
 */
@Configuration
@Import({TransMsgConfiguration.class, TransMsgInitializerConfiguration.class})
public class TransMsgAutoConfiguration {
}
