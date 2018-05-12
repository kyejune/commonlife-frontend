package com.kolon.comlife.post.service;

import com.kolon.comlife.post.model.LikeInfo;
import com.kolon.comlife.post.model.LikeStatusInfo;

import java.util.List;

public interface LikeService {

    public List<LikeInfo> getLikeList( int parentIdx );
    public boolean hasLike( int parentIdx, int usrIdx );

    public LikeStatusInfo addLike(int parentIdx, int usrId );

    public LikeStatusInfo cancelLike( int parentIdx, int usrId );
}
