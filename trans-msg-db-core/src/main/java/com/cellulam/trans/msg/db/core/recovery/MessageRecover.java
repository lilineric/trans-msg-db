package com.cellulam.trans.msg.db.core.recovery;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.coordinator.TransCoordinator;
import com.cellulam.trans.msg.db.core.exceptions.TransMessageRecoverException;
import com.cellulam.trans.msg.db.core.repository.TransRepository;
import com.cellulam.trans.msg.db.core.repository.model.Transaction;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author eric.li
 * @date 2022-06-10 17:17
 */
@Slf4j
public class MessageRecover {
    private final ScheduledExecutorService scheduledThreadPool;

    public MessageRecover() {
        this.scheduledThreadPool = Executors.newScheduledThreadPool(1);
    }


    public void start() {
        this.scheduledThreadPool.scheduleAtFixedRate(this::messageTransRecover,
                TransContext.getConfiguration().getRecoverPeriodSeconds(),
                TransContext.getConfiguration().getRecoverPeriodSeconds(),
                TimeUnit.SECONDS);
    }

    private void messageTransRecover() {
        TransRepository.instance.processSendingTrans(trans -> {
            try {
                if (!TransRepository.instance.tryExecute(trans)) {
                    throw new TransMessageRecoverException("Failed to try to execute trans: " + trans);
                }
                this.messageTransRecover(trans);
            } catch (Exception e) {
                log.error("Failed to commit trans message, trans: " + trans, e);
                this.resetTransStatus(trans);
            }
        });
    }

    private void messageTransRecover(Transaction trans) {
        if (BranchTransRegister.instance.registerBranchTrans(trans)) {
            TransCoordinator.instance.commit(trans.getTransType(), trans.getTransMessage());
        } else {
            log.warn("Failed to register branch trans, ignore the trans: " + trans);
        }
    }

    private void resetTransStatus(Transaction trans) {
        try {
            TransRepository.instance.resetStatus(trans);
        } catch (Exception e) {
            log.error("Failed to reset trans status, trans: " + trans, e);
        }
    }
}
