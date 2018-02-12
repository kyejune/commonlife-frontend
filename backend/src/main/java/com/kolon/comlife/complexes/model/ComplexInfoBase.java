package com.kolon.comlife.complexes.model;

public class ComplexInfoBase {
    protected int    cmplxId;            // ID, PK
    protected String cmplxGrp;           // COMPLEX GROUP
    protected String cmplxNm;            // COMPLEX NAME
    private   String comlifeLogoImgSrc;  // LOGO IMAGE SOURCE

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
}