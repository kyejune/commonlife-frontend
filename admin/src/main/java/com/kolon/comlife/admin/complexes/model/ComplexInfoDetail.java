package com.kolon.comlife.admin.complexes.model;

public class ComplexInfoDetail extends ComplexInfo {


    private String cmplxGrpType; // CMPLX_GRP_TYPE

    private String iokAdminId;  //  CMPLX.ADMIN_ID AS IOK_ADMIN_ID
    private String iokRegId;    //  CMPLX.REG_ID AS IOK_REG_ID,
    private String iokRegDt;    //  CMPLX.REG_DT AS IOK_REG_DT,
    private String iokChgId;    //  CMPLX.CHG_ID AS IOK_CHG_ID,
    private String iokChgDt;    //  CMPLX.CHG_DT AS IOK_CHG_DT,
    private String clMapSrc;    //  CMPLX_EXT.CL_MAP_SRC AS CL_MAP_SRC,
    private String clCmplxNm;    //  CMPLX_EXT.CL_CMPLX_NM AS CL_CMPLX_NM,
    private String clCmplxAddr;    //  CMPLX_EXT.CL_CMPLX_ADDR AS CL_CMPLX_ADDR,
    private String feedWriteAllowYn; // FEED_WRITE_ALLOW_YN
    private String regAdminId; // REG_ADMIN_ID
    private String updAdminId; // UPD_ADMIN_ID

    public ComplexInfoDetail() {

    }


    public String getCmplxGrpType() {
        return cmplxGrpType;
    }

    public void setCmplxGrpType(String cmplxGrpType) {
        this.cmplxGrpType = cmplxGrpType;
    }

    public String getIokAdminId() {
        return iokAdminId;
    }

    public void setIokAdminId(String iokAdminId) {
        this.iokAdminId = iokAdminId;
    }

    public String getIokRegId() {
        return iokRegId;
    }

    public void setIokRegId(String iokRegId) {
        this.iokRegId = iokRegId;
    }

    public String getIokRegDt() {
        return iokRegDt;
    }

    public void setIokRegDt(String iokRegDt) {
        this.iokRegDt = iokRegDt;
    }

    public String getIokChgId() {
        return iokChgId;
    }

    public void setIokChgId(String iokChgId) {
        this.iokChgId = iokChgId;
    }

    public String getIokChgDt() {
        return iokChgDt;
    }

    public void setIokChgDt(String iokChgDt) {
        this.iokChgDt = iokChgDt;
    }

    public String getClMapSrc() {
        return clMapSrc;
    }

    public void setClMapSrc(String clMapSrc) {
        this.clMapSrc = clMapSrc;
    }

    public String getClCmplxNm() {
        return clCmplxNm;
    }

    public void setClCmplxNm(String clCmplxNm) {
        this.clCmplxNm = clCmplxNm;
    }

    public String getClCmplxAddr() {
        return clCmplxAddr;
    }

    public void setClCmplxAddr(String clCmplxAddr) {
        this.clCmplxAddr = clCmplxAddr;
    }

    public String getFeedWriteAllowYn() {
        return feedWriteAllowYn;
    }

    public void setFeedWriteAllowYn(String feedWriteAllowYn) {
        this.feedWriteAllowYn = feedWriteAllowYn;
    }

    public String getRegAdminId() {
        return regAdminId;
    }

    public void setRegAdminId(String regAdminId) {
        this.regAdminId = regAdminId;
    }

    public String getUpdAdminId() {
        return updAdminId;
    }

    public void setUpdAdminId(String updAdminId) {
        this.updAdminId = updAdminId;
    }
}
