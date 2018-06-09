package com.kolon.comlife.complexes.model;


/**
 *
 */
public class ComplexSimpleInfo extends ComplexInfoBase {
    private String addr;        // Address
    private String addrDtl;     // Address Detail
    private String addr1;       // Address 1
    private String addr2;       // Address 2
    private String clMapSrc;      // CommonLife Only Map Source
    private String clLogoImgSrc;  // CommonLife Only Logo Image Source

    private int    logoImgIdx;

    public ComplexSimpleInfo() {
        super(0, null, null);
    }
    public ComplexSimpleInfo(int cmplxId, String cmplxGrp, String cmplxNm) {
        super(cmplxId, cmplxGrp, cmplxNm);
    }

    public ComplexSimpleInfo(
            int cmplxId, String cmplxGrp, String cmplxNm, String addr, String addrDtl,
            String addr1, String addr2, String clMapSrc, String clLogoImgSrc) {
        super(cmplxId, cmplxGrp, cmplxNm);
        this.addr = addr;
        this.addrDtl = addrDtl;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.clMapSrc = clMapSrc;
        this.clLogoImgSrc = clLogoImgSrc;
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

    public int getLogoImgIdx() {
        return logoImgIdx;
    }

    public void setLogoImgIdx(int logoImgIdx) {
        this.logoImgIdx = logoImgIdx;
    }
}
