package com.kolon.comlife.users.model;

import com.kolon.comlife.post.model.PostInfo;

import java.util.List;

public class UserInfo {
    private int usrId;
    private String userClass;
    private String kind;
    private String userId;
    private String userPw;
    private String userNm;
    private String cell;
    private String email;
    private String cellYn;
    private String emailYn;
    private String liveYn;
    private String inDt;
    private String outDt;
    private String reDt;
    private String smsChkYn;
    private String smsChkNo;
    private String smsChkDt;
    private String aprvSts;
    private int cmplxId;
    private String dong;
    private String ho;
    private String headId;
    private String loginYn;
    private String sipUserId;
    private String sipUserCancel;
    private String token;
    private String tokenIssueDt;
    private String tokenValidDt;
    private String tokenHeader;
    private String tokenPayload;
    private String tokenSignature;
    private String tokenEncrypt;
    private String tokenIssuer;
    private String secretKey;
    private String gwToken;
    private String gwValidDt;
    private String gwAccessId;
    private String regId;
    private String regDt;
    private String chgId;
    private String chgDt;

    // Relations
    private List<PostInfo> posts;

    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public String getUserPw() {
//        return userPw;
//    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellYn() {
        return cellYn;
    }

    public void setCellYn(String cellYn) {
        this.cellYn = cellYn;
    }

    public String getEmailYn() {
        return emailYn;
    }

    public void setEmailYn(String emailYn) {
        this.emailYn = emailYn;
    }

    public String getLiveYn() {
        return liveYn;
    }

    public void setLiveYn(String liveYn) {
        this.liveYn = liveYn;
    }

    public String getInDt() {
        return inDt;
    }

    public void setInDt(String inDt) {
        this.inDt = inDt;
    }

    public String getOutDt() {
        return outDt;
    }

    public void setOutDt(String outDt) {
        this.outDt = outDt;
    }

    public String getReDt() {
        return reDt;
    }

    public void setReDt(String reDt) {
        this.reDt = reDt;
    }

    public String getSmsChkYn() {
        return smsChkYn;
    }

    public void setSmsChkYn(String smsChkYn) {
        this.smsChkYn = smsChkYn;
    }

    public String getSmsChkNo() {
        return smsChkNo;
    }

    public void setSmsChkNo(String smsChkNo) {
        this.smsChkNo = smsChkNo;
    }

    public String getSmsChkDt() {
        return smsChkDt;
    }

    public void setSmsChkDt(String smsChkDt) {
        this.smsChkDt = smsChkDt;
    }

    public String getAprvSts() {
        return aprvSts;
    }

    public void setAprvSts(String aprvSts) {
        this.aprvSts = aprvSts;
    }

    public int getCmplxId() {
        return cmplxId;
    }

    public void setCmplxId(int cmplxId) {
        this.cmplxId = cmplxId;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getHeadId() {
        return headId;
    }

    public void setHeadId(String headId) {
        this.headId = headId;
    }

    public String getLoginYn() {
        return loginYn;
    }

    public void setLoginYn(String loginYn) {
        this.loginYn = loginYn;
    }

    public String getSipUserId() {
        return sipUserId;
    }

    public void setSipUserId(String sipUserId) {
        this.sipUserId = sipUserId;
    }

    public String getSipUserCancel() {
        return sipUserCancel;
    }

    public void setSipUserCancel(String sipUserCancel) {
        this.sipUserCancel = sipUserCancel;
    }

//    public String getToken() {
//        return token;
//    }

    public void setToken(String token) {
        this.token = token;
    }

//    public String getTokenIssueDt() {
//        return tokenIssueDt;
//    }

    public void setTokenIssueDt(String tokenIssueDt) {
        this.tokenIssueDt = tokenIssueDt;
    }

//    public String getTokenValidDt() {
//        return tokenValidDt;
//    }

    public void setTokenValidDt(String tokenValidDt) {
        this.tokenValidDt = tokenValidDt;
    }

//    public String getTokenHeader() {
//        return tokenHeader;
//    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

//    public String getTokenPayload() {
//        return tokenPayload;
//    }

    public void setTokenPayload(String tokenPayload) {
        this.tokenPayload = tokenPayload;
    }

//    public String getTokenSignature() {
//        return tokenSignature;
//    }

    public void setTokenSignature(String tokenSignature) {
        this.tokenSignature = tokenSignature;
    }

//    public String getTokenEncrypt() {
//        return tokenEncrypt;
//    }

    public void setTokenEncrypt(String tokenEncrypt) {
        this.tokenEncrypt = tokenEncrypt;
    }

//    public String getTokenIssuer() {
//        return tokenIssuer;
//    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

//    public String getSecretKey() {
//        return secretKey;
//    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

//    public String getGwToken() {
//        return gwToken;
//    }

    public void setGwToken(String gwToken) {
        this.gwToken = gwToken;
    }

//    public String getGwValidDt() {
//        return gwValidDt;
//    }

    public void setGwValidDt(String gwValidDt) {
        this.gwValidDt = gwValidDt;
    }

    public String getGwAccessId() {
        return gwAccessId;
    }

    public void setGwAccessId(String gwAccessId) {
        this.gwAccessId = gwAccessId;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getChgId() {
        return chgId;
    }

    public void setChgId(String chgId) {
        this.chgId = chgId;
    }

    public String getChgDt() {
        return chgDt;
    }

    public void setChgDt(String chgDt) {
        this.chgDt = chgDt;
    }

    /*
        Relations Getter and Setter
     */

    public List<PostInfo> getPosts() {
        return posts;
    }

    public void setPosts(List<PostInfo> posts) {
        this.posts = posts;
    }

}
