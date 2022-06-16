package com.trans.db.facade;

import lombok.Data;

/**
 * @author eric.li
 * @date 2022-06-15 16:40
 */
@Data
public class ConsumerRegister {
    private String consumer;
    private String producer;
    private String transType;
}
