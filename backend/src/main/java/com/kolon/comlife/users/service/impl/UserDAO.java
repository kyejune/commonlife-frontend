package com.kolon.comlife.users.service.impl;

import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.model.UserInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("userDAO")
public class UserDAO {
    @Resource
    private SqlSession sqlSession;

    public List<PostUserInfo> getUserListForPostById(List<Integer> ids) {
        return sqlSession.selectList( "User.selectUserListForPostById", ids );
    }

    public List<UserInfo> getUserListById(List<Integer> ids) {
        return sqlSession.selectList( "User.selectUserListById", ids );
    }
}
