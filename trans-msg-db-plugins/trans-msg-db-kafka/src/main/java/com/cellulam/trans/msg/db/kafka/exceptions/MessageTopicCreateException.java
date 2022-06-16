package com.cellulam.trans.msg.db.kafka.exceptions;

/**
 * @author eric.li
 * @date 2022-06-15 22:25
 */
public class MessageTopicCreateException extends RuntimeException{
    public MessageTopicCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
