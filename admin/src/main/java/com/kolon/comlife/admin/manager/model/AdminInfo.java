package com.kolon.comlife.admin.manager.model;

import com.kolon.common.admin.model.BaseUserInfo;
import org.apache.ibatis.type.Alias;

/**
 * 관리자 Value Object
 * @author nacsde
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  ----------   --------    ---------------------------
 *   2017-07-31    조신득          최초 생성
 * </pre>
 */
@Alias("adminInfo")
public class AdminInfo extends BaseUserInfo {
    private int adminIdx;
    private String adminId;
    private String adminPw;
    private String adminNm;
    private String adminEmail;
    private int grpId;
    private String grpNm; // 그룹 이름 (슈퍼관리자 or 현장관리자)
    private String desc;

    private int regAdminIdx;
    private int updAdminIdx;

    private int cmplxId; // 현장 ID
    private String cmplxNm; // 현장관리자의 경우(현장 이름)

    private String mngNm;
    private String mngEmail;
    private String useYn;
    private String bigo;
    private String regDttm;
    private String regUserId;
    private String updDttm;
    private String updUserId;

    @Override
    public String getMngNm() {
        return mngNm;
    }

    @Override
    public void setMngNm(String mngNm) {
        this.mngNm = mngNm;
    }

    @Override
    public String getMngEmail() {
        return mngEmail;
    }

    @Override
    public void setMngEmail(String mngEmail) {
        this.mngEmail = mngEmail;
    }

    @Override
    public String getUseYn() {
        return useYn;
    }

    @Override
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    @Override
    public String getBigo() {
        return bigo;
    }

    @Override
    public void setBigo(String bigo) {
        this.bigo = bigo;
    }

    @Override
    public String getRegDttm() {
        return regDttm;
    }

    @Override
    public void setRegDttm(String regDttm) {
        this.regDttm = regDttm;
    }

    @Override
    public String getRegUserId() {
        return regUserId;
    }

    @Override
    public void setRegUserId(String regUserId) {
        this.regUserId = regUserId;
    }

    @Override
    public String getUpdDttm() {
        return updDttm;
    }

    @Override
    public void setUpdDttm(String updDttm) {
        this.updDttm = updDttm;
    }

    @Override
    public String getUpdUserId() {
        return updUserId;
    }

    @Override
    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }


    public int getAdminIdx() {
        return adminIdx;
    }

    public void setAdminIdx(int adminIdx) {
        this.adminIdx = adminIdx;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminPw() {
        return adminPw;
    }

    public void setAdminPw(String adminPw) {
        this.adminPw = adminPw;
    }

    public String getAdminNm() {
        return adminNm;
    }

    public void setAdminNm(String adminNm) {
        this.adminNm = adminNm;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public int getGrpId() {
        return grpId;
    }

    public void setGrpId(int grpId) {
        this.grpId = grpId;
    }

    public String getGrpNm() {
        return grpNm;
    }

    public void setGrpNm(String grpNm) {
        this.grpNm = grpNm;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRegAdminIdx() {
        return regAdminIdx;
    }

    public void setRegAdminIdx(int regAdminIdx) {
        this.regAdminIdx = regAdminIdx;
    }

    public int getUpdAdminIdx() {
        return updAdminIdx;
    }

    public void setUpdAdminIdx(int updAdminIdx) {
        this.updAdminIdx = updAdminIdx;
    }


    public int getCmplxId() {
        return cmplxId;
    }

    public void setCmplxId(int cmplxId) {
        this.cmplxId = cmplxId;
    }

    public String getCmplxNm() {
        return cmplxNm;
    }

    public void setCmplxNm(String cmplxNm) {
        this.cmplxNm = cmplxNm;
    }
}
