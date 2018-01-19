package com.kolon.comlife.post.service;

import com.kolon.comlife.post.model.PostInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostService {

    public PostInfo getPost( int id );

    public List<PostInfo> getPostList(HttpServletRequest request);

    public PostInfo setPost(PostInfo example);

    public PostInfo updatePost(PostInfo example);

    public void deletePost(PostInfo example);

}
