package com.kolon.comlife.admin.complexes.service.impl;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
import com.kolon.comlife.admin.complexes.model.ComplexRegion;
import com.kolon.comlife.admin.complexes.model.ComplexSimpleInfo;
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

    public ComplexInfoDetail selectComplexById(int id) {
        ComplexInfoDetail complex = new ComplexInfoDetail();
        complex.setCmplxId(id);
        return sqlSession.selectOne("Complex.selectComplexById", complex );
    }

    public List<ComplexInfo> selectComplexList() {
        return sqlSession.selectList("Complex.selectComplexList" );
    }

    public List<ComplexSimpleInfo> selectComplexInfoDetailList() {
        return sqlSession.selectList( "Complex.selectComplexSimpleInfoDetailList" );
    }

    public List<ComplexSimpleInfo> selectComplexInfoList() {
        return sqlSession.selectList( "Complex.selectComplexSimpleInfoList" );
    }

    public int updateComplexGroupTypeById(int cmplxId, int cmplxGrpId) {
        ComplexInfo complex = new ComplexInfo();
        complex.setCmplxId(cmplxId);
        complex.setCmplxGrpId(cmplxGrpId);
        return sqlSession.update("Complex.updateComplexGroupTypeById", complex );
    }

    public List<HashMap> selectComplexGroupType() {
        return sqlSession.selectList("Complex.selectComplexGroupType");
    }

    public List<ComplexRegion> selectComplexRegion() {
        return sqlSession.selectList("Complex.selectComplexRegion");
    }
}
