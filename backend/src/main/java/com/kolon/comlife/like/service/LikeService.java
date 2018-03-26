package com.kolon.comlife.like.service;

import com.kolon.comlife.like.model.LikeInfo;
import com.kolon.comlife.like.model.LikeStatusInfo;

import java.util.List;

public interface LikeService {

    public List<LikeInfo> getLikeList( int parentIdx );
    public boolean hasLike( int parentIdx, int usrIdx );

    public LikeStatusInfo addLike(int parentIdx, int usrId );
    public LikeStatusInfo cancelLike( int parentIdx, int usrId );
}
