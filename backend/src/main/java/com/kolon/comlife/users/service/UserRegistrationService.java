package com.kolon.comlife.users.service;

import com.kolon.comlife.users.exception.NotAcceptedUserIdException;
import com.kolon.comlife.users.exception.UserNotExistException;
import com.kolon.comlife.users.exception.UsersGeneralException;
import com.kolon.comlife.users.model.AgreementInfo;
import com.kolon.comlife.users.model.UserInfo;

import java.util.List;

public interface UserRegistrationService {

    List<AgreementInfo> getLatestAgreement();

    boolean isAcceptedUserId(String userId) throws NotAcceptedUserIdException;

    UserInfo setUserExt(String userId, String userPw ) throws UserNotExistException, UsersGeneralException;
}
