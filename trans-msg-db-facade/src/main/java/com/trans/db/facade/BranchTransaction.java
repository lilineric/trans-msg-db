package com.trans.db.facade;

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
public class BranchTransaction {
    private String branchTransId;
    private String transId;
    private String consumer;
    /**
     * see {@link com.trans.db.facade.enums.TransProcessResult}
     */
    private String result;
    private LocalDateTime created;
    private LocalDateTime modified;
}
