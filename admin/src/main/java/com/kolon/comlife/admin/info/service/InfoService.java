package com.kolon.comlife.admin.info.service;


import com.kolon.comlife.admin.info.exception.InfoGeneralException;
import com.kolon.comlife.admin.info.model.model.InfoData;
import com.kolon.comlife.admin.support.exception.NoDataException;

import java.util.List;

public interface InfoService {

    List<InfoData> getInfoDataList(int cmplxId ) throws NoDataException;

    void updateCategoryInfoDisplayByComplexId(List<InfoData> dispOrderList) throws InfoGeneralException;

}
