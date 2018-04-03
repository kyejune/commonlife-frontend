package com.kolon.comlife.support.service;


import com.kolon.comlife.support.exception.SupportGeneralException;
import com.kolon.comlife.support.model.SupportCategoryInfo;

import java.util.List;

public interface SupportService {
    // 카테고리 가져오기
    List<SupportCategoryInfo> getCategoryInfoByComplexId(SupportCategoryInfo categoryInfo)
            throws SupportGeneralException;


}
