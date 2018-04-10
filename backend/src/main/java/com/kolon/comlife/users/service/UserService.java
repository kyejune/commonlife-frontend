package com.kolon.comlife.users.service;

import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.model.UserInfo;

import java.util.List;

public interface UserService {

    List<PostUserInfo> getUserListForPostById(List<Integer> ids);
    List<UserInfo> getUserListById(List<Integer> ids );
    UserInfo getUsrIdByUserIdAndPwd( String userId, String userPw );
}
