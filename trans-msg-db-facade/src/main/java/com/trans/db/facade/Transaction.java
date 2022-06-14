package com.trans.db.facade;

import lombok.*;

import java.io.Serializable;
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
public class Transaction implements Serializable {

    private static final long serialVersionUID = -4950248934017192219L;

    private String transType;
    private String transId;
    private String transMessage;
    private String producer;
    /**
     * see {@link com.trans.db.facade.enums.BranchTransStatus}
     */
    private String branchTransStatus;
    /**
     * see {@link com.trans.db.facade.enums.TransStatus}
     */
    private String status;
    private int retryCount;
    private LocalDateTime lastRetryTime;
    private LocalDateTime created;
    private LocalDateTime modified;
}
