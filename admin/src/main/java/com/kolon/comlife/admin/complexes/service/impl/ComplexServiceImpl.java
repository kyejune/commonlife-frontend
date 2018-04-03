package com.kolon.comlife.admin.complexes.service.impl;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
import com.kolon.comlife.admin.complexes.model.ComplexRegion;
import com.kolon.comlife.admin.complexes.model.ComplexSimpleInfo;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Complex Service Implementation
 */
@Service("complexService")
public class ComplexServiceImpl implements ComplexService {
    public static final Logger logger = LoggerFactory.getLogger(ComplexServiceImpl.class);

    @Resource(name = "complexDAO")
    private ComplexDAO complexDAO;

    @Override
    public ComplexInfoDetail getComplexById(int id ) {
        return complexDAO.selectComplexById( id );
    }

    @Override
    public List<ComplexInfo> getComplexList() {
        return complexDAO.selectComplexList();
    }

    @Override
    public List<ComplexSimpleInfo> getComplexSimpleList() { return complexDAO.selectComplexInfoList(); };

    @Override
    public int updateComplexGroupTypeById(int cmplxId, int cmplxGrpId) throws Exception {
        int cmplxExtUpdatedCount;
        int updatedCount;

        // 1. Complex Ext 레코드를 생성
        cmplxExtUpdatedCount = complexDAO.createComplexExtById(cmplxId);

        // 2. Complex Group Type 정보를 설정
        updatedCount = complexDAO.updateComplexGroupTypeById(cmplxId, cmplxGrpId);
        if( updatedCount < 1 ) {
            String errMsg = "현장 그룹 정보(COMPLEX_GRP_M)를 업데이트할 수 없습니다. COMPLEX_GRP_M의 데이터를 확인하세요.";
            logger.error( errMsg );
            throw new Exception( errMsg );
        }

        // updateComplexGroupTypeById()에 의해 rollback 될 수 있으므로, 로깅은 마지막에 수행
        if( cmplxExtUpdatedCount > 0 ) {
            logger.info( "> 새로운 확장현장정보(COMPLEX_M_EXT)가 생성되었습니다. Complex Id:" + cmplxId );
        }

        return updatedCount;
    }

    @Override
    public List<HashMap> selectComplexGroupType() {
        return complexDAO.selectComplexGroupType();
    }

    @Override
    public List<ComplexRegion> getComplexRegion() {
        return complexDAO.selectComplexRegion();
    }
}
