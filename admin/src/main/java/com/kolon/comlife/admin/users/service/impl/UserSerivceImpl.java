package com.kolon.comlife.admin.users.service.impl;

import com.kolon.comlife.admin.users.exception.UserGeneralException;
import com.kolon.comlife.admin.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserSerivceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    // 해당 현장의 전체 회원 가입자 수
    public int getTotalUserCountByComplexId( int cmplxId ) throws UserGeneralException {
        return userDAO.selectUserCountByComplexId( cmplxId );
    }

}
