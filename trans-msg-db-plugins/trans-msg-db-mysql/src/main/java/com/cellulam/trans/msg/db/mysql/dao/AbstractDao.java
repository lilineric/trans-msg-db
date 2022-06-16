package com.cellulam.trans.msg.db.mysql.dao;

import com.cellulam.trans.msg.db.mysql.SqlSessionFactoryHolder;
import com.cellulam.trans.msg.db.mysql.exceptions.DbException;
import com.cellulam.trans.msg.db.common.utils.ClassUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.function.Function;

public abstract class AbstractDao<MAPPER> {
    protected <R> R execute(Function<MAPPER, R> executor) {
        SqlSession sqlSession = this.getSqlSessionFactory().openSession(false);
        MAPPER mapper = sqlSession.getMapper(getMapper());
        try {
            R result = executor.apply(mapper);
            sqlSession.commit();
            return result;
        } catch (Exception e) {
            sqlSession.rollback();
            throw new DbException("Failed execute sql.", e);
        } finally {
            sqlSession.close();
        }
    }

    protected SqlSessionFactory getSqlSessionFactory() {
        return SqlSessionFactoryHolder.HOLDER.getSqlSessionFactory();
    }

    protected Class<MAPPER> getMapper() {
        return ClassUtil.getSuperClassGenericType(getClass(), 0);
    }
}
