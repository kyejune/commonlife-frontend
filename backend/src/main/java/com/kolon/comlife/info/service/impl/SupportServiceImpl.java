package com.kolon.comlife.info.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.info.exception.SupportGeneralException;
import com.kolon.comlife.info.model.SupportCategoryInfo;
import com.kolon.comlife.info.service.SupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("supportService")
public class SupportServiceImpl implements SupportService {
    public static final Logger logger = LoggerFactory.getLogger(SupportServiceImpl.class);

    @Autowired
    private SupportDAO supportDAO;

    @Autowired
    private ComplexDAO complexDAO;

    @Override
    public List<SupportCategoryInfo> getCategoryInfoByComplexId(SupportCategoryInfo categoryInfo)
                                        throws SupportGeneralException
    {
        ComplexInfo               complexInfo;
        List<SupportCategoryInfo> categoryInfoList;


        if( categoryInfo == null ) {
            throw new SupportGeneralException("입력값이 잘못 입력되었습니다.");
        }

        complexInfo = complexDAO.selectComplexById( categoryInfo.getCmplxId() );
        if(complexInfo == null) {
            throw new SupportGeneralException("해당 현장에 대한 정보가 없습니다.");
        }

        categoryInfoList = supportDAO.selectCategoryListByComplexId( categoryInfo );

        return categoryInfoList;
    }
}
