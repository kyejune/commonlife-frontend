package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.post.model.PostInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Repository("postDAO")
public class PostDAO {
    @Resource
    private SqlSession sqlSession;

    public PostInfo selectPost(int id) {
        return null;
    }

    public int countPostList() {
        return sqlSession.selectOne( "Post.countPostList" );
    }

    public List<PostInfo> selectPostList(Map params) {
        return sqlSession.selectList( "Post.selectPostList", params );
    }

    public PostInfo insertPost(PostInfo post) {
        return null;
    }

    public PostInfo updatePost(PostInfo post) {
        return null;
    }

    public void deletePost(PostInfo post) {
        return;
    }
}
