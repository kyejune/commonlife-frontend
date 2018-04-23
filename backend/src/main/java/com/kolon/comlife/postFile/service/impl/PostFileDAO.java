package com.kolon.comlife.postFile.service.impl;

import com.kolon.comlife.post.web.PostController;
import com.kolon.comlife.postFile.model.PostFileInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("postFileDAO")
public class PostFileDAO {
    private static final Logger logger = LoggerFactory.getLogger(PostFileDAO.class);

    @Resource
    private SqlSession sqlSession;

    public PostFileInfo getPostFile( int id ) {
        Map<String, Integer> selectParams = new HashMap<String, Integer>();
        selectParams.put( "postFileIdx", id );
        return sqlSession.selectOne( "PostFile.selectPostFile", selectParams );
    }

    public List<PostFileInfo> getPostFilesByPostId( int id ) {
        Map<String, Integer> selectParams = new HashMap<String, Integer>();
        selectParams.put( "postIdx", id );
        return sqlSession.selectList( "PostFile.selectPostFile", selectParams );
    }

    public List<PostFileInfo> getPostFilesByPostIds( List<Integer> ids ) {
        Map<String, List<Integer>> selectParams = new HashMap<String, List<Integer>>();
        selectParams.put( "postIdxs", ids );
        return sqlSession.selectList( "PostFile.selectPostFile", selectParams );
    }

    public PostFileInfo setPostFile( PostFileInfo postFileInfo ) {
        sqlSession.insert( "PostFile.insertPostFile", postFileInfo );
        return sqlSession.selectOne( "PostFile.selectLatestPostFile" );
    }

    public PostFileInfo updatePostFile( int id, PostFileInfo postFileInfo ) {
        sqlSession.update( "PostFile.updatePostFile", postFileInfo );
        Map<String, Integer> selectParams = new HashMap<String, Integer>();
        selectParams.put( "postFileIdx", id );
        return sqlSession.selectOne( "PostFile.selectLatestPostFile" );
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
