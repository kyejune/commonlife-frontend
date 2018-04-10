package com.kolon.comlife.users.service.impl;

import com.kolon.comlife.users.exception.NotAcceptedUserIdException;
import com.kolon.comlife.users.exception.UserNotExistException;
import com.kolon.comlife.users.exception.UsersGeneralException;
import com.kolon.comlife.users.model.AgreementInfo;

import com.kolon.comlife.users.model.UserInfo;
import com.kolon.comlife.users.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("registrationService")
public class UserRegistrationSerivceImpl implements UserRegistrationService {

    final static String NOT_ALLOWED_USERID_REGEX = "([A|a][D|d][M|m][I|i][N|n])|([M|m][A|a][N|n][A|a][G|g][E|e][R|r])";

    @Autowired
    private RegistrationDAO registrationDAO;

    @Autowired
    private UserDAO userDAO;

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

    @Override
    /**
     * 해당 USR_ID/USR_PWD에 대해서 USR_INFO_EXT에 연결 레코드를 생성합니다.
     */
    public UserInfo setUserExt( String userId, String userPw ) throws UserNotExistException, UsersGeneralException {
        UserInfo userInfo;
        int retCnt;

        userInfo = userDAO.getUsrIdByUserIdAndPwd( userId, userPw );
        if( userInfo == null ) {
            throw new UserNotExistException( userId + "사용자가 존재하지 않습니다." );
        }

        retCnt = userDAO.setUserExt( userInfo.getUsrId() );
        if( retCnt < 1 ) {
            throw new UsersGeneralException("사용자 정보를 가져올 수 없습니다.");
        }

        return userInfo;
    }
}
