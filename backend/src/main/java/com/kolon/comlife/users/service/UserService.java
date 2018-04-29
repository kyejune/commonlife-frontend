package com.kolon.comlife.users.service;

import com.kolon.comlife.users.exception.NotFoundException;
import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.model.UserInfo;
import com.kolon.comlife.users.model.UserProfileInfo;

import java.util.List;

public interface UserService {

    // ID 리스트로 (Feed에 표시 용도) 사용자 목록 가져오기
    List<PostUserInfo> getUserListForPostById(List<Integer> ids);

    // ID 리스트로 사용자 상세 정보 목록 가져오기
    List<UserInfo> getUserListById(List<Integer> ids );

    // 사용자 상세 정보 가져오기
    UserInfo getUsrIdByUserIdAndPwd( String userId, String userPw );

    // 가입된 회원인지 확인
    boolean isExistedUser( String userId );

    // 사용자의 프로필 정보 조회
    UserProfileInfo getUserProfile( int usrId ) throws NotFoundException;
}
