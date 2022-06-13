package com.cellulam.trans.msg.db.core.exceptions;

/**
 * @author eric.li
 * @date 2022-06-13 14:45
 */
public class TransMessageProcessException extends TransMessageException{
    public TransMessageProcessException(String message) {
        super(message);
    }

    public TransMessageProcessException(Throwable cause) {
        super(cause);
    }

    public TransMessageProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
