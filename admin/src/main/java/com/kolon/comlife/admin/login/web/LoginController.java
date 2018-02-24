package com.kolon.comlife.admin.login.web;

import com.kolon.comlife.admin.login.service.LoginService;
import com.kolon.common.admin.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller("loginController")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource(name = "loginService")
    private LoginService loginService;


    @RequestMapping(value = {"","/", "/loginPage.*","login.*"}, method = RequestMethod.GET)
    public String loginPage(HttpSession session) {
        return "manage/loginPage";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public void logout(HttpSession session) {
        CustomUserDetails userDetails = (CustomUserDetails)session.getAttribute("userLoginInfo");

        logger.info("Welcome logout! {}, {}", session.getId(), userDetails.getUsername());

        session.invalidate();
    }

    @RequestMapping(value = "login_success", method = RequestMethod.GET)
    public void login_success(HttpSession session) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        logger.info("Welcome login_success! {}, {}", session.getId(), userDetails.getUsername() + "/" + userDetails.getPassword());
        session.setAttribute("userLoginInfo", userDetails);

        // RANDOM TOKEN 추가
        session.setAttribute("CSRF_TOKEN", UUID.randomUUID().toString());
    }

}
