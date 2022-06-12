package com.cellulam.trans.msg.db.core.exceptions;

/**
 * @author eric.li
 * @date 2022-06-12 21:22
 */
public class TransMessageRecoverException extends TransMessageException {
    public TransMessageRecoverException(String message) {
        super(message);
    }

    public TransMessageRecoverException(Throwable cause) {
        super(cause);
    }

    public TransMessageRecoverException(String message, Throwable cause) {
        super(message, cause);
    }
}
