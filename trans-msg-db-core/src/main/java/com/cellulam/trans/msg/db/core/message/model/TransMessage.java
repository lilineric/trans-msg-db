package com.cellulam.trans.msg.db.core.message.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-11 19:14
 */
@ToString
@Data
public class TransMessage implements Serializable{
    private static final long serialVersionUID = -4682777769419177821L;

    private TransMessageHeader header;
    private String body;
}
