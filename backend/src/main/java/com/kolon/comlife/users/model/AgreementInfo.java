package com.kolon.comlife.users.model;

import java.util.Date;

/**
 *  개인정보 취급 정책
 */
public class AgreementInfo {
    int    id;
    String useYn; // 사용 여부
    String content; // 내용
    Date   contentDate; // 내용
    String regAdminId;
    Date   regDttm;

    public AgreementInfo(int id) {
        this.id = id;
    }

    public AgreementInfo(int id, String content, Date contentDate) {
        this.id = id;
        this.content = content;
        this.contentDate = contentDate;
    }

    public AgreementInfo(int id, String useYn, String content, String regAdminId, Date regDttm) {
        this.id = id;
        this.useYn = useYn;
        this.content = content;
        this.regAdminId = regAdminId;
        this.regDttm = regDttm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getContentDate() {
        return contentDate;
    }

    public void setContentDate(Date contentDate) {
        this.contentDate = contentDate;
    }

    public String getRegAdminId() {
        return regAdminId;
    }

    public void setRegAdminId(String regAdminId) {
        this.regAdminId = regAdminId;
    }

    public Date getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(Date regDttm) {
        this.regDttm = regDttm;
    }


}
