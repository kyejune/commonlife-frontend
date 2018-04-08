package com.kolon.comlife.users.service;

import com.kolon.comlife.users.exception.NotAcceptedUserIdException;
import com.kolon.comlife.users.model.AgreementInfo;

import java.util.List;

public interface UserRegistrationService {

    List<AgreementInfo> getLatestAgreement();

    boolean isAcceptedUserId(String userId) throws NotAcceptedUserIdException;
}
