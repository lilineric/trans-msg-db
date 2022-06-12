package com.cellulam.trans.msg.db.core.spi;

import com.cellulam.trans.msg.db.core.enums.TransStatus;
import com.cellulam.trans.msg.db.core.repository.model.Transaction;
import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;

import java.util.function.Consumer;

/**
 * @author eric.li
 * @date 2022-06-12 10:42
 */
@SingletonSPI
public interface RepositorySPI extends TypeSPI {

    void insertTransMessage(String producer, String transType, String transId, String transMessage);

    void processSendingTrans(Consumer<Transaction> executor);

    boolean tryExecute(Transaction transaction);

    void resetStatus(Transaction trans);

    void finishTrans(Transaction trans, TransStatus status);

    /**
     * register branch trans is not exist (in a same local transaction)
     *
     * @param consumer
     * @param trans
     */
    void registerBranchTrans(String consumer, Transaction trans);
}
