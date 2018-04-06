package com.kolon.comlife.admin.login.web;

import com.kolon.comlife.admin.manager.model.AdminInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller("loginController")
@RequestMapping("/")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String USER_LOGIN_INFO = "userLoginInfo";

    @RequestMapping(
            value = {"","/", "/loginPage.*","login.*"},
            method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("time", System.currentTimeMillis());
        logger.debug("Controller inside! ");
        return "admin/loginPage";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public void logout(HttpSession session) {
        AdminInfo userLoginInfo = (AdminInfo) session.getAttribute( USER_LOGIN_INFO );

        logger.info( "Logged out! {}, {}@{}",
                     session.getId(),
                     userLoginInfo.getAdminId(),
                     userLoginInfo.getGrpNm() );

        session.invalidate();
    }

    @RequestMapping(value = "login_success", method = RequestMethod.GET)
    public void login_success(HttpSession session) {
        AdminInfo userLoginInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        logger.info( "Login success! {}, {}@{}",
                     session.getId(),
                     userLoginInfo.getAdminId(),
                     userLoginInfo.getGrpNm() );
        session.setAttribute("userLoginInfo", userLoginInfo);

        // RANDOM TOKEN 추가
        session.setAttribute("CSRF_TOKEN", UUID.randomUUID().toString());
    }

}
