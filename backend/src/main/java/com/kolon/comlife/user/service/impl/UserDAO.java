package com.kolon.comlife.user.service.impl;

import com.kolon.comlife.user.model.UserInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("userDAO")
public class UserDAO {
    @Resource
    private SqlSession sqlSession;

    public List<UserInfo> getUserListById(List<Integer> ids) {
        return sqlSession.selectList( "User.selectUserListById", ids );
    }
}
