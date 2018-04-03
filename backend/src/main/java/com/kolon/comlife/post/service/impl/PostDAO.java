package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.web.PostController;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("postDAO")
public class PostDAO {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Resource
    private SqlSession sqlSession;

    public PostInfo selectPost(int id, int usrId) {
        Map<String, Integer> selectParams = new HashMap<>();

        selectParams.put( "usrId", Integer.valueOf(usrId) );
        selectParams.put( "postIdx", Integer.valueOf(id) );
        selectParams.put( "cmplxId", null );
        selectParams.put( "postType", null );
        selectParams.put( "limit", null );
        selectParams.put( "offset", null );

        return sqlSession.selectOne( "Post.selectPostList", selectParams );
    }

    public int countPostList() {
        return sqlSession.selectOne( "Post.countPostList" );
    }

    public List<PostInfo> selectPostList(Map params) {
        return sqlSession.selectList( "Post.selectPostList", params );
    }

    public List<PostInfo> selectPostListByComplexId(Map params) {
        params.put( "postIdx", null );
        return sqlSession.selectList( "Post.selectPostList", params );
    }

    public PostInfo insertPost(PostInfo post) {
        sqlSession.insert( "Post.insertPost", post );
        return sqlSession.selectOne( "Post.selectLatestPost" );
    }

    public PostInfo updatePost(PostInfo post) {
        int                  updateCount  = -1;
        Map<String, Integer> selectParams = new HashMap<>();

        updateCount = sqlSession.update( "Post.updatePost", post );
        if( updateCount < 1 ) {
            // 업데이트 되지 않은 경우, NULL 반환
            return null;
        }

        selectParams.put( "postIdx", post.getPostIdx() );
        selectParams.put( "cmplxId", null );
        selectParams.put( "postType", null );
        selectParams.put( "limit", null );
        selectParams.put( "offset", null );

        return sqlSession.selectOne( "Post.selectPostList", selectParams );
    }

    public int deletePost(int id, int usrId) {
        int updateCount = -1;
        Map<String, Integer> selectParams = new HashMap<String, Integer>();
        selectParams.put( "postIdx", id );
        selectParams.put( "usrId", usrId );
        updateCount = sqlSession.update( "Post.deletePost", selectParams );
        return updateCount;
    }
}
