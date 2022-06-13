package com.cellulam.trans.msg.db.core.repository;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.factories.RepositoryFactory;
import com.cellulam.trans.msg.db.spi.RepositorySPI;
import com.trans.db.facade.Transaction;
import com.trans.db.facade.enums.BranchTransStatus;
import com.trans.db.facade.enums.TransProcessResult;
import com.trans.db.facade.enums.TransStatus;

import java.util.function.Consumer;

/**
 * @author eric.li
 * @date 2022-06-12 10:56
 */
public class TransRepository {
    private TransRepository() {
    }

    private RepositorySPI repositorySPI = RepositoryFactory.getInstance(TransContext.getConfiguration().getRepositoryType());

    public final static TransRepository instance = new TransRepository();

    public void registerBranchTrans(String branchTransId, String consumer, Transaction trans) {
        repositorySPI.registerBranchTrans(branchTransId, consumer, trans);
    }

    public void insertTransMessage(String producer, String transType, String transId, String transMessage) {
        repositorySPI.insertTransMessage(producer, transType, transId, transMessage);
    }

    public void processSendingTrans(Consumer<Transaction> executor) {
        repositorySPI.processSendingTrans(executor);
    }

    public void recoverTryingStatus(long transTryTimeoutSeconds, Consumer<Transaction> executor) {
        repositorySPI.recoverTryingStatus(transTryTimeoutSeconds, executor);
    }


    public boolean tryExecute(Transaction transaction) {
        return repositorySPI.tryExecute(transaction.getTransId());
    }

    public void resetStatus(String transId) {
        repositorySPI.resetStatus(transId);
    }

    public void finishTrans(String transId, TransStatus status) {
        repositorySPI.finishTrans(transId, status);
    }

    public boolean executedBranchTrans(String source, String transId, TransProcessResult result) {
        return repositorySPI.executedBranchTrans(source, transId, result);
    }

    public Transaction getTrans(String transId) {
        return repositorySPI.getTrans(transId);
    }

    public void updateBranchTransStatus(String transId, BranchTransStatus status) {
        repositorySPI.updateBranchTransStatus(transId, status);
    }
}
