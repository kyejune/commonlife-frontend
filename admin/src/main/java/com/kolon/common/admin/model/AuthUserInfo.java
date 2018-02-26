package com.kolon.common.admin.model;


/**
 * 인증 성공시, Redis에서 가져오는 사용자 정보 클래스
 */

public class AuthUserInfo {

    int    cmplxId;     // 현장(table) ID
    int    usrId;       // 사용자(table) ID
    String userId;      // 사용자 로그인 ID
    String tokenOrg;    // Token payload
    String secretKey;
    String issueDate;
    String expireDate;
    String authorizationDefault;     // ??
    String authorizationUserInfo;    // ??
    String authorizationSystem;  // ??

    public AuthUserInfo() {
    }

    public AuthUserInfo( int cmplxId, int usrId, String userId,
                         String tokenOrg, String secretKey, String issueDate,
                         String expireDate, String authorizationDefault, String authorizationUserInfo,
                         String authorizationSystem) {
        this.cmplxId = cmplxId;
        this.usrId = usrId;
        this.userId = userId;
        this.tokenOrg = tokenOrg;
        this.secretKey = secretKey;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.authorizationDefault = authorizationDefault;
        this.authorizationUserInfo = authorizationUserInfo;
        this.authorizationSystem = authorizationSystem;
    }

    public int getUsrId() { return usrId; }

    public void setUsrId(int usrId) { this.usrId = usrId; }

    public int getCmplxId() {
        return cmplxId;
    }

    public void setCmplxId(int cmplxId) {
        this.cmplxId = cmplxId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTokenOrg() {
        return tokenOrg;
    }

    public void setTokenOrg(String tokenOrg) {
        this.tokenOrg = tokenOrg;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getAuthorizationDefault() {
        return authorizationDefault;
    }

    public void setAuthorizationDefault(String authorizationDefault) {
        this.authorizationDefault = authorizationDefault;
    }

    public String getAuthorizationUserInfo() {
        return authorizationUserInfo;
    }

    public void setAuthorizationUserInfo(String authorizationUserInfo) {
        this.authorizationUserInfo = authorizationUserInfo;
    }

    public String getAuthorizationSystem() {
        return authorizationSystem;
    }

    public void setAuthorizationSystem(String authorizationSystem) {
        this.authorizationSystem = authorizationSystem;
    }
}
