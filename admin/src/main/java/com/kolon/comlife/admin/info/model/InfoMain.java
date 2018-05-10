package com.kolon.comlife.admin.info.model;

import java.util.List;

public class InfoMain {
    String userImgSrc;
    String userNm;
    String cmplxNm;
    String dong;
    String ho;
    String startDt;
    int point;
    String notice;
    List<InfoData> infoList;

    public String getUserImgSrc() {
        return userImgSrc;
    }

    public void setUserImgSrc(String userImgSrc) {
        this.userImgSrc = userImgSrc;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getCmplxNm() {
        return cmplxNm;
    }

    public void setCmplxNm(String cmplxNm) {
        this.cmplxNm = cmplxNm;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public List<InfoData> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<InfoData> infoList) {
        this.infoList = infoList;
    }
}
