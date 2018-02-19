package com.kolon.comlife.users.service.impl;

import com.kolon.comlife.users.util.IokUtil;
import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import com.kolonbenit.benitware.framework.orm.mybatis.BaseIbatisDao;
import com.kolonbenit.benitware.framework.xplaform.domain.ResultSetMap;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Repository("userDebugDAO")
public class UserDebugDAO {
    private static Logger logger = LoggerFactory.getLogger( UserDebugDAO.class );

    @Resource
    private SqlSession sqlSession;

    private static String NAMESPACE = "mobile.UserCertNoMapper.";

    public Map getHeadCert(RequestParameter parameter) {
        ResultSetMap     result;

//        String           key;
//        Iterator e = parameter.keySet().iterator();
//        while( e.hasNext() ){
//            key = (String)e.next();
//
//            logger.debug(">>>>>key " + key );
//            logger.debug(">>>>>value " + parameter.get(key) );
//        }

        result = sqlSession.selectOne(NAMESPACE+"getHeadCertM", parameter );
        return result;
    }

    public Map getUserCert(RequestParameter parameter) {
        ResultSetMap     result;

        result = sqlSession.selectOne(NAMESPACE+"getUserCertM", parameter );
        return result;
    }

}
