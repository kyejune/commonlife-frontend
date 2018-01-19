package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("postService")
public class PostServiceImpl implements PostService {
    @Resource(name = "postDAO")
    private PostDAO postDAO;

    @Override
    public PostInfo getPost(int id) {
        return postDAO.selectPost(id);
    }

    @Override
    public List<PostInfo> getPostList(HttpServletRequest request) {
        return postDAO.selectPostList( request );
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
    public void deletePost(PostInfo post) {
        postDAO.deletePost(post);
        return;
    }
}
