package com.kolon.comlife.admin.info.model;

public class InfoUserProfile {
    String userImgSrc;
    String userId;
    String userNm;
    String email;

    String cmplxNm;
    String cmplxAddr;

    public String getUserImgSrc() {
        return userImgSrc;
    }

    public void setUserImgSrc(String userImgSrc) {
        this.userImgSrc = userImgSrc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCmplxNm() {
        return cmplxNm;
    }

    public void setCmplxNm(String cmplxNm) {
        this.cmplxNm = cmplxNm;
    }

    public String getCmplxAddr() {
        return cmplxAddr;
    }

    public void setCmplxAddr(String cmplxAddr) {
        this.cmplxAddr = cmplxAddr;
    }
}
