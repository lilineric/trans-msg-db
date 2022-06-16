package com.trans.db.facade;


import com.cellulam.trans.msg.db.common.utils.ClassUtil;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-13 19:45
 */
public abstract class AbstractTransMessageProcessor<T extends Serializable> implements TransMessageProcessor<T> {

    public Class<T> getBodyClassType() {
        return ClassUtil.getSuperClassGenericType(this.getClass(), 0);
    }
}
