package com.cellulam.trans.msg.db.sample.order.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author eric.li
 * @date 2022-06-10 00:34
 */
@Data
public class OrderBean implements Serializable {
    private Long id;
    private Long uid;
    private String title;
    private String status;
    private Long amount;
    private LocalDateTime created;
    private LocalDateTime modified;
}
