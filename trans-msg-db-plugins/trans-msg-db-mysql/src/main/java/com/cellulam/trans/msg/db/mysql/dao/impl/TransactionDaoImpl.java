package com.cellulam.trans.msg.db.mysql.dao.impl;

import com.cellulam.trans.msg.db.mysql.dao.AbstractDao;
import com.cellulam.trans.msg.db.mysql.dao.TransactionDao;
import com.cellulam.trans.msg.db.mysql.dao.mappers.TransactionMapper;
import com.trans.db.facade.Transaction;
import com.trans.db.facade.enums.TransStatus;

import java.util.List;

/**
 * @author eric.li
 * @date 2022-06-13 22:44
 */
public class TransactionDaoImpl extends AbstractDao<TransactionMapper> implements TransactionDao {
    @Override
    public void insertTransMessage(String producer, String transType, String transId, String transMessage) {
        execute(transactionMapper ->
                transactionMapper.insert(producer, transType, transId, transMessage) > 0);
    }

    @Override
    public List<Transaction> fetchTransByStatus(String status) {
        return execute(transactionMapper -> transactionMapper.fetchTransByStatus(status));
    }

    @Override
    public List<Transaction> fetchTransByStatus(long tryTtl, String status) {
        return execute(transactionMapper -> transactionMapper.fetchTransByStatusTtl(tryTtl, status));
    }

    @Override
    public boolean tryExecute(String transId) {
        return execute(transactionMapper -> transactionMapper.tryExecute(transId,
                TransStatus.TRYING.name(),
                TransStatus.SENDING.name()) > 0);
    }

    @Override
    public void resetStatus(String transId) {
        execute(transactionMapper -> transactionMapper.updateStatus(transId,
                TransStatus.SENDING.name(),
                TransStatus.TRYING.name()) > 0);
    }

    @Override
    public void finishTrans(String transId, String status) {
        execute(transactionMapper -> {
            Transaction transaction = transactionMapper.getTrans(transId);
            if (transaction != null) {
                transaction.setStatus(status);
                transactionMapper.insertHistory(transaction);
                transactionMapper.delete(transId);
            }
            return null;
        });
    }

    @Override
    public Transaction getTrans(String transId) {
        return execute(transactionMapper -> transactionMapper.getTrans(transId));
    }

    @Override
    public void updateBranchTransStatus(String transId, String status) {
        execute(transactionMapper -> transactionMapper.updateBranchTransStatus(transId, status) > 0);
    }
}
