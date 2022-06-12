package com.cellulam.trans.msg.db.core.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author eric.li
 * @date 2022-06-12 12:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransMessage {
    private String transId;
    private String body;
    private String producer;
    private LocalDateTime created;
    private LocalDateTime modified;
}
