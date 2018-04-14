package com.kolon.comlife.admin.reservation.model;

import org.apache.ibatis.type.Alias;

@Alias("reservationGroupInfo")
public class ReservationGroupInfo {
    private int idx;
    private int cmplxIdx;
    private String cmplxNm;
    private String icon;
    private String title;
    private String summary;
    private String description;
    private String delYn;
    private String regDttm;
    private String updDttm;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getCmplxIdx() {
        return cmplxIdx;
    }

    public void setCmplxIdx(int cmplxIdx) {
        this.cmplxIdx = cmplxIdx;
    }

    public String getCmplxNm() {
        return cmplxNm;
    }

    public void setCmplxNm(String cmplxNm) {
        this.cmplxNm = cmplxNm;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
