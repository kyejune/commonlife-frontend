package com.kolon.comlife.info.service.impl;


import com.kolon.comlife.info.model.InfoData;
import com.kolon.comlife.info.model.InfoItem;
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

    public List<InfoData> getInfoDataListByCmplxId( int cmplxId ) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmplxId", Integer.valueOf( cmplxId ));
        return sqlSession.selectList( "Info.selectInfoDataListByCmplxId", params );
    }

    public List<InfoItem> getInfoItemListByCmplxIdAndCategory(int cmplxId, String category ) {
        Map<String, Object> params = new HashMap<>();

        logger.debug("cmplxId >>> " + cmplxId);
        logger.debug("category >>> " + category );
        logger.debug("category >>> " + category.toUpperCase() );
        logger.debug("category >>> " + category.toLowerCase() );

        params.put("cmplxId", Integer.valueOf( cmplxId ));
        params.put("imageType", category.toUpperCase());
        params.put("cateId", category.toLowerCase());

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
}
