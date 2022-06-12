package com.cellulam.trans.msg.db.core.test.spi;

import com.cellulam.msg.db.test.utils.TestUtils;
import com.cellulam.trans.msg.db.core.enums.TransStatus;
import com.cellulam.trans.msg.db.core.repository.model.Transaction;
import com.cellulam.trans.msg.db.core.spi.RepositorySPI;
import lombok.extern.slf4j.Slf4j;

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
    public void insertTransMessage(String producer, String transType, String transId, String transMessage) {
        log.info("insertTransMessage producer: {}, transType: {} transId: {}, message: {}",
                producer, transType, transId, transMessage);
    }

    @Override
    public void processSendingTrans(Consumer<Transaction> executor) {
        executor.accept(TestUtils.randomBean(Transaction.class));
        executor.accept(TestUtils.randomBean(Transaction.class));
    }

    @Override
    public boolean tryExecute(Transaction transaction) {
        return true;
    }

    @Override
    public void resetStatus(Transaction trans) {
    }

    @Override
    public void finishTrans(Transaction trans, TransStatus status) {

    }

    @Override
    public void registerBranchTrans(String consumer, Transaction trans) {

    }
}
