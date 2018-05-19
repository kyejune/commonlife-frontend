package com.kolon.comlife.admin.imageStore.service.impl;

import com.kolon.comlife.admin.imageStore.model.ImageInfo;
import com.kolon.comlife.admin.post.model.PostFileInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("imageInfoDAO")
public class ImageInfoDAO {
    private static final Logger logger = LoggerFactory.getLogger(ImageInfoDAO.class);

    @Resource
    private SqlSession sqlSession;

    public ImageInfo getImageFile(int imageIdx ) {
        Map<String, Integer> selectParams = new HashMap<String, Integer>();
        selectParams.put( "imageIdx", imageIdx );
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

    public ImageInfo updateImageParentIdx( int imageIdx, int parentIdx ) {
        int                  retCnt;
        Map<String, Integer> selectParams = new HashMap<String, Integer>();

        selectParams.put( "imageIdx", imageIdx );
        selectParams.put( "parentIdx", parentIdx );
        retCnt = sqlSession.update( "ImageInfo.updateImageParentIdx", selectParams );

        if(retCnt < 1) {
            return null;
        }

        return sqlSession.selectOne( "ImageInfo.selectImageInfo", selectParams );
    }

    public List<PostFileInfo> bindPostToPostFiles( int postIdx, List<Integer> postFileIdxs, int usrId ) {
        Map<String, Object> params = new HashMap<>();
        params.put( "postIdx", postIdx );
        params.put( "postFileIdxs", postFileIdxs );
        params.put( "usrId", usrId );
        sqlSession.update( "PostFile.bindPostToPostFiles", params );
        return sqlSession.selectList( "PostFile.selectPostFile", params );
    }

    public void deletePostFile( int id ) {
        Map<String, Integer> selectParams = new HashMap<String, Integer>();
        selectParams.put( "postFileIdx", id );
        sqlSession.delete( "PostFile.deletePostFile", selectParams );
        return;
    }
}
