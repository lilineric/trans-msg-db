package com.cellulam.trans.msg.db.core.test.mock.storage;

import com.google.common.collect.Maps;
import com.trans.db.facade.BranchTransaction;
import com.trans.db.facade.Transaction;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author eric.li
 * @date 2022-06-13 16:52
 */
public class MockDB {
    public final static Map<String, Transaction> transactionMap = Maps.newConcurrentMap();
    public final static Map<String, List<BranchTransaction>> branchMap = Maps.newConcurrentMap();

    public final static DataSource dataSource = new DataSource() {
        @Override
        public Connection getConnection() throws SQLException {
            return null;
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return null;
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {

        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {

        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
        }
    };
}
