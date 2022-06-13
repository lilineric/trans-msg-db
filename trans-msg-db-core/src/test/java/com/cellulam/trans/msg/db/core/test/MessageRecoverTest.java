package com.cellulam.trans.msg.db.core.test;

import com.cellulam.trans.msg.db.core.recovery.MessageRecover;
import com.cellulam.trans.msg.db.core.test.configuration.BaseInitiatedTest;
import org.junit.Test;

import java.lang.reflect.Method;

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
}
