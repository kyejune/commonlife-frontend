package com.kolon.comlife.admin.users.service.impl;

import com.kolon.comlife.admin.users.model.PostUserInfo;
import com.kolon.comlife.admin.users.model.UserExtInfo;
import com.kolon.comlife.admin.users.model.UserInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userDAO")
public class UserDAO {
    @Resource
    private SqlSession sqlSession;

    ////////////////// ADMIN ONLY //////////////////
    public int selectUserCountByComplexId(int cmplxId) {
        Map params = new HashMap();
        Map result;

        params.put("cmplxId", cmplxId);

        result = sqlSession.selectOne("User.selectUserCountByComplexId", params);

        return ((Long)result.get("USER_COUNT")).intValue();
    }

    public int selectHomeHeadCountByComplexId(int cmplxId) {
        Map params = new HashMap();
        Map result;

        params.put("cmplxId", cmplxId);

        result = sqlSession.selectOne("User.selectHomeHeadCountByComplexId", params);

        return ((Long)result.get("HOME_HEAD_COUNT")).intValue();
    }

    public UserExtInfo selectUserExtInfoByUsrId( int usrId ) {
        Map params = new HashMap();

        params.put("usrId", usrId);

        return sqlSession.selectOne( "User.selectUserExtInfoByUsrId", params);
    }


    ////////////////// BACKEND SHARED //////////////////
    public List<UserExtInfo> getUserListByComplexId(Map params) {
        return sqlSession.selectList( "User.selectUserListByCmplxId", params );
    }

    public int countUserList(Map params) {
        return sqlSession.selectOne( "User.countUserList", params );
    }

    public List<UserExtInfo> getHeadListByComplexId(Map params) {
        return sqlSession.selectList( "User.selectHeadListByCmplxId", params );
    }

    public int countHeadList(Map params) {
        return sqlSession.selectOne( "User.countHeadList", params );
    }


    public List<PostUserInfo> getUserListForPostById(List<Integer> ids) {
        return sqlSession.selectList( "User.selectUserListForPostById", ids );
    }

    public List<UserInfo> getUserListById(List<Integer> ids) {
        return sqlSession.selectList( "User.selectUserListById", ids );
    }

    public UserInfo getUserById( int usrId ) {
        List<Integer> usrIdList = new ArrayList<>();
        usrIdList.add( new Integer( usrId ) );
        return sqlSession.selectOne( "User.selectUserListById", usrIdList );
    }

    public UserExtInfo  getUserExtById( int usrId ) {
        List<Integer> usrIdList = new ArrayList<>();
        usrIdList.add( new Integer( usrId ) );
        return sqlSession.selectOne( "User.selectUserListById", usrIdList );
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

    public UserInfo getUsrIdByUserId( String userId ) {
        Map params = new HashMap();
        params.put("userId", userId);
        return sqlSession.selectOne( "User.selectUsrIdByUserId", params );
    }

    public int updateUserEmail( String newEmail, int usrId, String userId ) {
        Map params = new HashMap();
        params.put("newEmail",  newEmail);
        params.put("usrId",     Integer.valueOf( usrId ));
        params.put("userId",    userId);
        return sqlSession.update( "User.updateUserEmail", params );
    }

    public int updateUserPw( String oldUserPw, String newUserPw, int usrId, String userId ) {
        Map params = new HashMap();
        params.put("oldUserPw",  oldUserPw);
        params.put("newUserPw",  newUserPw);
        params.put("usrId",     Integer.valueOf( usrId ));
        params.put("userId",    userId);
        return sqlSession.update( "User.updateUserPw", params );
    }

}
