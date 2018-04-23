package com.kolon.comlife.admin.users.service;

import com.kolon.comlife.admin.users.exception.UserGeneralException;
import com.kolon.comlife.admin.users.model.PostUserInfo;
import com.kolon.comlife.admin.users.model.UserExtInfo;
import com.kolon.comlife.admin.users.model.UserInfo;

import java.util.List;

public interface UserService {

    ////////////////// ADMIN ONLY //////////////////
    int getTotalUserCountByComplexId( int cmplxId ) throws UserGeneralException;

    UserExtInfo getUserExtInfoById(int usrId );

    ////////////////// BACKEND SHARED //////////////////
    List<PostUserInfo> getUserListForPostById(List<Integer> ids);

    List<UserInfo> getUserListById(List<Integer> ids );

    UserInfo getUsrIdByUserIdAndPwd( String userId, String userPw );

    boolean isExistedUser( String userId );
}
