package com.kolon.comlife.admin.post.model;

import com.kolon.comlife.common.model.SimpleMsgInfo;

public class LikeStatusInfo extends SimpleMsgInfo {
    private int     likeCount;
    private boolean myLikeFlag;

    public LikeStatusInfo() {
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isMyLikeFlag() {
        return myLikeFlag;
    }

    public void setMyLikeFlag(boolean myLikeFlag) {
        this.myLikeFlag = myLikeFlag;
    }
}
