package com.kolon.comlife.admin.post.service;

import com.kolon.comlife.admin.post.exception.NotFoundException;
import com.kolon.comlife.admin.post.exception.OperationFailedException;
import com.kolon.comlife.admin.post.model.PostInfo;
import com.kolon.comlife.common.paginate.PaginateInfo;

import java.util.List;
import java.util.Map;

public interface PostService {

    PostInfo getPostById(int id, int currUsrId) throws NotFoundException;

    PaginateInfo getPostWithLikeInfoList(Map params)  throws Exception ;

    List<PostInfo> getPostList(Map params);

    List<PostInfo> getPostListByComplexId(Map params);

    PostInfo setPostWithImage(PostInfo newPost, List<Integer> fileInfo, int adminIdx);

    PostInfo updatePost(PostInfo example) throws OperationFailedException;

    PostInfo makePostPrivate(int id, int cmplxId, int adminIdx);

    PostInfo makePostPublic(int id, int cmplxId, int adminIdx);

}
