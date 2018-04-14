package com.kolon.comlife.info.service;


import com.kolon.comlife.info.exception.SupportGeneralException;
import com.kolon.comlife.info.model.SupportCategoryInfo;

import java.util.List;

public interface SupportService {
    // 카테고리 가져오기
    List<SupportCategoryInfo> getCategoryInfoByComplexId(SupportCategoryInfo categoryInfo)
            throws SupportGeneralException;


}
