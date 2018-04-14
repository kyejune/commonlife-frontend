package com.kolon.comlife.complexes.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.model.ComplexSimpleInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Complex Service Implementation
 */
@Service("complexService")
public class ComplexServiceImpl implements ComplexService {
    public static final Logger logger = LoggerFactory.getLogger(ComplexServiceImpl.class);

    @Resource(name = "complexDAO")
    private ComplexDAO complexDAO;

    @Override
    public ComplexInfo getComplexById(int id ) {
        return complexDAO.selectComplexById( id );
    }

    @Override
    public List<ComplexInfo> getComplexList() {
        return complexDAO.selectComplexList();
    }

    @Override
    public List<ComplexSimpleInfo> getComplexSimpleList() { return complexDAO.selectComplexInfoList(); }

    @Override
    public List<ComplexSimpleInfo> getComplexListInSameGroup( int cmplxId ) {
        return complexDAO.selectComplexListInSameGroup( cmplxId );
    }
}

