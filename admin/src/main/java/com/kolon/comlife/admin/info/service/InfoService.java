package com.kolon.comlife.admin.info.service;


import com.kolon.comlife.admin.info.exception.DataNotFoundException;
import com.kolon.comlife.admin.info.exception.InfoGeneralException;
import com.kolon.comlife.admin.info.exception.OperationFailedException;
import com.kolon.comlife.admin.info.model.InfoData;
import com.kolon.comlife.admin.info.model.InfoItem;
import com.kolon.comlife.admin.support.exception.NoDataException;
import com.kolon.comlife.common.paginate.PaginateInfo;

import java.util.List;

public interface InfoService {

    List<InfoData> getInfoDataList(int cmplxId ) throws NoDataException;

    void updateCategoryInfoDisplayByComplexId(List<InfoData> dispOrderList) throws InfoGeneralException;

    PaginateInfo getInfoGuideItemList(int cmplxId, int pageNum, int recCntPerPage ) throws DataNotFoundException;

    InfoItem getInfoGuideItem( int cmplxId, int itemIdx ) throws DataNotFoundException, OperationFailedException;

    PaginateInfo getInfoBenefitsItemList( int cmplxId, int pageNum, int recCntPerPage ) throws DataNotFoundException;

    InfoItem getInfoBenefitsItem( int cmplxId, int itemIdx ) throws DataNotFoundException, OperationFailedException ;

    InfoItem makeItemPrivate(int id, int cmplxId, int adminIdx) ;

    InfoItem makeItemPublic(int id, int cmplxId, int adminIdx) ;

    InfoItem setItemWithImage(InfoItem newItem, List<Integer> filesIdList, int adminIdx);

    InfoItem updateItem( InfoItem item ) throws OperationFailedException;


}
