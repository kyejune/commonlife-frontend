package com.kolonbenit.benitware.framework.orm.mybatis;

import java.util.List;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseIbatisDao<T, P> extends SqlSessionDaoSupport {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public BaseIbatisDao() {
    }

    @Resource(
            name = "sqlSessionFactory"
    )
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public int countBySqlId(String sql_id, P parameter) {
        return (Integer)this.getSqlSession().selectOne(sql_id, parameter);
    }

    public List listWithPaging(String queryId, Object parameterObject, int pageIndex, int pageSize) {
        int skipResults = pageIndex * pageSize;
        RowBounds rowBounds = new RowBounds(skipResults, pageSize);
        return this.getSqlSession().selectList(queryId, parameterObject, rowBounds);
    }

    public long countLongBySqlId(String sql_id, P parameter) {
        return (Long)this.getSqlSession().selectOne(sql_id, parameter);
    }

    public T getBySqlId(String sql_id, P parameter) {
        return this.getSqlSession().selectOne(sql_id, parameter);
    }

    public List<T> listBySqlId(String sql_id, P parameter) {
        return this.getSqlSession().selectList(sql_id, parameter);
    }

    public int insertBySqlId(String sql_id, P parameter) {
        return this.getSqlSession().insert(sql_id, parameter);
    }

    public int updateBySqlId(String sql_id, P parameter) {
        return this.getSqlSession().update(sql_id, parameter);
    }

    public int deleteBySqlId(String sql_id, P parameter) {
        return this.getSqlSession().delete(sql_id, parameter);
    }
}
