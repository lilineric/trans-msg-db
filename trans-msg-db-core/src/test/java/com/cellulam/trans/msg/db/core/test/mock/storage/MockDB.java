package com.cellulam.trans.msg.db.core.test.mock.storage;

import com.google.common.collect.Maps;
import com.trans.db.facade.BranchTransaction;
import com.trans.db.facade.Transaction;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author eric.li
 * @date 2022-06-13 16:52
 */
public class MockDB {
    public final static Map<String, Transaction> transactionMap = Maps.newConcurrentMap();
    public final static Map<String, List<BranchTransaction>> branchMap = Maps.newConcurrentMap();

    public final static DataSource dataSource;

    static {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/order_db?characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("root123");
        dataSource = hikariDataSource;
    }
}
