package com.kolon.comlife.complexes.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.model.ComplexSimpleInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 단지 정보(Complex) DAO
 *
 *  단지 정보는 IOK에서 관리하기 때문에, Complex(단지) 정보는 CommonLife 서버에서 Update 및 Delete는 구현하지 않음
 */
@Repository("complexDAO")
public class ComplexDAO {

    @Resource
    private SqlSession sqlSession;

    public ComplexInfo selectComplexById(int id) {
        ComplexInfo complex = new ComplexInfo();
        complex.setCmplxId(id);
        return sqlSession.selectOne("Complex.selectComplexById", complex );
    }

    public List<ComplexInfo> selectComplexList() {
        return sqlSession.selectList("Complex.selectComplexList" );
    }

    public List<ComplexSimpleInfo> selectComplexInfoList() {
        return sqlSession.selectList( "Complex.selectComplexSimpleInfoList" );
    }
}