package com.kolon.comlife.admin.support.service;

import com.kolon.comlife.admin.support.exception.SupportGeneralException;
import com.kolon.comlife.admin.support.model.SupportCategoryInfo;

import java.util.List;


public interface SupportService {

    // 카테고리 가져오기
    List<SupportCategoryInfo> getCategoryInfoByComplexId(SupportCategoryInfo categoryInfo ) throws SupportGeneralException;

    // 카테고리 표시 여부 설정 및 순서 업데이트
    void updateCategoryInfoDisplayByComplexId(List<SupportCategoryInfo> dispOrderList) throws SupportGeneralException;


}
