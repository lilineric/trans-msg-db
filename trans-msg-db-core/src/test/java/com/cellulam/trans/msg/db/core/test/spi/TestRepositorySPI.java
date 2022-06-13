package com.cellulam.trans.msg.db.core.test.spi;

import com.cellulam.trans.msg.db.core.test.mock.storage.MockDB;
import com.cellulam.trans.msg.db.spi.RepositorySPI;
import com.google.common.collect.Lists;
import com.trans.db.facade.BranchTransaction;
import com.trans.db.facade.Transaction;
import com.trans.db.facade.enums.BranchTransStatus;
import com.trans.db.facade.enums.TransProcessResult;
import com.trans.db.facade.enums.TransStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author eric.li
 * @date 2022-06-12 12:23
 */
@Slf4j
public class TestRepositorySPI implements RepositorySPI {

    @Override
    public String getType() {
        return "test";
    }


    @Override
    public synchronized void insertTransMessage(String producer, String transType, String transId, String transMessage) {
        Transaction transaction = new Transaction();
        transaction.setTransType(transType);
        transaction.setTransId(transId);
        transaction.setTransMessage(transMessage);
        transaction.setProducer(producer);
        transaction.setStatus(TransStatus.SENDING.name());
        transaction.setBranchTransStatus(BranchTransStatus.INIT.name());
        transaction.setRetryCount(0);
        transaction.setCreated(LocalDateTime.now());
        transaction.setModified(LocalDateTime.now());

        MockDB.transactionMap.put(transId, transaction);
    }

    @Override
    public synchronized void processSendingTrans(Consumer<Transaction> executor) {
        for (Transaction transaction : MockDB.transactionMap.values()) {
            if (transaction.getStatus().equalsIgnoreCase(TransStatus.SENDING.name())) {
                executor.accept(transaction);
            }
        }
    }

    @Override
    public synchronized void recoverTryingStatus(long transTryTimeoutSeconds, Consumer<Transaction> executor) {
        for (Transaction transaction : MockDB.transactionMap.values()) {
            if (transaction.getStatus().equalsIgnoreCase(TransStatus.TRYING.name())) {
                executor.accept(transaction);
            }
        }
    }

    @Override
    public synchronized boolean tryExecute(Transaction transaction) {
        MockDB.transactionMap.get(transaction.getTransId()).setStatus(TransStatus.TRYING.name());
        return true;
    }

    @Override
    public synchronized void resetStatus(String transId) {
        MockDB.transactionMap.get(transId).setStatus(TransStatus.SENDING.name());
    }

    @Override
    public synchronized void finishTrans(String transId, TransStatus status) {
        List<BranchTransaction> branchTransactions = MockDB.branchMap.get(transId);
        System.out.println("branchTransactions: " + branchTransactions);
        if (CollectionUtils.isEmpty(branchTransactions)
                || branchTransactions.stream()
                .allMatch(x -> x.getResult().equalsIgnoreCase(TransProcessResult.SUCCESS.name()))) {
            System.out.println("Remove transaction " + transId);
            MockDB.transactionMap.remove(transId);
        }
    }

    @Override
    public synchronized void registerBranchTrans(String consumer, Transaction trans) {
        if (!MockDB.branchMap.containsKey(trans.getTransId())) {
            MockDB.branchMap.put(trans.getTransId(), Lists.newArrayList());
        }
        if (MockDB.branchMap.get(trans.getTransId()).stream()
                .anyMatch(x -> x.getConsumer().equalsIgnoreCase(consumer))) {
            return;
        }
        MockDB.branchMap.get(trans.getTransId()).add(BranchTransaction.builder()
                .branchTransId(UUID.randomUUID().toString())
                .transId(trans.getTransId())
                .consumer(consumer)
                .result(TransProcessResult.INIT.name())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build());
    }

    @Override
    public synchronized boolean executedBranchTrans(String source, String transId, TransProcessResult result) {
        List<BranchTransaction> branchTransactions = MockDB.branchMap.get(transId);
        if (CollectionUtils.isEmpty(branchTransactions)) {
            return false;
        }
        BranchTransaction branchTransaction = branchTransactions.stream()
                .filter(x -> x.getConsumer().equalsIgnoreCase(source))
                .findFirst().orElse(null);
        if (branchTransaction == null) {
            return false;
        }
        branchTransaction.setResult(result.name());
        return true;
    }

    @Override
    public synchronized Transaction getTrans(String transId) {
        return MockDB.transactionMap.get(transId);
    }

    @Override
    public void updateBranchTransStatus(String transId, BranchTransStatus status) {
        MockDB.transactionMap.get(transId).setBranchTransStatus(status.name());
    }
}
