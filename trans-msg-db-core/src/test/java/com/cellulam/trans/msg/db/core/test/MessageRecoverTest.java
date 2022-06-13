package com.cellulam.trans.msg.db.core.test;

import com.cellulam.trans.msg.db.core.recovery.MessageRecover;
import com.trans.db.facade.Transaction;
import com.cellulam.trans.msg.db.core.test.configuration.BaseInitiatedTest;
import com.trans.db.facade.enums.TransStatus;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author eric.li
 * @date 2022-06-12 22:44
 */
public class MessageRecoverTest extends BaseInitiatedTest {

    @Test
    public void testMessageRecover() throws Exception {
        Method messageTransRecoverMethod = MessageRecover.class.getDeclaredMethod("messageTransRecover");
        MessageRecover messageRecover = MessageRecover.class.newInstance();
        messageTransRecoverMethod.setAccessible(true);
        messageTransRecoverMethod.invoke(messageRecover);
    }

    @Test
    public void testMessageRecover2() throws Exception {
        Method messageTransRecoverMethod = MessageRecover.class.getDeclaredMethod("messageTransRecover", Transaction.class);
        messageTransRecoverMethod.setAccessible(true);
        MessageRecover messageRecover = MessageRecover.class.newInstance();
        messageTransRecoverMethod.invoke(messageRecover, Transaction.builder()
                .producer("test")
                .status(TransStatus.SENDING.name())
                .transId(UUID.randomUUID().toString())
                .transType("test-type")
                .transMessage("abc")
                .build());
    }
}
