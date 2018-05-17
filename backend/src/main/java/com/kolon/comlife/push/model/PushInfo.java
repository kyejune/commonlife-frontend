package com.kolon.comlife.push.model;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("pushInfo")
public class PushInfo {
    private int pushID;
    private int cmplxID;
    private int homeID;
    private String gcmRegID;
    private String deviceID;
    private String usrID;
    private String osType;
    private String regID;
    private Date regDt;

    public int getPushID() {
        return pushID;
    }

    public void setPushID(int pushID) {
        this.pushID = pushID;
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

    public String getGcmRegID() {
        return gcmRegID;
    }

    public void setGcmRegID(String gcmRegID) {
        this.gcmRegID = gcmRegID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUsrID() {
        return usrID;
    }

    public void setUsrID(String usrID) {
        this.usrID = usrID;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
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
