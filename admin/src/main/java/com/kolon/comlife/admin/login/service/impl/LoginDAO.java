package com.kolon.comlife.admin.login.service.impl;

import com.kolon.comlife.admin.login.model.LoginInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * LOGIN Data Access Object
 * @author nacsde
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  ----------   --------    ---------------------------
 *   2017-07-31     조신득          최초 생성
 * </pre>
 */
@Repository("loginDAO")
public class LoginDAO {
    @Resource
    private SqlSession sqlSession;


    /**
     * USER 조회
     * @param loginInfo
     * @return
     */
    public LoginInfo selectUser(LoginInfo loginInfo) {
        return sqlSession.selectOne("Login.selectUser", loginInfo);
    }


    /**
     * DEPT USER 조회
     * @param loginInfo
     * @return
     */
    public LoginInfo selectDeptUser(LoginInfo loginInfo) {
        return sqlSession.selectOne("Login.selectDeptUser", loginInfo);
    }
}
