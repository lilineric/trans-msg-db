package com.cellulam.trans.msg.db.core.test.spi;

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
    public void insertTransMessage(String producer, String transId, String transMessage) {
        log.info("insertTransMessage producer: {}, transId: {}, message: {}", producer, transId, transMessage);
    }

    @Override
    public void processSendingTrans(Consumer<Transaction> executor) {
    }

    @Override
    public boolean tryExecute(Transaction transaction) {
        return true;
    }

    @Override
    public void resetStatus(Transaction trans) {
    }
}
