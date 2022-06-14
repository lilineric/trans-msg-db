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
public class BranchTransaction implements Serializable {
    private static final long serialVersionUID = 3338011179981257348L;

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
