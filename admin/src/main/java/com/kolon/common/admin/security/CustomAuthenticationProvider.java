package com.kolon.common.admin.security;

import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.manager.model.ManagerInfo;
import com.kolon.comlife.admin.manager.service.ManagerService;
import com.kolon.common.admin.model.BaseUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private ManagerService managerService;

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {

        AdminInfo  managerInfo;
        String user_id = (String)authentication.getPrincipal();
        String user_pw = (String)authentication.getCredentials();
        UsernamePasswordAuthenticationToken result;

        AdminInfo loginInfo = new AdminInfo();
        loginInfo.setAdminId( user_id );
        loginInfo.setAdminPw( user_pw );
        logger.debug(">>>>>>> user_id: " + user_id );

        try {
            managerInfo = managerService.selectLoginManager(loginInfo);
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
        // ============================================================================================================

        if(managerInfo == null) {
            logger.info("=====> 사용자 크리덴셜 정보가 틀립니다. 에러가 발생합니다.");
            throw new BadCredentialsException("Bad credentials");
        }

        logger.info("=====> 정상 로그인입니다.");
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add( new SimpleGrantedAuthority("ROLE_USER") );

        result = new UsernamePasswordAuthenticationToken( user_id, user_id, roles );
        result.setDetails( managerInfo );

        return result;
    }
}
