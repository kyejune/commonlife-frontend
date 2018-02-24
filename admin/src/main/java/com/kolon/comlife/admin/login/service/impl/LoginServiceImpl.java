package com.kolon.comlife.admin.login.service.impl;

import com.kolon.comlife.admin.login.model.LoginInfo;
import com.kolon.comlife.admin.login.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 로그인 서비스 구현체
 * @author nacsde
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  ----------   --------    ---------------------------
 *   2017-07-31   조신득          최초 생성
 * </pre>
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {
    public static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Resource(name = "loginDAO")
    private LoginDAO loginDAO;


    public LoginInfo selectUser(LoginInfo loginInfo) {
        return loginDAO.selectUser(loginInfo);
    }


    public LoginInfo selectDeptUser(LoginInfo loginInfo) {
        return loginDAO.selectDeptUser(loginInfo);
    }

}
