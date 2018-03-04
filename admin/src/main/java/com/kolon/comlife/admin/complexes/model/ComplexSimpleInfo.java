package com.kolon.comlife.admin.complexes.model;


/**
 *
 */
public class ComplexSimpleInfo extends ComplexInfoBase {
    private String addr;        // Address
    private String addrDtl;     // Address Detail
    private String addr1;       // Address 1
    private String addr2;       // Address 2

    public ComplexSimpleInfo() {
        super(0, null, null);
    }
    public ComplexSimpleInfo(int cmplxId, String cmplxGrp, String cmplxNm) {
        super(cmplxId, cmplxGrp, cmplxNm);
    }

    public ComplexSimpleInfo(
            int cmplxId, String cmplxGrp, String cmplxNm, String addr, String addrDtl,
            String addr1, String addr2 ) {
        super(cmplxId, cmplxGrp, cmplxNm);
        this.addr = addr;
        this.addrDtl = addrDtl;
        this.addr1 = addr1;
        this.addr2 = addr2;
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

}
