package com.kolon.comlife.admin.post.service;

import com.kolon.comlife.admin.post.model.PostInfo;
import com.kolon.comlife.common.paginate.PaginateInfo;

import java.util.List;
import java.util.Map;

public interface PostService {

    PostInfo getPostById(int id, int currUsrId) throws Exception;

    PaginateInfo getPostWithLikeInfoList(Map params)  throws Exception ;

    List<PostInfo> getPostList(Map params);

    List<PostInfo> getPostListByComplexId(Map params);

    PostInfo setPost(PostInfo example);

    PostInfo setPostWithImage(PostInfo newPost, List<Integer> fileInfo, int usrId);

    PostInfo updatePost(PostInfo example);

    PostInfo deletePost(int id, int usrId);

}