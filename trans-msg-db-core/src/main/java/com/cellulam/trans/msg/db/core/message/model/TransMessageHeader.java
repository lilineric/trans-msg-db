package com.cellulam.trans.msg.db.core.message.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-11 19:16
 */
@Data
public class TransMessageHeader implements Serializable {
    private static final long serialVersionUID = -6480435843578975489L;

    /**
     * unique ID
     */
    private String transId;

    /**
     * trans business type
     */
    private String transType;

    /**
     * trans stage
     * see {@link com.cellulam.trans.msg.db.core.enums.TransStage}
     */
    private String stage;
}
