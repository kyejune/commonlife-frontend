package com.kolon.comlife.users.model;



// cmplex
// 동:dong, 호:ho, 세대주이름:headNm,
// 세대주 휴대폰 번호:headCell, 세대주 검증된 번호: headCertNum, 사용자검증번호 : userCertId }
public class RegistrationInfo {
    private int userCertId;         // 사용자가입 검증 번호(i.e. 세션값)

    private String cmplxId;         // 현장ID,
    private String dong;            // 현장의 동
    private String ho;              // 현장/동의 호

    private String headNm;          // 세대주 이름
    private String headCell;        // 세대주 전화번호
    private String headCellCertNum; // 세대주 전화번호 확인번호

    private String userId;          // 사용자 ID
    private String userPwd;         // 사용자 PASSWORD todo: 내용 변경 필요
    private String userNm;          // 사용자 이름
    private String userCell;        // 사용자 전화번호
    private String userCellCertNum; // 사용자 전화번호 확인번호
}
