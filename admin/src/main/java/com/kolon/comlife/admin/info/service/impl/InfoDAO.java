package com.kolon.comlife.admin.info.service.impl;


import com.kolon.comlife.admin.info.model.InfoData;
import com.kolon.comlife.admin.info.model.InfoItem;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("infoDAO")
public class InfoDAO {
    private static final Logger logger = LoggerFactory.getLogger(InfoDAO.class);

    @Autowired
    private SqlSession sqlSession;

    public List<InfoData> getInfoDataListByCmplxId(int cmplxId ) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmplxId", Integer.valueOf( cmplxId ));
        return sqlSession.selectList( "Info.selectInfoDataListByCmplxId", params );
    }


    public int updateCategoryDisplayOrder( InfoData categoryInfo ) {
        return sqlSession.update("Info.updateCategoryDisplayOrder", categoryInfo);
    }


    public int countInfoItemList( int cmplxId, String category ) {
        Map<String, Object> params = new HashMap<>();

        params.put( "cmplxId", Integer.valueOf( cmplxId ) );
        params.put( "cateId", category.toLowerCase() );

        return sqlSession.selectOne( "Info.countInfoItemList", params );
    }

    public List<InfoItem> getInfoItemListByCmplxIdAndCategory(int cmplxId, String category, int limit, int offset ) {
        Map<String, Object> params = new HashMap<>();

        logger.debug("cmplxId >>> " + cmplxId);
        logger.debug("category >>> " + category );
        logger.debug("category >>> " + category.toUpperCase() );
        logger.debug("category >>> " + category.toLowerCase() );

        params.put("cmplxId", Integer.valueOf( cmplxId ));
        params.put("imageType", category.toUpperCase());
        params.put("cateId", category.toLowerCase());
        params.put("limit", Integer.valueOf( limit ));
        params.put("offset", Integer.valueOf( offset ));

        return sqlSession.selectList( "Info.selectInfoItemListByCmplxIdAndCategory", params );
    }

    public InfoItem getInfoItemByCmplxIdAndCategory(int cmplxId, String category, int itemIdx ) {
        Map<String, Object> params = new HashMap<>();

        logger.debug("cmplxId >>> " + cmplxId);
        logger.debug("category >>> " + category );
        logger.debug("category >>> " + category.toUpperCase() );
        logger.debug("category >>> " + category.toLowerCase() );

        params.put("cmplxId", Integer.valueOf( cmplxId ));
        params.put("imageType", category.toUpperCase());
        params.put("cateId", category.toLowerCase());
        params.put("itemIdx", Integer.valueOf( itemIdx ));

        return sqlSession.selectOne( "Info.selectInfoItemByCmplxIdAndCategory", params );
    }

//    public InfoItem updateItem(InfoItem item) {
//        int                  updateCount  = -1;
//        Map<String, Integer> selectParams = new HashMap<>();
//
//        updateCount = sqlSession.update( "Post.updatePost", post );
//        if( updateCount < 1 ) {
//            // 업데이트 되지 않은 경우, NULL 반환
//            return null;
//        }
//
//        selectParams.put( "postIdx", post.getPostIdx() );
//        selectParams.put( "cmplxId", null );
//        selectParams.put( "postType", null );
//        selectParams.put( "limit", null );
//        selectParams.put( "offset", null );
//
//        return sqlSession.selectOne( "Post.selectPostList", selectParams );
//    }

    public InfoItem insertItemByAdmin( InfoItem item ) {

        sqlSession.insert( "Info.insertItemByAdmin", item );
        return sqlSession.selectOne( "Info.selectLatestItem" );
    }

    public InfoItem updateItem(InfoItem item) {
        int                  updateCount  = -1;
        Map<String, Object> selectParams = new HashMap<>();

        updateCount = sqlSession.update( "Info.updateItem", item );
        if( updateCount < 1 ) {
            // 업데이트 되지 않은 경우, NULL 반환
            return null;
        }

        selectParams.put( "itemIdx", item.getItemIdx() );
        selectParams.put( "cmplxId", item.getCmplxId() );
        selectParams.put( "cateId", item.getCateId() );
        selectParams.put( "limit", null );
        selectParams.put( "offset", null );

        return sqlSession.selectOne( "Info.selectInfoItemByCmplxIdAndCategory", selectParams );
    }

    public int updateItemDelYn(int id, int cmplxId, String delYn) {
        int updateCount  = -1;
        Map selectParams = new HashMap();

        selectParams.put( "itemIdx", id );
        selectParams.put( "cmplxId", cmplxId );
        selectParams.put( "delYn",   delYn.toUpperCase() );

        updateCount = sqlSession.update( "Info.updateInfoDelYn", selectParams );

        return updateCount;
    }

}
