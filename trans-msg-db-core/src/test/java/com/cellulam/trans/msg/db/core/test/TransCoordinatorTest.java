package com.cellulam.trans.msg.db.core.test;

import com.cellulam.msg.db.test.concurrent.SyncExecutorService;
import com.cellulam.trans.msg.db.core.coordinator.TransCoordinator;
import com.cellulam.trans.msg.db.core.test.configuration.BaseInitiatedTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author eric.li
 * @date 2022-06-12 02:12
 */
public class TransCoordinatorTest extends BaseInitiatedTest {

    @BeforeClass
    public static void before() throws Exception {
        Field field = TransCoordinator.class.getDeclaredField("messageSendThreadPool");
        field.setAccessible(true);
        field.set(TransCoordinator.instance, new SyncExecutorService());
    }
    @Test
    public void testCommit() {
        TransCoordinator.instance.asyncCommit("order-success", "{\"header\":{\"transId\":\"7d23a815-5382-41af-80e6-1d0ab300922a\",\"source\":\"order\",\"transType\":\"order-success\",\"stage\":\"COMMIT\"},\"body\":\"{\\\"id\\\":1,\\\"uid\\\":2,\\\"title\\\":\\\"success-scenario\\\",\\\"status\\\":\\\"success\\\",\\\"amount\\\":1234}\"}");
    }
}
