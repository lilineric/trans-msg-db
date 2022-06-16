package com.cellulam.trans.db.sample.common.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author eric.li
 * @date 2022-06-16 15:29
 */
@Data
@ToString
public class OrderDto implements Serializable {
    private static final long serialVersionUID = -4198087108145506084L;

    private Long id;
    private Long uid;
    private String title;
    private String status;
    private Long amount;
    private LocalDateTime created;
    private LocalDateTime modified;
}
