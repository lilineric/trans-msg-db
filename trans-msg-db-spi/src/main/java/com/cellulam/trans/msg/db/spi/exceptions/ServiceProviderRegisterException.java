package com.cellulam.trans.msg.db.spi.exceptions;

/**
 * @author eric.li
 * @date 2022-06-12 01:35
 */
public class ServiceProviderRegisterException extends RuntimeException{
    public ServiceProviderRegisterException(String message) {
        super(message);
    }
    public ServiceProviderRegisterException(Throwable cause) {
        super(cause);
    }
    public ServiceProviderRegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
