package com.cellulam.trans.msg.db.mysql;

import com.cellulam.trans.msg.db.mysql.dao.BranchTransactionDao;
import com.cellulam.trans.msg.db.mysql.dao.TransactionDao;
import com.cellulam.trans.msg.db.mysql.dao.impl.BranchTransactionDaoImpl;
import com.cellulam.trans.msg.db.mysql.dao.impl.TransactionDaoImpl;
import com.cellulam.trans.msg.db.spi.RepositorySPI;
import com.trans.db.facade.BranchTransaction;
import com.trans.db.facade.Transaction;
import com.trans.db.facade.enums.BranchTransStatus;
import com.trans.db.facade.enums.TransProcessResult;
import com.trans.db.facade.enums.TransStatus;
import org.apache.commons.collections4.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author eric.li
 * @date 2022-06-13 22:23
 */
public class MySQLRepository implements RepositorySPI {
    private final TransactionDao transactionDao;
    private final BranchTransactionDao branchTransactionDao;

    public MySQLRepository() {
        this.transactionDao = new TransactionDaoImpl();
        this.branchTransactionDao = new BranchTransactionDaoImpl();
    }

    @Override
    public void init(DataSource dataSource) {
        SqlSessionFactoryHolder.HOLDER.init(dataSource);
    }

    @Override
    public void insertTransMessage(String producer, String transType, String transId, String transMessage) {
        this.transactionDao.insertTransMessage(producer, transType, transId, transMessage);
    }

    @Override
    public void processSendingTrans(Consumer<Transaction> executor) {
        List<Transaction> transactions = this.transactionDao.fetchTransByStatus(TransStatus.SENDING.name());
        transactions.parallelStream()
                .forEach(x -> executor.accept(x));
    }

    @Override
    public void recoverTryingStatus(long transTryTimeoutSeconds, Consumer<Transaction> executor) {
        List<Transaction> transactions = this.transactionDao.fetchTransByStatus(transTryTimeoutSeconds, TransStatus.TRYING.name());
        transactions.parallelStream()
                .forEach(x -> executor.accept(x));
    }

    @Override
    public boolean tryExecute(String transId) {
        return this.transactionDao.tryExecute(transId);
    }

    @Override
    public void resetStatus(String transId) {
        this.transactionDao.resetStatus(transId);
    }

    @Override
    public void finishTrans(String transId, TransStatus status) {
        List<BranchTransaction> branchTransactions = this.branchTransactionDao.getBranchTrans(transId);
        if (CollectionUtils.isNotEmpty(branchTransactions)) {
            if (branchTransactions.stream()
                    .anyMatch(x -> !TransProcessResult.SUCCESS.name().equalsIgnoreCase(x.getResult()))) {
                //Only finished the transaction when all branch transactions succeed.
                return;
            }
            branchTransactions.parallelStream()
                    .forEach(x -> this.branchTransactionDao.finishTrans(x.getBranchTransId()));
        }

        //If there is no registered branch transaction,
        // only the status is set to IGNORED to indicate the end of the transaction.
        if (CollectionUtils.isNotEmpty(branchTransactions) || status == TransStatus.IGNORED) {
            if (CollectionUtils.isEmpty(this.branchTransactionDao.getBranchTrans(transId))) {
                //check again no branch transactions
                this.transactionDao.finishTrans(transId, status.name());
            }
        }
    }

    @Override
    public void registerBranchTrans(String branchTransId, String consumer, Transaction trans) {
        this.branchTransactionDao.registerBranchTrans(branchTransId, consumer, trans);
    }

    @Override
    public boolean executedBranchTrans(String source, String transId, TransProcessResult result) {
        return this.branchTransactionDao.executedBranchTrans(source, transId, result.name());
    }

    @Override
    public Transaction getTrans(String transId) {
        return this.transactionDao.getTrans(transId);
    }

    @Override
    public void updateBranchTransStatus(String transId, BranchTransStatus status) {
        this.transactionDao.updateBranchTransStatus(transId, status.name());
    }

    @Override
    public String getType() {
        return "mysql";
    }
}
