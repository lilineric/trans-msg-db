package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import com.cellulam.trans.msg.db.core.recovery.BranchTransRegister;
import com.cellulam.trans.msg.db.core.repository.TransRepository;
import com.cellulam.trans.msg.db.spi.contract.MessageProcessor;
import com.trans.db.facade.enums.TransProcessResult;
import com.trans.db.facade.enums.TransStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * process ACK message by producer
 *
 * @author eric.li
 * @date 2022-06-12 00:26
 */
@Slf4j
public class MessageProducerReceiveProcessor extends AbstractMessageReceiveProcessor implements MessageProcessor {


    public MessageProducerReceiveProcessor() {
        super();
    }

    @Override
    protected boolean process(TransMessage transMessage) {
        log.debug("Process {}", transMessage);
        TransProcessResult processResult = TransProcessResult.valueOf(transMessage.getBody());

        boolean result = TransRepository.instance.executedBranchTrans(transMessage.getHeader().getSource(),
                transMessage.getHeader().getTransId(),
                processResult);
        if (!result) {
            log.debug("No branch transactions for trans: {}, re-register", transMessage.getHeader().getTransId());
            if (!BranchTransRegister.instance.registerBranchTrans(transMessage.getHeader().getTransId())) {
                return false;
            }
            // re-update the branch trans status
            TransRepository.instance.executedBranchTrans(transMessage.getHeader().getSource(),
                    transMessage.getHeader().getTransId(),
                    processResult);
        }

        if (processResult == TransProcessResult.SUCCESS) {
            TransRepository.instance.finishTrans(transMessage.getHeader().getTransId(), TransStatus.SUCCESS);
        } else {
            TransRepository.instance.resetStatus(transMessage.getHeader().getTransId());
        }
        return true;
    }
}
