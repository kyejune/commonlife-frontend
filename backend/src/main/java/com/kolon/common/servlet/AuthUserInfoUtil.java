package com.kolon.common.servlet;

import com.kolon.common.model.AuthUserInfo;

import javax.servlet.http.HttpServletRequest;

public class AuthUserInfoUtil {

    public final static String ATTR_KEY = "AUTH_USER_INFO";

    public final static void setAuthUserInfo(HttpServletRequest target, AuthUserInfo userInfo) {

        target.getSession().setAttribute(ATTR_KEY, userInfo);
    }

    public final static AuthUserInfo getAuthUserInfo(HttpServletRequest target) {

        return (AuthUserInfo)target.getSession().getAttribute(ATTR_KEY);
    }

    public final static boolean isAuthUserInfoExisted(HttpServletRequest target) {

        return target.getSession().getAttribute(ATTR_KEY) == null ? false : true ;
    }
}
