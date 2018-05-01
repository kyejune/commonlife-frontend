package com.kolon.comlife.admin.users.model;

public class UserExtInfo extends UserInfo {

    private int usrInfoExtId;
    private String avatarImgSrc;
    private String userAlias;

    private String cmplxNm; //
    private String headNm;

    // UserInfo

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

    // Getter & Setter

    public int getUsrInfoExtId() {
        return usrInfoExtId;
    }

    public void setUsrInfoExtId(int usrInfoExtId) {
        this.usrInfoExtId = usrInfoExtId;
    }

    public String getAvatarImgSrc() {
        return avatarImgSrc;
    }

    public void setAvatarImgSrc(String avatarImgSrc) {
        this.avatarImgSrc = avatarImgSrc;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getHeadNm() {
        return headNm;
    }

    public void setHeadNm(String headNm) {
        this.headNm = headNm;
    }



    // Getter & Setter of UserInfo

    public String getCmplxNm() {
        return cmplxNm;
    }

    public void setCmplxNm(String cmplxNm) {
        this.cmplxNm = cmplxNm;
    }

    @Override
    public String getUserClass() {
        return userClass;
    }

    @Override
    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    @Override
    public String getKind() {
        return kind;
    }

    @Override
    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    @Override
    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    @Override
    public String getUserNm() {
        return userNm;
    }

    @Override
    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    @Override
    public String getCell() {
        return cell;
    }

    @Override
    public void setCell(String cell) {
        this.cell = cell;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getCellYn() {
        return cellYn;
    }

    @Override
    public void setCellYn(String cellYn) {
        this.cellYn = cellYn;
    }

    @Override
    public String getEmailYn() {
        return emailYn;
    }

    @Override
    public void setEmailYn(String emailYn) {
        this.emailYn = emailYn;
    }

    @Override
    public String getLiveYn() {
        return liveYn;
    }

    @Override
    public void setLiveYn(String liveYn) {
        this.liveYn = liveYn;
    }

    @Override
    public String getInDt() {
        return inDt;
    }

    @Override
    public void setInDt(String inDt) {
        this.inDt = inDt;
    }

    @Override
    public String getOutDt() {
        return outDt;
    }

    @Override
    public void setOutDt(String outDt) {
        this.outDt = outDt;
    }

    @Override
    public String getReDt() {
        return reDt;
    }

    @Override
    public void setReDt(String reDt) {
        this.reDt = reDt;
    }

    @Override
    public String getSmsChkYn() {
        return smsChkYn;
    }

    @Override
    public void setSmsChkYn(String smsChkYn) {
        this.smsChkYn = smsChkYn;
    }

    @Override
    public String getSmsChkNo() {
        return smsChkNo;
    }

    @Override
    public void setSmsChkNo(String smsChkNo) {
        this.smsChkNo = smsChkNo;
    }

    @Override
    public String getSmsChkDt() {
        return smsChkDt;
    }

    @Override
    public void setSmsChkDt(String smsChkDt) {
        this.smsChkDt = smsChkDt;
    }

    @Override
    public String getAprvSts() {
        return aprvSts;
    }

    @Override
    public void setAprvSts(String aprvSts) {
        this.aprvSts = aprvSts;
    }

    @Override
    public int getCmplxId() {
        return cmplxId;
    }

    @Override
    public void setCmplxId(int cmplxId) {
        this.cmplxId = cmplxId;
    }

    @Override
    public String getDong() {
        return dong;
    }

    @Override
    public void setDong(String dong) {
        this.dong = dong;
    }

    @Override
    public String getHo() {
        return ho;
    }

    @Override
    public void setHo(String ho) {
        this.ho = ho;
    }

    @Override
    public String getHeadId() {
        return headId;
    }

    @Override
    public void setHeadId(String headId) {
        this.headId = headId;
    }

    @Override
    public String getLoginYn() {
        return loginYn;
    }

    @Override
    public void setLoginYn(String loginYn) {
        this.loginYn = loginYn;
    }

    @Override
    public String getSipUserId() {
        return sipUserId;
    }

    @Override
    public void setSipUserId(String sipUserId) {
        this.sipUserId = sipUserId;
    }

    @Override
    public String getSipUserCancel() {
        return sipUserCancel;
    }

    @Override
    public void setSipUserCancel(String sipUserCancel) {
        this.sipUserCancel = sipUserCancel;
    }

    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenIssueDt() {
        return tokenIssueDt;
    }

    @Override
    public void setTokenIssueDt(String tokenIssueDt) {
        this.tokenIssueDt = tokenIssueDt;
    }

    public String getTokenValidDt() {
        return tokenValidDt;
    }

    @Override
    public void setTokenValidDt(String tokenValidDt) {
        this.tokenValidDt = tokenValidDt;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    @Override
    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getTokenPayload() {
        return tokenPayload;
    }

    @Override
    public void setTokenPayload(String tokenPayload) {
        this.tokenPayload = tokenPayload;
    }

    public String getTokenSignature() {
        return tokenSignature;
    }

    @Override
    public void setTokenSignature(String tokenSignature) {
        this.tokenSignature = tokenSignature;
    }

    public String getTokenEncrypt() {
        return tokenEncrypt;
    }

    @Override
    public void setTokenEncrypt(String tokenEncrypt) {
        this.tokenEncrypt = tokenEncrypt;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    @Override
    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getSecretKey() {
        return secretKey;
    }

    @Override
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getGwToken() {
        return gwToken;
    }

    @Override
    public void setGwToken(String gwToken) {
        this.gwToken = gwToken;
    }

    public String getGwValidDt() {
        return gwValidDt;
    }

    @Override
    public void setGwValidDt(String gwValidDt) {
        this.gwValidDt = gwValidDt;
    }

    @Override
    public String getGwAccessId() {
        return gwAccessId;
    }

    @Override
    public void setGwAccessId(String gwAccessId) {
        this.gwAccessId = gwAccessId;
    }
}
