package com.cellulam.trans.msg.db.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;
import com.trans.db.facade.Transaction;
import com.trans.db.facade.enums.BranchTransStatus;
import com.trans.db.facade.enums.TransProcessResult;
import com.trans.db.facade.enums.TransStatus;

import java.util.function.Consumer;


/**
 * repository SPI
 * @author eric.li
 * @date 2022-06-12 10:42
 */
@SingletonSPI
public interface RepositorySPI extends TypeSPI {

    /**
     * insert trans message, set retry count to 0
     * @param producer
     * @param transType
     * @param transId
     * @param transMessage
     */
    void insertTransMessage(String producer, String transType, String transId, String transMessage);

    /**
     * Process the transaction that the status is SENDING {@link TransStatus}
     * Process each SENDING transaction via executor
     * @param executor
     */
    void processSendingTrans(Consumer<Transaction> executor);

    /**
     * Recover the transaction that the status is TRYING {@link TransStatus}
     * Recover each TRYING transaction via executor
     *
     * @param transTryTimeoutSeconds
     * @param executor
     */
    void recoverTryingStatus(long transTryTimeoutSeconds, Consumer<Transaction> executor);

    /**
     * set the transaction status to TRYING {@link TransStatus}
     * @param transaction
     * @return
     */
    boolean tryExecute(Transaction transaction);

    /**
     * set the transaction status to SENDING {@link TransStatus}
     * @param transId
     */
    void resetStatus(String transId);

    /**
     * set the transaction status to SUCCESS {@link TransStatus} and migrate to history table
     * The branch transaction status needs to be checked before modifying the status
     * @param transId
     * @param status
     */
    void finishTrans(String transId, TransStatus status);

    /**
     * register branch trans is not exist (in a same local transaction)
     *
     * @param consumer
     * @param trans
     */
    void registerBranchTrans(String consumer, Transaction trans);

    /**
     * executed branch trans and update the process result
     * @param source
     * @param transId
     * @return
     */
    boolean executedBranchTrans(String source, String transId, TransProcessResult result);

    /**
     * get trans
     * @param transId
     * @return
     */
    Transaction getTrans(String transId);

    /**
     * update branch transaction status
     * @param transId
     * @param status
     */
    void updateBranchTransStatus(String transId, BranchTransStatus status);
}
