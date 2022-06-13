package com.cellulam.trans.msg.db.mysql.dao;

import com.trans.db.facade.BranchTransaction;
import com.trans.db.facade.Transaction;

import java.util.List;

public interface BranchTransactionDao {

    boolean finishTrans(String branchTransId);

    void registerBranchTrans(String branchTransId, String consumer, Transaction trans);

    boolean executedBranchTrans(String source, String transId, String name);

    List<BranchTransaction> getBranchTrans(String transId);
}
