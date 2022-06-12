package com.cellulam.trans.msg.db.core.exceptions;

/**
 * @author eric.li
 * @date 2022-06-12 11:15
 */
public class TransMessageSerializeException extends TransMessageException{
    public TransMessageSerializeException(String message) {
        super(message);
    }

    public TransMessageSerializeException(Throwable cause) {
        super(cause);
    }

    public TransMessageSerializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
