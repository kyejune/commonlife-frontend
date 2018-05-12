package com.kolon.comlife.imageStore.service.impl;

import com.kolon.comlife.imageStore.model.ImageInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Repository("imageInfoDAO")
public class ImageInfoDAO {
    private static final Logger logger = LoggerFactory.getLogger(ImageInfoDAO.class);

    @Resource
    private SqlSession sqlSession;

    public ImageInfo getImageFile( int imageId ) {
        Map<String, Integer> selectParams = new HashMap<String, Integer>();
        selectParams.put( "imageIdx", imageId );
        return sqlSession.selectOne( "ImageInfo.selectImageInfo", selectParams );
    }

    public Integer getImageTypeByTypeNm( String imageType ) {
        Map<String, String> selectParams = new HashMap<>();
        selectParams.put( "typeNm", imageType );
        return sqlSession.selectOne( "ImageInfo.selectImageTypeIdxByTypeNm", selectParams );
    }

    public ImageInfo setImageFile(ImageInfo imageInfo ) {
        sqlSession.insert( "ImageInfo.insertImageInfo", imageInfo );
        return sqlSession.selectOne( "ImageInfo.selectLatestImageInfo" );
    }

}
