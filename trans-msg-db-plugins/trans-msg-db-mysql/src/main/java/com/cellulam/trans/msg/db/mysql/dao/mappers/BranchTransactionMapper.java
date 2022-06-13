package com.cellulam.trans.msg.db.mysql.dao.mappers;

import com.trans.db.facade.BranchTransaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BranchTransactionMapper {

    List<BranchTransaction> getBranchTrans(String transId);

    int insertHistory(BranchTransaction transaction);

    int delete(String branchTransId);

    int insert(BranchTransaction branchTransaction);

    BranchTransaction getBranchTransaction(@Param("source") String source,
                                           @Param("transId") String transId);

    int updateResult(@Param("branchTransId") String branchTransId,
                     @Param("result") String result);

    BranchTransaction getBranchTransactionById(String branchTransId);
}
