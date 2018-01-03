package com.kolon.common.security;

import com.kolon.common.model.BaseUserInfo;
//import com.kolon.secuinfo.login.model.LoginInfo;
//import com.kolon.secuinfo.login.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    /*
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Resource(name = "loginService")
    private LoginService loginService;

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String user_id = (String)authentication.getPrincipal();


        // ============================================================================================================
        logger.info("=====> 사용자가 입력한 로그인정보입니다. {}", user_id + "/");

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setIkenId(user_id);

        loginInfo = loginService.selectUser(loginInfo);
        // ============================================================================================================

        logger.info("=====> 사용자 authCheck : {}", loginInfo.getIkenId());

        if(user_id.equals(loginInfo.getIkenId())){
            logger.info("=====> 정상 로그인입니다.");
            List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user_id, user_id, roles);
            result.setDetails(new BaseUserInfo(loginInfo.getEmpNo(), loginInfo.getEmpNm(), loginInfo.getPostNm(), loginInfo.getIkenId(),loginInfo.getDeptCd(), loginInfo.getDeptNm(),
                                               loginInfo.getDeptParCd(), loginInfo.getDeptParNm(), loginInfo.getIsManage(), loginInfo.getAuthLvl()));
            return result;
        }else{
            logger.info("=====> 사용자 크리덴셜 정보가 틀립니다. 에러가 발생합니다.");
            throw new BadCredentialsException("Bad credentials");
        }
    }
    */

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        return null;
    }
}
