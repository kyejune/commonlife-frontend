package com.kolon.comlife.users.service.impl;

import com.kolon.comlife.users.model.AgreementInfo;

import com.kolon.comlife.users.service.RegistrationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("registrationService")
public class RegistrationSerivceImpl implements RegistrationService {
    @Resource(name = "registrationDAO")
    private RegistrationDAO registrationDAO;

    @Override
    public List<AgreementInfo> getLatestAgreement() {
        List<AgreementInfo> agreements;
        agreements = registrationDAO.getLatestAgreement();
        return agreements;
    }
}
