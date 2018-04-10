package com.kolon.comlife.users.service.impl;

import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.model.UserInfo;
import com.kolon.comlife.users.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserSerivceImpl implements UserService {
    @Resource(name = "userDAO")
    private UserDAO userDAO;

    @Override
    public List<PostUserInfo> getUserListForPostById(List<Integer> ids) {
        return userDAO.getUserListForPostById( ids );
    }

    @Override
    public List<UserInfo> getUserListById( List<Integer> ids ) {
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
