package com.cellulam.trans.msg.db.core.test.mock.storage;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author eric.li
 * @date 2022-06-13 16:28
 */
public class MockMQ {
    public final static Queue<String> queue = new LinkedBlockingDeque<>();
    public final static Queue<String> ackQueue = new LinkedBlockingDeque<>();

    public static void clear() {
        queue.clear();
        ackQueue.clear();
    }
}
