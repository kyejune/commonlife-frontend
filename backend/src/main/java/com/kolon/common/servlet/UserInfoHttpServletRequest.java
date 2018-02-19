package com.kolon.common.servlet;

import com.kolon.common.model.AuthUserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class UserInfoHttpServletRequest extends HttpServletRequestWrapper {

    private AuthUserInfo authUserInfo;

    public UserInfoHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    public AuthUserInfo getAuthUserInfo() {
        return authUserInfo;
    }

    public void setAuthUserInfo(AuthUserInfo authUserInfo) {
        this.authUserInfo = authUserInfo;
    }
}
