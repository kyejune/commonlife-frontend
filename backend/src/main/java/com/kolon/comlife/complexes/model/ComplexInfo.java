package com.kolon.comlife.complexes.model;

public class ComplexInfo extends ComplexInfoBase {
    private String cmplxCd;     // COMPLEX CODE
    private String cmplxSNm;    // COMPLEX SUB NAME
    private double latiPos;     // Latitude Postion
    private double longPos;     // Longitute Postion
    private String unitCnt;     // Unit Count
    private String addr;        // Address
    private String addrDtl;     // Address Detail
    private String addr1;       // Address 1
    private String addr2;       // Address 2
    private String loc1Nm;
    private String loc2Nm;
    private String loc3Nm;
    private String postNo;
    private String imgSrc;
    private String mapSrc;
    private String logoImgSrc;  // LOGO IMAGE SOURCE
    private String mnlSrc;      // ??
    private String clCmplxNm;       // CL_CMPLX_NM
    private String clCmplxAddr;     // CL_CMPLX_ADDR
    private String clMapSrc;   // CL_MAP_SRC
    private String clLogoImgSrc;    // CL_LOGO_IMG_SRC



    public ComplexInfo() {
        super(0,null, null);
    }

    public ComplexInfo(int cmplxId) {
        super(cmplxId, null, null);
        this.cmplxId = cmplxId;
    }

    public ComplexInfo(
            int cmplxId, String cmplxNm, String cmplxGrp,
            String cmplxCd, String cmplxSNm, double latiPos,
            double longPos, String unitCnt, String addr,
            String addrDtl, String addr1, String loc1Nm,
            String loc2Nm, String loc3Nm,  String postNo,
            String imgSrc, String mapSrc, String logoImgSrc,
            String mnlSrc) {

        super(cmplxId, cmplxNm, cmplxGrp);
        this.cmplxCd = cmplxCd;
        this.cmplxSNm = cmplxSNm;
        this.latiPos = latiPos;
        this.longPos = longPos;
        this.unitCnt = unitCnt;
        this.addr = addr;
        this.addrDtl = addrDtl;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.loc1Nm = loc1Nm;
        this.loc2Nm = loc2Nm;
        this.loc3Nm = loc3Nm;
        this.postNo = postNo;
        this.imgSrc = imgSrc;
        this.mapSrc = mapSrc;
        this.logoImgSrc = logoImgSrc;
        this.mnlSrc = mnlSrc;
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

    public String getCmplxCd() {
        return cmplxCd;
    }

    public void setCmplxCd(String cmplxCd) {
        this.cmplxCd = cmplxCd;
    }

    public String getCmplxSNm() {
        return cmplxSNm;
    }

    public void setCmplxSNm(String cmplxSNm) {
        this.cmplxSNm = cmplxSNm;
    }

    public double getLatiPos() {
        return latiPos;
    }

    public void setLatiPos(double latiPos) {
        this.latiPos = latiPos;
    }

    public double getLongPos() {
        return longPos;
    }

    public void setLongPos(double longPos) {
        this.longPos = longPos;
    }

    public String getUnitCnt() {
        return unitCnt;
    }

    public void setUnitCnt(String unitCnt) {
        this.unitCnt = unitCnt;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrDtl() {
        return addrDtl;
    }

    public void setAddrDtl(String addrDtl) {
        this.addrDtl = addrDtl;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getLoc1Nm() {
        return loc1Nm;
    }

    public void setLoc1Nm(String loc1Nm) {
        this.loc1Nm = loc1Nm;
    }

    public String getLoc2Nm() {
        return loc2Nm;
    }

    public void setLoc2Nm(String loc2Nm) {
        this.loc2Nm = loc2Nm;
    }

    public String getLoc3Nm() {
        return loc3Nm;
    }

    public void setLoc3Nm(String loc3Nm) {
        this.loc3Nm = loc3Nm;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getMapSrc() {
        return mapSrc;
    }

    public void setMapSrc(String mapSrc) {
        this.mapSrc = mapSrc;
    }

    public String getLogoImgSrc() {
        return logoImgSrc;
    }

    public void setLogoImgSrc(String logoImgSrc) {
        this.logoImgSrc = logoImgSrc;
    }

    public String getMnlSrc() {
        return mnlSrc;
    }

    public void setMnlSrc(String mnlSrc) {
        this.mnlSrc = mnlSrc;
    }
}
