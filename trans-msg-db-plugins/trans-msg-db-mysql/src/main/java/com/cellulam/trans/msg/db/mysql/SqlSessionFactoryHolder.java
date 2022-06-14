package com.cellulam.trans.msg.db.mysql;

import com.cellulam.trans.msg.db.mysql.dao.mappers.BranchTransactionMapper;
import com.cellulam.trans.msg.db.mysql.dao.mappers.TransactionMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;

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
//        configuration.addMapper(TransactionMapper.class);
//        configuration.addMapper(BranchTransactionMapper.class);
//        configuration.addLoadedResource("classpath:/mapper/*.xml");
        configuration.addMappers("com.cellulam.trans.msg.db.mysql.dao.mappers");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
//        try {
//            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
