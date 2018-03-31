package com.kolon.comlife.admin.complexes.service.impl;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
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
    public int updateComplexGroupTypeById(int cmplxId, int cmplxGrpId) {
        return complexDAO.updateComplexGroupTypeById(cmplxId, cmplxGrpId);
    }

    @Override
    public List<HashMap> selectComplexGroupType() {
        return complexDAO.selectComplexGroupType();
    }
}
