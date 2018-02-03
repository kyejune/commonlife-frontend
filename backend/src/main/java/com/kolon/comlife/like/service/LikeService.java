package com.kolon.comlife.like.service;

import com.kolon.comlife.like.model.LikeInfo;

import java.util.List;

public interface LikeService {

    public List<LikeInfo> getLikeList( int parentIdx );
    public boolean hasLike( int parentIdx, int usrIdx );
    public LikeInfo addLike( int parentIdx, int usrIdx );
    public void cancelLike( int parentIdx, int usrIdx );

}
