package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.coordinator.TransCoordinator;
import com.cellulam.trans.msg.db.core.factories.SerializeFactory;
import com.cellulam.trans.msg.db.core.factories.UidGeneratorFactory;
import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import com.cellulam.trans.msg.db.core.message.model.TransMessageHeader;
import com.cellulam.trans.msg.db.core.repository.TransRepository;
import com.cellulam.trans.msg.db.core.spi.SerializeSPI;
import com.cellulam.trans.msg.db.core.spi.UidGeneratorSPI;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-11 19:51
 */
public class TransMessageSender {
    private UidGeneratorSPI uidGeneratorSPI = UidGeneratorFactory.getInstance(TransContext.getConfiguration().getUidGeneratorType());
    private SerializeSPI serializeSPI = SerializeFactory.getInstance(TransContext.getConfiguration().getSerializeType());

    public <T extends Serializable> String send(String transType, T body) {
        TransMessage message = new TransMessage();

        String transId = uidGeneratorSPI.nextId();

        TransMessageHeader header = new TransMessageHeader();
        header.setTransId(transId);
        header.setTransType(transType);

        message.setHeader(header);
        message.setBody(body);

        String serializedMessage = serializeSPI.serialize(message);

        TransRepository.instance.insertTransMessage(TransContext.getConfiguration().getAppName(),
                transType,
                transId,
                serializedMessage);
        TransCoordinator.instance.asyncCommit(transType, serializedMessage);
        return header.getTransId();
    }
}
