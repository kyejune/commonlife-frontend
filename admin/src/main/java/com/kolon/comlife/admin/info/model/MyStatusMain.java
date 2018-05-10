package com.kolon.comlife.admin.info.model;

import com.kolon.comlife.common.model.SimpleMsgInfo;

import java.util.List;

public class MyStatusMain extends SimpleMsgInfo {
    String cmplxNm;
    String headNm;
    String startDt;

    List<MyStatusInfo> infoList;

    public String getCmplxNm() {
        return cmplxNm;
    }

    public void setCmplxNm(String cmplxNm) {
        this.cmplxNm = cmplxNm;
    }

    public String getHeadNm() {
        return headNm;
    }

    public void setHeadNm(String headNm) {
        this.headNm = headNm;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public List<MyStatusInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<MyStatusInfo> infoList) {
        this.infoList = infoList;
    }
}
