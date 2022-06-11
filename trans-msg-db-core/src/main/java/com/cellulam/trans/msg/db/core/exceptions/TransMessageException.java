package com.cellulam.trans.msg.db.core.exceptions;

/**
 * @author eric.li
 * @date 2022-06-11 21:51
 */
public class TransMessageException extends RuntimeException {
    public TransMessageException(String message) {
        super(message);
    }

    public TransMessageException(Throwable cause) {
        super(cause);
    }

    public TransMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
