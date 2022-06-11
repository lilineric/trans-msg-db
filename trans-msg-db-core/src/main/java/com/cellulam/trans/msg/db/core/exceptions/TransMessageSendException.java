package com.cellulam.trans.msg.db.core.exceptions;

/**
 * @author eric.li
 * @date 2022-06-11 21:51
 */
public class TransMessageSendException extends TransMessageException {
    public TransMessageSendException(String message) {
        super(message);
    }

    public TransMessageSendException(Throwable cause) {
        super(cause);
    }

    public TransMessageSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
