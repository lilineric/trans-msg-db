package com.cellulam.trans.msg.db.core.test.mock.storage;

import com.google.common.collect.Maps;
import com.trans.db.facade.BranchTransaction;
import com.trans.db.facade.Transaction;

import java.util.List;
import java.util.Map;

/**
 * @author eric.li
 * @date 2022-06-13 16:52
 */
public class MockDB {
    public final static Map<String, Transaction> transactionMap = Maps.newConcurrentMap();
    public final static Map<String, List<BranchTransaction>> branchMap = Maps.newConcurrentMap();
}
