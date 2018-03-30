package com.kolon.comlife.post.model;

import com.kolon.comlife.users.model.PostUserInfo;
import org.apache.ibatis.type.Alias;

@Alias("postRsvItemInfo")
public class PostRsvItemInfo {
    private int rsvItemIdx;
    private String postType;
    private int parentIdx;
    private int usrId;
    private String delYn;
    private String regDttm;
    private String updDttm;

    private PostUserInfo user;

    public int getRsvItemIdx() {
        return rsvItemIdx;
    }

    public void setRsvItemIdx(int rsvItemIdx) {
        this.rsvItemIdx = rsvItemIdx;
    }

    public int getParentIdx() {
        return parentIdx;
    }

    public void setParentIdx(int parentIdx) {
        this.parentIdx = parentIdx;
    }

    public String getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(String regDttm) {
        this.regDttm = regDttm;
    }

    public String getUpdDttm() {
        return updDttm;
    }

    public void setUpdDttm(String updDttm) {
        this.updDttm = updDttm;
    }

    public PostUserInfo getUser() {
        return user;
    }

    public void setUser(PostUserInfo user) {
        this.user = user;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }
}