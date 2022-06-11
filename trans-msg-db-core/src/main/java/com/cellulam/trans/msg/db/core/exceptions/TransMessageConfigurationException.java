package com.cellulam.trans.msg.db.core.exceptions;

/**
 * @author eric.li
 * @date 2022-06-11 23:30
 */
public class TransMessageConfigurationException extends TransMessageException{
    public TransMessageConfigurationException(String message) {
        super(message);
    }

    public TransMessageConfigurationException(Throwable cause) {
        super(cause);
    }

    public TransMessageConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
