package com.kolon.comlife.post.service;

import com.kolon.comlife.like.model.LikeInfo;
import com.kolon.comlife.like.model.LikeStatusInfo;

import java.util.List;

public interface PostRsvService {

    public List<LikeInfo> getLikeList(int parentIdx);
    public boolean hasLike(int parentIdx, int usrIdx);

    public LikeStatusInfo requestRsv(int parentIdx, int usrId);

    public LikeStatusInfo cancelRsv(int parentIdx, int usrId);
}
