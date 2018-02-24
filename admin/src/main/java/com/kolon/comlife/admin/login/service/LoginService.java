package com.kolon.comlife.admin.login.service;

import com.kolon.comlife.admin.login.model.LoginInfo;

/**
 * 로그인 서비스 인터페이스
 * @author nacsde
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  ----------   --------    ---------------------------
 *   2017-07-31    조신득          최초 생성
 * </pre>
 */
public interface LoginService {
    /**
     * USER 확인(로그인용도)
     * @param loginInfo
     * @return boolean
     */
    LoginInfo selectUser(LoginInfo loginInfo);

    /**
     * DEPT USER 조회
     * @param loginInfo
     * @return
     */
    LoginInfo selectDeptUser(LoginInfo loginInfo);
}
