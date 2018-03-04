package com.kolon.comlife.admin.complexes.model;

import com.kolon.common.admin.model.BaseInfo;

public class ComplexInfoBase extends BaseInfo {
    protected int    cmplxId;            // ID, PK
    protected String cmplxGrp;           // COMPLEX GROUP
    protected int    cmplxGrpId;           // COMPLEX GROUP
    protected String cmplxNm;            // COMPLEX NAME
    protected String clMapSrc;      // CommonLife Only Map Source
    protected String clLogoImgSrc;  // CommonLife Only Logo Image Source


    public ComplexInfoBase(int cmplxId, String cmplxGrp, String cmplxNm) {
        this.cmplxId = cmplxId;
        this.cmplxGrp = cmplxGrp;
        this.cmplxNm = cmplxNm;
    }

    public int getCmplxId() {
        return cmplxId;
    }

    public void setCmplxId(int cmplxId) {
        this.cmplxId = cmplxId;
    }

    public String getCmplxGrp() {
        return cmplxGrp;
    }

    public void setCmplxGrp(String cmplxGrp) {
        this.cmplxGrp = cmplxGrp;
    }

    public String getCmplxNm() {
        return cmplxNm;
    }

    public void setCmplxNm(String cmplxNm) {
        this.cmplxNm = cmplxNm;
    }

    public int getCmplxGrpId() {
        return cmplxGrpId;
    }

    public void setCmplxGrpId(int cmplxGrpId) {
        this.cmplxGrpId = cmplxGrpId;
    }

    public String getClMapSrc() {
        return clMapSrc;
    }

    public void setClMapSrc(String clMapSrc) {
        this.clMapSrc = clMapSrc;
    }

    public String getClLogoImgSrc() {
        return clLogoImgSrc;
    }

    public void setClLogoImgSrc(String clLogoImgSrc) {
        this.clLogoImgSrc = clLogoImgSrc;
    }
}
