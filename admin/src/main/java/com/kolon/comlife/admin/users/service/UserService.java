package com.kolon.comlife.admin.users.service;

import com.kolon.comlife.admin.users.exception.UserGeneralException;

public interface UserService {

    int getTotalUserCountByComplexId( int cmplxId ) throws UserGeneralException;

}
