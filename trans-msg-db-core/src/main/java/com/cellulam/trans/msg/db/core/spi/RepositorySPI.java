package com.cellulam.trans.msg.db.core.spi;

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

    void insertTransMessage(String producer, String transId, String transMessage);

    void processSendingTrans(Consumer<Transaction> executor);

    boolean tryExecute(Transaction transaction);

    void resetStatus(Transaction trans);
}
