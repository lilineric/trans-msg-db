package com.cellulam.trans.msg.db.core.repository;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.enums.TransStatus;
import com.cellulam.trans.msg.db.core.factories.RepositoryFactory;
import com.cellulam.trans.msg.db.core.repository.model.Transaction;
import com.cellulam.trans.msg.db.core.spi.RepositorySPI;

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

    public void registerBranchTrans(String consumer, Transaction trans) {
        repositorySPI.registerBranchTrans(consumer, trans);
    }

    public void insertTransMessage(String producer, String transType, String transId, String transMessage) {
        repositorySPI.insertTransMessage(producer, transType, transId, transMessage);
    }

    public void processSendingTrans(Consumer<Transaction> executor) {
        repositorySPI.processSendingTrans(executor);
    }

    public boolean tryExecute(Transaction transaction) {
        return repositorySPI.tryExecute(transaction);
    }

    public void resetStatus(Transaction trans) {
        repositorySPI.resetStatus(trans);
    }

    public void finishTrans(Transaction trans, TransStatus status) {
        repositorySPI.finishTrans(trans, status);
    }
}
