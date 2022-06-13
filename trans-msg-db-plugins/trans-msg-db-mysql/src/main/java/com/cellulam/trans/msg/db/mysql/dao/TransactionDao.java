package com.cellulam.trans.msg.db.mysql.dao;

import com.trans.db.facade.Transaction;

import java.util.List;

public interface TransactionDao {
    void insertTransMessage(String producer, String transType, String transId, String transMessage);

    List<Transaction> fetchTransByStatus(String status);

    List<Transaction> fetchTransByStatus(long tryTtl, String status);

    boolean tryExecute(String transId);

    void resetStatus(String transId);

    void finishTrans(String transId, String status);

    Transaction getTrans(String transId);

    void updateBranchTransStatus(String transId, String status);
}
