package com.cellulam.trans.msg.db.core.recovery;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.factories.DynamicConfigFactory;
import com.cellulam.trans.msg.db.core.factories.UidGeneratorFactory;
import com.cellulam.trans.msg.db.core.repository.TransRepository;
import com.cellulam.trans.msg.db.spi.DynamicConfigSPI;
import com.cellulam.trans.msg.db.spi.UidGeneratorSPI;
import com.trans.db.facade.Transaction;
import com.trans.db.facade.enums.BranchTransStatus;
import com.trans.db.facade.enums.TransStatus;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author eric.li
 * @date 2022-06-12 22:20
 */
public class BranchTransRegister {
    private final DynamicConfigSPI dynamicConfigSPI;
    private final UidGeneratorSPI uidGeneratorSPI;

    private BranchTransRegister() {
        this.dynamicConfigSPI = DynamicConfigFactory.getInstance(TransContext.getConfiguration().getDynamicConfigType());
        this.uidGeneratorSPI = UidGeneratorFactory.getInstance(TransContext.getConfiguration().getUidGeneratorType());
    }

    public final static BranchTransRegister instance = new BranchTransRegister();

    public boolean registerBranchTrans(String transId) {
        return this.registerBranchTrans(TransRepository.instance.getTrans(transId));
    }

    public boolean registerBranchTrans(Transaction trans) {
        if (BranchTransStatus.REGISTERED.name().equalsIgnoreCase(trans.getBranchTransStatus())) {
            return true;
        }
        List<String> consumers = dynamicConfigSPI.getConsumers(trans.getTransType(), trans.getProducer());
        if (CollectionUtils.isEmpty(consumers)) {
            TransRepository.instance.finishTrans(trans.getTransId(), TransStatus.IGNORED);
            return false;
        }
        consumers.parallelStream()
                .forEach(x -> TransRepository.instance.registerBranchTrans(uidGeneratorSPI.nextId(), x, trans));
        TransRepository.instance.updateBranchTransStatus(trans.getTransId(), BranchTransStatus.REGISTERED);
        return true;
    }
}
