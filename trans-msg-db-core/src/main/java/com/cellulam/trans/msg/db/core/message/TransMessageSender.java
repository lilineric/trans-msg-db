package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.coordinator.TransCoordinator;
import com.cellulam.trans.msg.db.core.factories.SerializeFactory;
import com.cellulam.trans.msg.db.core.factories.UidGeneratorFactory;
import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import com.cellulam.trans.msg.db.core.message.model.TransMessageHeader;
import com.cellulam.trans.msg.db.core.repository.TransRepository;
import com.cellulam.trans.msg.db.spi.SerializeSPI;
import com.cellulam.trans.msg.db.spi.UidGeneratorSPI;
import com.trans.db.facade.enums.TransStage;

import java.io.Serializable;

/**
 * message sender
 *
 * @author eric.li
 * @date 2022-06-11 19:51
 */
public class TransMessageSender {
    private final String source = TransContext.getConfiguration().getAppName();

    private UidGeneratorSPI uidGeneratorSPI = UidGeneratorFactory.getInstance(TransContext.getConfiguration().getUidGeneratorType());
    private SerializeSPI serializeSPI = SerializeFactory.getInstance(TransContext.getConfiguration().getSerializeType());

    /**
     * send message to consumer
     *
     * @param transType business trans type
     * @param body      business message body
     * @param <T>
     * @return trans id
     */
    public <T extends Serializable> String send(String transType, T body) {
        TransMessage message = new TransMessage();

        String transId = uidGeneratorSPI.nextId();

        TransMessageHeader header = new TransMessageHeader();
        header.setTransId(transId);
        header.setTransType(transType);
        header.setStage(TransStage.COMMIT.name());
        header.setSource(source);

        message.setHeader(header);
        message.setBody(serializeSPI.serialize(body));

        String serializedMessage = serializeSPI.serialize(message);

        TransRepository.instance.insertTransMessage(source,
                transType,
                transId,
                serializedMessage);
        TransCoordinator.instance.asyncCommit(transType, serializedMessage);
        return header.getTransId();
    }
}
