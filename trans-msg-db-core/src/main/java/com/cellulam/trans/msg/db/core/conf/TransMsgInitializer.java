package com.cellulam.trans.msg.db.core.conf;

import com.cellulam.trans.msg.db.core.context.TransContext;

/**
 * init
 *
 * @author eric.li
 * @date 2022-06-12 00:12
 */
public abstract class TransMsgInitializer {
    public static void init(TransConfiguration configuration) {
        TransContext.init(configuration);
    }
}
