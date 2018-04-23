package com.kolon.comlife.admin.users.service.impl;

import com.kolon.comlife.admin.users.exception.UserGeneralException;
import com.kolon.comlife.admin.users.model.PostUserInfo;
import com.kolon.comlife.admin.users.model.UserExtInfo;
import com.kolon.comlife.admin.users.model.UserInfo;
import com.kolon.comlife.admin.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserSerivceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    ////////////////// ADMIN ONLY //////////////////
    // 해당 현장의 전체 회원 가입자 수
    public int getTotalUserCountByComplexId( int cmplxId ) throws UserGeneralException {
        return userDAO.selectUserCountByComplexId( cmplxId );
    }

    public UserExtInfo getUserExtInfoById( int usrId ) {
        return userDAO.selectUserExtInfoByUsrId( usrId );
    }


    ////////////////// BACKEND SHARED //////////////////
    @Override
    public List<PostUserInfo> getUserListForPostById(List<Integer> ids) {
        return userDAO.getUserListForPostById( ids );
    }

    @Override
    public List<UserInfo> getUserListById(List<Integer> ids ) {
        return userDAO.getUserListById( ids );
    }

    @Override
    public UserInfo getUsrIdByUserIdAndPwd( String userId, String userPw ) {
        return userDAO.getUsrIdByUserIdAndPwd( userId, userPw );
    }

    @Override
    public boolean isExistedUser( String userId ) {
        return (userDAO.getUsrIdByUserId( userId ) != null);
    }

}
