package com.kolon.comlife.users.service.impl;

import com.kolon.comlife.users.exception.NotAcceptedUserIdException;
import com.kolon.comlife.users.model.AgreementInfo;

import com.kolon.comlife.users.service.UserRegistrationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("registrationService")
public class UserRegistrationSerivceImpl implements UserRegistrationService {

    final static String NOT_ALLOWED_USERID_REGEX = "([A|a][D|d][M|m][I|i][N|n])|([M|m][A|a][N|n][A|a][G|g][E|e][R|r])";

    @Resource(name = "registrationDAO")
    private RegistrationDAO registrationDAO;

    @Override
    public List<AgreementInfo> getLatestAgreement() {
        List<AgreementInfo> agreements;
        agreements = registrationDAO.getLatestAgreement();
        return agreements;
    }

    @Override
    public boolean isAcceptedUserId(String userId)  throws NotAcceptedUserIdException {
        // 입력한 USER ID가 사용 가능한 값인지 체크함
        // * manager 또는 admin이 이름 사이에 포함되어있으면 ID로 사용이 불가함

        if( true == Pattern.matches( NOT_ALLOWED_USERID_REGEX, userId ) ){
            // 사용할 수 없음
            throw new NotAcceptedUserIdException("admin/manager 등의 단어가 포함된 아이디는 사용할 수 없습니다.");
        }

        return true;
    }
}
