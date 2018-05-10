package com.kolon.comlife.admin.info.service.impl;

import com.kolon.comlife.admin.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.admin.imageStore.model.ImageInfo;
import com.kolon.comlife.admin.imageStore.model.ImageInfoExt;
import com.kolon.comlife.admin.info.exception.DataNotFoundException;
import com.kolon.comlife.admin.info.exception.InfoGeneralException;
import com.kolon.comlife.admin.info.exception.OperationFailedException;
import com.kolon.comlife.admin.info.model.InfoData;
import com.kolon.comlife.admin.info.model.InfoItem;
import com.kolon.comlife.admin.info.service.InfoService;
import com.kolon.comlife.admin.post.model.PostInfo;
import com.kolon.comlife.admin.support.exception.NoDataException;
import com.kolon.comlife.admin.users.service.impl.UserDAO;
import com.kolon.comlife.common.paginate.PaginateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("infoService")
public class InfoServiceImpl implements InfoService {
    private static final Logger logger = LoggerFactory.getLogger( InfoServiceImpl.class );

    @Autowired
    UserDAO userDAO;

    @Autowired
    ComplexDAO complexDAO;

    @Autowired
    InfoDAO infoDAO;


    public List<InfoData> getInfoDataList( int cmplxId ) throws NoDataException {

        List<InfoData> infoDataList;

        infoDataList = infoDAO.getInfoDataListByCmplxId( cmplxId );
        if( infoDataList == null ) {
            logger.error("INFO data list가 없음");
            throw new NoDataException("표시할 목록이 없습니다. 문제가 지속되면 담당자에게 문의하세요.");
        }

        return infoDataList;
    }

    public void updateCategoryInfoDisplayByComplexId(List<InfoData> dispOrderList)
            throws InfoGeneralException
    {
        int resultCnt;

        for(InfoData e : dispOrderList ) {
            resultCnt = infoDAO.updateCategoryDisplayOrder( e );
            if( resultCnt < 1 ) {
                logger.error("resultCnt는 1보다 커야 합니다. CL_INFO_CONF 테이블의 데이터를 확인하세요.");
                throw new InfoGeneralException( "업데이트 과정에 문제가 발생했습니다. 문제가 지속되면 담당자에게 문의하세요.");
            }
        }

        return;
    }

    public PaginateInfo getInfoGuideItemList(int cmplxId, int pageNum, int recCntPerPage )
            throws DataNotFoundException
    {
        PaginateInfo paginateInfo;
        int          countItems;
        int          limit;
        int          offset;

        List<InfoItem> infoItemList;

        limit = recCntPerPage;
        offset = limit * ( pageNum - 1 );

        infoItemList = infoDAO.getInfoItemListByCmplxIdAndCategory( cmplxId, "GUIDE", limit, offset );
        if (infoItemList == null) {
            throw new DataNotFoundException("가져올 목록이 없습니다");
        }

        // Calculate Pagination
        countItems = infoDAO.countInfoItemList( cmplxId, "GUIDE" );

        paginateInfo = new PaginateInfo();
        paginateInfo.setData( infoItemList );
        paginateInfo.setCurrentPageNo( pageNum );
        paginateInfo.setRecordCountPerPage( limit );
        paginateInfo.setPageSize( limit );
        paginateInfo.setTotalRecordCount( countItems );

        return paginateInfo;
    }

    public PaginateInfo getInfoBenefitsItemList( int cmplxId, int pageNum, int recCntPerPage )
            throws DataNotFoundException
    {
        PaginateInfo paginateInfo;
        int          countItems;
        int          limit;
        int          offset;

        List<InfoItem> infoItemList;

        limit = recCntPerPage;
        offset = limit * ( pageNum - 1 );

        infoItemList = infoDAO.getInfoItemListByCmplxIdAndCategory( cmplxId, "BENEFITS", limit, offset );
        if (infoItemList == null) {
            throw new DataNotFoundException("가져올 목록이 없습니다");
        }

        // Calculate Pagination
        countItems = infoDAO.countInfoItemList( cmplxId, "BENEFITS" );

        paginateInfo = new PaginateInfo();
        paginateInfo.setData( infoItemList );
        paginateInfo.setCurrentPageNo( pageNum );
        paginateInfo.setRecordCountPerPage( limit );
        paginateInfo.setPageSize( limit );
        paginateInfo.setTotalRecordCount( countItems );

        return paginateInfo;
    }


    public InfoItem getInfoGuideItem( int cmplxId, int itemIdx )
            throws DataNotFoundException, OperationFailedException {
        InfoItem infoItem;

        infoItem = infoDAO.getInfoItemByCmplxIdAndCategory( cmplxId, "GUIDE", itemIdx );
        if( infoItem == null ) {
            throw new DataNotFoundException("가져올 게시물이 없습니다.");
        }

        return infoItem;
    }

    public InfoItem getInfoBenefitsItem( int cmplxId, int itemIdx )
            throws DataNotFoundException, OperationFailedException {
        InfoItem infoItem;

        infoItem = infoDAO.getInfoItemByCmplxIdAndCategory( cmplxId, "BENEFITS", itemIdx );
        if( infoItem == null ) {
            throw new DataNotFoundException("가져올 게시물이 없습니다.");
        }

        return infoItem;
    }



    @Override
    public InfoItem makeItemPrivate(int id, int cmplxId, int adminIdx) {
        InfoItem deletedItemInfo = null;

        if( infoDAO.updateItemDelYn( id, cmplxId, "Y") > 0 ) {
            deletedItemInfo = new InfoItem();
            deletedItemInfo.setDelYn("Y");
            deletedItemInfo.setItemIdx(id);
        }

        return deletedItemInfo;
    }

    @Override
    public InfoItem makeItemPublic(int id, int cmplxId, int adminIdx) {
        InfoItem deletedItemInfo = null;

        if( infoDAO.updateItemDelYn( id, cmplxId, "N") > 0 ) {
            deletedItemInfo = new InfoItem();
            deletedItemInfo.setDelYn("N");
            deletedItemInfo.setItemIdx(id);
        }

        return deletedItemInfo;
    }


    @Override
    public InfoItem setItemWithImage(InfoItem newItem, List<Integer> filesIdList, int adminIdx) {

        InfoItem         retItemInfo;
        List<ImageInfo>  fileInfoList;

        retItemInfo = infoDAO.insertItemByAdmin( newItem );


//        if( (filesIdList != null) && (filesIdList.size() > 0) ) {
//            logger.debug(">>> Post Files Count: " +  filesIdList.size());
//
//            fileInfoList = postFileDAO.bindPostToPostFiles( retPostInfo.getPostIdx(), filesIdList, adminIdx);
//            retPostInfo.setPostFiles( fileInfoList );
//        }

        return retItemInfo;
    }

    @Override
    public InfoItem updateItem( InfoItem item ) throws OperationFailedException {
        List<Integer>      newFileIdxList = new ArrayList<>();
        List<Integer>      oldFileIdxList = new ArrayList<>();
        InfoItem           retItemInfo;
//        List<PostFileInfo> retPostFileInfoList;
//        List<PostFileInfo> oldPostFileInfoList;

        // 이전에 업로드 된 이미지가 있는지 체크
//        oldPostFileInfoList = postFileDAO.getPostFilesByPostId( item.getPostIdx() );
//        logger.debug("oldPost.getPostFiles().size()>>> " + oldPostFileInfoList.size() );
//        for( PostFileInfo f : oldPostFileInfoList ) {
//            logger.debug("f.getPostFileIdx>>>> " + f.getPostFileIdx() );
//            oldFileIdxList.add( f.getPostFileIdx() );
//        }

        // 업데이트 수행
        retItemInfo = infoDAO.updateItem( item );

//        if( oldFileIdxList.size() > 0 && newFileIdxList.size() > 0 &&
//                oldFileIdxList.get(0) == newFileIdxList.get(0) ) {
//            // do nothing
//        } else {
//            if( oldFileIdxList.size() > 0 ) {
//                // delete¸
//                postFileDAO.deletePostFile( oldFileIdxList.get(0) );
//                retPostInfo.setPostFiles( new ArrayList<PostFileInfo>() );
//            }
//
//            if( newFileIdxList.size() > 0 ) {
//                // Bind
//                retPostFileInfoList = postFileDAO.bindPostToPostFiles( newPost.getPostIdx(), newFileIdxList, -1 );
//                retPostInfo.setPostFiles( retPostFileInfoList );
//            }
//        }

        return retItemInfo;
    }


}


