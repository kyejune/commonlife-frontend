package com.kolon.comlife.admin.info.service.impl;


import com.kolon.comlife.admin.info.model.model.InfoData;
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



}
