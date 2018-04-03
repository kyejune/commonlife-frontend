package com.kolon.comlife.post.service;

import com.kolon.comlife.common.model.PaginateInfo;
import com.kolon.comlife.post.model.PostInfo;

import java.util.List;
import java.util.Map;

public interface PostService {

    PostInfo getPostById(int id, int currUsrId ) throws Exception;

    PaginateInfo getPostWithLikeInfoList(Map params)  throws Exception ;

    List<PostInfo> getPostList(Map params);

    List<PostInfo> getPostListByComplexId(Map params);

    PostInfo setPost(PostInfo example);

    PostInfo setPostWithImage(PostInfo newPost, List<Integer> fileInfo, int usrId);

    PostInfo updatePost(PostInfo example);

    PostInfo deletePost(int id, int usrId);

}
