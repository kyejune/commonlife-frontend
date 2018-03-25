package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service("postService")
public class PostServiceImpl implements PostService {
    @Resource(name = "postDAO")
    private PostDAO postDAO;

    @Override
    public PostInfo getPost(int id) {
        return postDAO.selectPost(id);
    }

    @Override
    public int countPostList() {
        return postDAO.countPostList();
    }

    @Override
    public List<PostInfo> getPostList(Map params) {
        return postDAO.selectPostList( params );
    }

    @Override
    public List<PostInfo> getPostListByComplexId(Map params) {
        return postDAO.selectPostListByComplexId( params );
    }

    @Override
    public PostInfo setPost(PostInfo post) {
        return postDAO.insertPost(post);
    }

    @Override
    public PostInfo updatePost(PostInfo post) {
        return postDAO.updatePost(post);
    }

    @Override
    public void deletePost(int id) {
        postDAO.deletePost(id);
        return;
    }
}
