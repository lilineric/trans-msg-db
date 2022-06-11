package com.cellulam.trans.msg.db.spi.exceptions;

/**
 * @author eric.li
 * @date 2022-06-11 00:22
 */
public class ServiceProviderNotFoundException extends RuntimeException {
    public ServiceProviderNotFoundException(final Class<?> clazz) {
        super(String.format("No implementation class load from SPI `%s`.", clazz.getName()));
    }

    public ServiceProviderNotFoundException(final Class<?> clazz, final String type) {
        super(String.format("No implementation class load from SPI `%s` with type `%s`.", clazz.getName(), type));
    }
}
