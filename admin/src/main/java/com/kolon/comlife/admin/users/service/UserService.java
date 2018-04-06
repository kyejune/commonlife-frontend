package com.kolon.comlife.admin.users.service;

import com.kolon.comlife.admin.users.exception.UserGeneralException;
import com.kolon.comlife.admin.users.model.UserExtInfo;

public interface UserService {

    int getTotalUserCountByComplexId( int cmplxId ) throws UserGeneralException;

    UserExtInfo getUserExtInfoById(int usrId );

}
