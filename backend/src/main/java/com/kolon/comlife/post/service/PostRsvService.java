package com.kolon.comlife.post.service;

import com.kolon.comlife.post.exception.ReservedAlreadyException;
import com.kolon.comlife.post.exception.PostRsvGeneralException;
import com.kolon.comlife.post.model.PostRsvInfo;
import com.kolon.comlife.post.model.PostRsvStatusInfo;

import java.util.List;

public interface PostRsvService {

    PostRsvInfo getRsvInfoWithUserListByPostId(int postId );

    PostRsvStatusInfo requestRsv(int parentIdx, int usrId ) throws PostRsvGeneralException, ReservedAlreadyException;

    PostRsvStatusInfo cancelRsv(int parentIdx, int usrId) throws PostRsvGeneralException, ReservedAlreadyException;
}
