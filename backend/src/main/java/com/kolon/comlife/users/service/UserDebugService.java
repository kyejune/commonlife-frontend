package com.kolon.comlife.users.service;

import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;

import java.util.List;
import java.util.Map;

public interface UserDebugService {

    public Map getHeadCert(RequestParameter parameter);

    public Map getUserCert(RequestParameter parameter);

}
