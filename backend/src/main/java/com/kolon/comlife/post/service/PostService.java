package com.kolon.comlife.post.service;

import com.kolon.comlife.post.model.PostInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface PostService {

    public PostInfo getPost( int id );

    public int countPostList();

    public List<PostInfo> getPostList(Map params);

    public PostInfo setPost(PostInfo example);

    public PostInfo updatePost(PostInfo example);

    public void deletePost(int id);

}
