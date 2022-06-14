package com.cellulam.trans.msg.db.mysql.dao.impl;

import com.cellulam.trans.msg.db.mysql.dao.AbstractDao;
import com.cellulam.trans.msg.db.mysql.dao.BranchTransactionDao;
import com.cellulam.trans.msg.db.mysql.dao.mappers.BranchTransactionMapper;
import com.trans.db.facade.BranchTransaction;
import com.trans.db.facade.Transaction;
import com.trans.db.facade.enums.TransProcessResult;
import org.apache.ibatis.exceptions.PersistenceException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * @author eric.li
 * @date 2022-06-13 22:44
 */
public class BranchTransactionDaoImpl extends AbstractDao<BranchTransactionMapper> implements BranchTransactionDao {
    @Override
    public boolean finishTrans(String branchTransId) {
        return execute(branchTransactionMapper -> {
            BranchTransaction transaction = branchTransactionMapper.getBranchTransactionById(branchTransId);
            if (transaction != null && TransProcessResult.SUCCESS.name().equals(transaction.getResult())) {
                branchTransactionMapper.insertHistory(transaction);
                branchTransactionMapper.delete(branchTransId);
                return true;
            }
            return false;
        });
    }

    @Override
    public void registerBranchTrans(String branchTransId, String consumer, Transaction trans) {
        execute(branchTransactionMapper -> {
            BranchTransaction branchTransaction = new BranchTransaction();
            branchTransaction.setBranchTransId(branchTransId);
            branchTransaction.setTransId(trans.getTransId());
            branchTransaction.setConsumer(consumer);

            try {
                return branchTransactionMapper.insert(branchTransaction) > 0;
            } catch (PersistenceException e) {
                if (e.getCause() != null && e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                    //ignore SQLIntegrityConstraintViolationException
                    return false;
                } else {
                    throw e;
                }
            }
        });
    }

    @Override
    public boolean executedBranchTrans(String source, String transId, String result) {
        return execute(branchTransactionMapper -> {
            BranchTransaction branchTransaction = branchTransactionMapper.getBranchTransaction(source, transId);
            if (branchTransaction == null) {
                return false;
            }
            return branchTransactionMapper.updateResult(branchTransaction.getBranchTransId(), result) > 0;
        });
    }

    @Override
    public List<BranchTransaction> getBranchTrans(String transId) {
        return execute(branchTransactionMapper -> branchTransactionMapper.getBranchTrans(transId));
    }
}
