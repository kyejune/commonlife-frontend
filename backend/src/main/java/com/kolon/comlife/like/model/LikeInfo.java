package com.kolon.comlife.like.model;

import com.kolon.comlife.users.model.UserInfo;
import org.apache.ibatis.type.Alias;

@Alias("likeInfo")
public class LikeInfo {
    private int likeIdx;
    private String parentType;
    private int parentIdx;
    private int usrId;
    private String delYn;
    private String regDttm;
    private String updDttm;

    private UserInfo user;

    public int getLikeIdx() {
        return likeIdx;
    }

    public void setLikeIdx(int likeIdx) {
        this.likeIdx = likeIdx;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public int getParentIdx() {
        return parentIdx;
    }

    public void setParentIdx(int parentIdx) {
        this.parentIdx = parentIdx;
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

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

}
