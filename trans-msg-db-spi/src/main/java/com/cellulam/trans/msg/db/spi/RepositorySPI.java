package com.cellulam.trans.msg.db.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;
import com.trans.db.facade.Transaction;
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
     * @param trans
     */
    void resetStatus(Transaction trans);

    /**
     * set the transaction status to SUCCESS {@link TransStatus} and migrate to history table
     * @param trans
     * @param status
     */
    void finishTrans(Transaction trans, TransStatus status);

    /**
     * register branch trans is not exist (in a same local transaction)
     *
     * @param consumer
     * @param trans
     */
    void registerBranchTrans(String consumer, Transaction trans);
}
