package com.kolon.comlife.push.model;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("pushHistoryInfo")
public class PushHistoryInfo {
    private int pushID;
    private String msgType;
    private int cmplxID;
    private int homeID;
    private String userID;
    private Date sendDt;
    private String subject;
    private String msg;
    private int scnaID;
    private int thingsID;
    private int dashID;
    private String rcvYn;
    private String regID;
    private Date regDt;

    public int getPushID() {
        return pushID;
    }

    public void setPushID(int pushID) {
        this.pushID = pushID;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public int getCmplxID() {
        return cmplxID;
    }

    public void setCmplxID(int cmplxID) {
        this.cmplxID = cmplxID;
    }

    public int getHomeID() {
        return homeID;
    }

    public void setHomeID(int homeID) {
        this.homeID = homeID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getSendDt() {
        return sendDt;
    }

    public void setSendDt(Date sendDt) {
        this.sendDt = sendDt;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getScnaID() {
        return scnaID;
    }

    public void setScnaID(int scnaID) {
        this.scnaID = scnaID;
    }

    public int getThingsID() {
        return thingsID;
    }

    public void setThingsID(int thingsID) {
        this.thingsID = thingsID;
    }

    public int getDashID() {
        return dashID;
    }

    public void setDashID(int dashID) {
        this.dashID = dashID;
    }

    public String getRcvYn() {
        return rcvYn;
    }

    public void setRcvYn(String rcvYn) {
        this.rcvYn = rcvYn;
    }

    public String getRegID() {
        return regID;
    }

    public void setRegID(String regID) {
        this.regID = regID;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }
}
