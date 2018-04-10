package com.kolon.comlife.users.service.impl;

import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.model.UserInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public int setUserExt( int usrId ) {
        Map params = new HashMap();
        params.put("usrId", Integer.valueOf(usrId ) );
        return sqlSession.insert("User.insertUserExt", params );
    }

    public UserInfo getUsrIdByUserIdAndPwd( String userId, String userPw ) {
        Map params = new HashMap();
        params.put("userId", userId);
        params.put("userPw", userPw);
        return sqlSession.selectOne( "User.selectUsrIdByUserIdAndPw", params );
    }
}
