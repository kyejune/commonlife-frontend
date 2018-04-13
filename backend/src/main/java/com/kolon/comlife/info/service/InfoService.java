package com.kolon.comlife.info.service;

import com.kolon.comlife.info.model.InfoMain;
import com.kolon.comlife.info.model.InfoUserProfile;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.postFile.service.exception.NoDataException;
import com.kolon.comlife.users.model.UserExtInfo;
import com.kolon.common.model.AuthUserInfo;

public interface InfoService {

    InfoMain getInfoMain(AuthUserInfo authUserInfo ) throws NoDataException;

    PostInfo getInfoNoticeByComplexId( int cmplxId ) throws NoDataException;

    InfoUserProfile getInfoProfile(AuthUserInfo authUserInfo ) throws NoDataException;

    InfoUserProfile updateInfoProfileEmail( AuthUserInfo authUserInfo, String newEmail ) throws NoDataException;

    InfoUserProfile updateInfoProfileUserPw( AuthUserInfo authUserInfo, String oldUserPw, String newUserPw )
            throws NoDataException;
}
