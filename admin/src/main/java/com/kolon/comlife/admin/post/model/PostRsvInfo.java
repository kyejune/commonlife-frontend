package com.kolon.comlife.admin.post.model;

import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("postRsvInfo")
public class PostRsvInfo {
    private int     rsvIdx;
    private int     parentIdx;
    private int     rsvCurrCnt;
    private int     rsvMaxCnt;
    private String  regDttm;
    private String  updDttm;

    List<PostRsvItemInfo> rsvItemInfoList;


    public int getRsvIdx() {
        return rsvIdx;
    }

    public void setRsvIdx(int rsvIdx) {
        this.rsvIdx = rsvIdx;
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


    public int getRsvCurrCnt() {
        return rsvCurrCnt;
    }

    public void setRsvCurrCnt(int rsvCurrCnt) {
        this.rsvCurrCnt = rsvCurrCnt;
    }

    public int getRsvMaxCnt() {
        return rsvMaxCnt;
    }

    public void setRsvMaxCnt(int rsvMaxCnt) {
        this.rsvMaxCnt = rsvMaxCnt;
    }

    public List<PostRsvItemInfo> getRsvItemInfoList() {
        return rsvItemInfoList;
    }

    public void setRsvItemInfoList(List<PostRsvItemInfo> rsvItemInfoList) {
        this.rsvItemInfoList = rsvItemInfoList;
    }
}
