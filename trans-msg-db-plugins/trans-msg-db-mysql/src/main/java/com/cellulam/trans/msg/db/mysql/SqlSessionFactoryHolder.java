package com.cellulam.trans.msg.db.mysql;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

public class SqlSessionFactoryHolder {

    private SqlSessionFactoryHolder() {

    }

    public final static SqlSessionFactoryHolder HOLDER = new SqlSessionFactoryHolder();

    private SqlSessionFactory sqlSessionFactory;

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void init(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers("com.cellulam.trans.msg.db.mysql.dal.dao.mappers");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }
}
