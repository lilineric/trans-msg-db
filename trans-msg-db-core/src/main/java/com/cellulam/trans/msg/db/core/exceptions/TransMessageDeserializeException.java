package com.cellulam.trans.msg.db.core.exceptions;

/**
 * @author eric.li
 * @date 2022-06-12 11:16
 */
public class TransMessageDeserializeException extends TransMessageException{
    public TransMessageDeserializeException(String message) {
        super(message);
    }

    public TransMessageDeserializeException(Throwable cause) {
        super(cause);
    }

    public TransMessageDeserializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
