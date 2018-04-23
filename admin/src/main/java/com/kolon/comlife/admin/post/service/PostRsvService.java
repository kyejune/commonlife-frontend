package com.kolon.comlife.admin.post.service;

import com.kolon.comlife.admin.post.exception.PostRsvGeneralException;
import com.kolon.comlife.admin.post.exception.ReservedAlreadyException;
import com.kolon.comlife.admin.post.model.PostRsvInfo;
import com.kolon.comlife.admin.post.model.PostRsvStatusInfo;

public interface PostRsvService {

    PostRsvInfo getRsvInfoWithUserListByPostId(int postId);

    PostRsvStatusInfo requestRsv(int parentIdx, int usrId) throws PostRsvGeneralException, ReservedAlreadyException;

    PostRsvStatusInfo cancelRsv(int parentIdx, int usrId) throws PostRsvGeneralException, ReservedAlreadyException;
}
