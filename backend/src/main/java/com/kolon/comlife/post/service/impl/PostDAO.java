package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.post.model.PostInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Repository("postDAO")
public class PostDAO {
    @Resource
    private SqlSession sqlSession;

    public PostInfo selectPost(int id) {
        return null;
    }

    public List<PostInfo> selectPostList(HttpServletRequest request) {
        return sqlSession.selectList( "Post.selectPostList", request );
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
