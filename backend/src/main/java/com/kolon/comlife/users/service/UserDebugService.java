package com.kolon.comlife.users.service;

import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;

import java.util.Map;

public interface UserDebugService {

    Map getEncodeValue(String value);

    Map getHeadCert(RequestParameter parameter);

    Map getUserCert(RequestParameter parameter);

}
