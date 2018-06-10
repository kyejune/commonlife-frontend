package com.kolon.comlife.complexes.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.model.ComplexSimpleInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 단지 정보(Complex) DAO
 *
 *  단지 정보는 IOK에서 관리하기 때문에, Complex(단지) 정보는 CommonLife 서버에서 Update 및 Delete는 구현하지 않음
 */
@Repository("complexDAO")
public class ComplexDAO {

    @Resource
    private SqlSession sqlSession;

    public ComplexInfo selectComplexById( int id ) {
        Map params = new HashMap();
        params.put( "cmplxId", Integer.valueOf( id ) );
        return sqlSession.selectOne("Complex.selectComplexList", params );
    }

    public List<ComplexInfo> selectComplexList() {
        Map params = new HashMap();
        return sqlSession.selectList("Complex.selectComplexList", params );
    }

    public List<ComplexSimpleInfo> selectComplexInfoList() {
        return sqlSession.selectList( "Complex.selectComplexSimpleInfoList" );
    }

    public List<ComplexSimpleInfo> selectComplexListInSameGroup( int cmplxId ) {
        Map params = new HashMap();
        params.put("cmplxId", Integer.valueOf(cmplxId) );
        return sqlSession.selectList( "Complex.selectComplexListInSameGroup", params );
    }

    public String selectFeedWriteAllowByCmplxId( int cmplxId ) {
        Map result;
        Map params = new HashMap();
        params.put("cmplxId", Integer.valueOf(cmplxId) );
        result = sqlSession.selectOne( "Complex.selectFeedWriteAllowByCmplxId", params );

        return (String)result.get("FEED_WRITE_ALLOW_YN");
    }
}
