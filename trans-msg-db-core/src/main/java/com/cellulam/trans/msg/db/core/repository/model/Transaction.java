package com.cellulam.trans.msg.db.core.repository.model;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author eric.li
 * @date 2022-06-12 12:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    private String transId;
    private String transMessage;
    private String producer;
    private LocalDateTime created;
    private LocalDateTime modified;
}
