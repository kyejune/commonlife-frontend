package com.kolon.comlife.admin.reservation.model;

import com.kolon.comlife.admin.users.model.UserExtInfo;
import com.kolon.comlife.admin.users.model.UserInfo;
import org.apache.ibatis.type.Alias;

@Alias("reservationInfo")
public class ReservationInfo {
    private int idx;
    private int parentIdx;
    private int usrID;
    private String status;
    private String startDt;
    private String startTime;
    private String endDt;
    private String endTime;
    private int point;
    private int amount;
    private int qty;
    private String regDttm;
    private String updDttm;

    // Relations
    private UserExtInfo user;
    private ReservationSchemeInfo scheme;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getParentIdx() {
        return parentIdx;
    }

    public void setParentIdx(int parentIdx) {
        this.parentIdx = parentIdx;
    }

    public int getUsrID() {
        return usrID;
    }

    public void setUsrID(int usrID) {
        this.usrID = usrID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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

    // Relations


    public UserExtInfo getUser() {
        return user;
    }

    public void setUser(UserExtInfo user) {
        this.user = user;
    }

    public ReservationSchemeInfo getScheme() {
        return scheme;
    }

    public void setScheme(ReservationSchemeInfo scheme) {
        this.scheme = scheme;
    }
}
