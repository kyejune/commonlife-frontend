package com.kolon.comlife.admin.support.service.impl;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.admin.support.exception.SupportGeneralException;
import com.kolon.comlife.admin.support.model.SupportCategoryInfo;
import com.kolon.comlife.admin.support.service.SupportService;
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

    @Override
    public void updateCategoryInfoDisplayByComplexId(List<SupportCategoryInfo> dispOrderList)
            throws SupportGeneralException
    {
        int resultCnt;

        for(SupportCategoryInfo e : dispOrderList ) {
            resultCnt = supportDAO.updateCategoryDisplayOrder( e );
            if( resultCnt < 1 ) {
                logger.error("resultCnt는 1보다 커야 합니다. CL_LIVING_SUPPORT_CONF 테이블의 데이터를 확인하세요.");
                throw new SupportGeneralException("업데이트 과정에 문제가 발생했습니다. 문제가 지속되면 담당자에게 문의하세요.");
            }
        }

        return;
    }
}
