package com.kolon.comlife.post.model;

import com.kolon.comlife.common.model.SimpleMsgInfo;

public class PostRsvStatusInfo extends SimpleMsgInfo {
    private int     rsvCount;
    private boolean rsvFlag;

    public PostRsvStatusInfo() {
    }

    public int getRsvCount() {
        return rsvCount;
    }

    public void setRsvCount(int rsvCount) {
        this.rsvCount = rsvCount;
    }

    public boolean isRsvFlag() {
        return rsvFlag;
    }

    public void setRsvFlag(boolean rsvFlag) {
        this.rsvFlag = rsvFlag;
    }
}
