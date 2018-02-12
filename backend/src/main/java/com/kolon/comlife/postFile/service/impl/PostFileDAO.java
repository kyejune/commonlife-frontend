package com.kolon.comlife.postFile.service.impl;

import com.kolon.comlife.post.web.PostController;
import com.kolon.comlife.postFile.model.PostFileInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Repository("postFileDAO")
public class PostFileDAO {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Resource
    private SqlSession sqlSession;

    public PostFileInfo getPostFile( int id ) {
        Map<String, Integer> selectParams = new HashMap<String, Integer>();
        selectParams.put( "fileIdx", id );
        return sqlSession.selectOne( "PostFile.selectPostFile", selectParams );
    }

    public PostFileInfo setPostFile( PostFileInfo postFileInfo ) {
        sqlSession.insert( "PostFile.insertPostFile", postFileInfo );
        return null;
    }

    public PostFileInfo updatePostFile( int id, PostFileInfo postFileInfo ) {
        sqlSession.update( "PostFile.updatePostFile", postFileInfo );
        Map<String, Integer> selectParams = new HashMap<String, Integer>();
        selectParams.put( "fileIdx", id );
        return sqlSession.selectOne( "PostFile.selectLatestPostFile" );
    }

    public void deletePostFile( int id ) {
        Map<String, Integer> selectParams = new HashMap<String, Integer>();
        selectParams.put( "fileIdx", id );
        sqlSession.delete( "PostFile.deletePostFile", selectParams );
        return;
    }
}
