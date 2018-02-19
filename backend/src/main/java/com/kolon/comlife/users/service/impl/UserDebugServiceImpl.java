package com.kolon.comlife.users.service.impl;

import com.kolon.comlife.users.service.UserDebugService;
import com.kolonbenit.benitware.common.util.FormattingUtil;
import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userDebugService")
public class UserDebugServiceImpl implements UserDebugService {

    @Autowired
    UserDebugDAO userDebugDAO;

    @Override
    public Map getHeadCert(RequestParameter parameter) {

        // Hypens must be in cellphone number.
        if( parameter.get("headCell") != null ) {
            parameter.put(
                    "headCell",
                    FormattingUtil.cellPhoneNumHyphen((String) parameter.get("headCell")));
        }

        return userDebugDAO.getHeadCert( parameter );
    }

    @Override
    public Map getUserCert(RequestParameter parameter) {

        // Hypens must be in cellphone number.
        if( parameter.get("userCell") != null ) {
            parameter.put(
                    "userCell",
                    FormattingUtil.cellPhoneNumHyphen( (String)parameter.get("userCell") ));
        }

        return userDebugDAO.getUserCert( parameter );
    }
}
