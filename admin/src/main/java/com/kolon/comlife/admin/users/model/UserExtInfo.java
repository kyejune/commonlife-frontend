package com.kolon.comlife.admin.users.model;

public class UserExtInfo extends UserInfo {

    private int usrInfoExtId;
    private String avatarImgSrc;
    private String userAlias;

    private String cmplxNm; //


    public int getUsrInfoExtId() {
        return usrInfoExtId;
    }

    public void setUsrInfoExtId(int usrInfoExtId) {
        this.usrInfoExtId = usrInfoExtId;
    }

    public String getAvatarImgSrc() {
        return avatarImgSrc;
    }

    public void setAvatarImgSrc(String avatarImgSrc) {
        this.avatarImgSrc = avatarImgSrc;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }
}
