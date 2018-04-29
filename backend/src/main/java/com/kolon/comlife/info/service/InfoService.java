package com.kolon.comlife.info.service;

import com.kolon.comlife.info.exception.DataNotFoundException;
import com.kolon.comlife.info.exception.OperationFailedException;
import com.kolon.comlife.info.model.InfoItem;
import com.kolon.comlife.info.model.InfoMain;
import com.kolon.comlife.users.model.UserProfileInfo;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.common.model.AuthUserInfo;

import java.util.List;

public interface InfoService {

    InfoMain getInfoMain(AuthUserInfo authUserInfo ) throws DataNotFoundException;

    PostInfo getInfoNoticeByComplexId( int cmplxId ) throws DataNotFoundException;

    List<InfoItem> getInfoGuideItemList(int cmplxId ) throws DataNotFoundException;

    InfoItem getInfoGuideItem( int cmplxId, int itemIdx ) throws DataNotFoundException, OperationFailedException;

    List<InfoItem> getInfoBenefitsItemList( int cmplxId ) throws DataNotFoundException;

    InfoItem getInfoBenefitsItem( int cmplxId, int itemIdx ) throws DataNotFoundException, OperationFailedException ;

    UserProfileInfo getInfoProfile(AuthUserInfo authUserInfo ) throws DataNotFoundException;

    UserProfileInfo updateInfoProfileEmail(AuthUserInfo authUserInfo, String newEmail ) throws DataNotFoundException;

    UserProfileInfo updateInfoProfileUserPw(AuthUserInfo authUserInfo, String oldUserPw, String newUserPw )
            throws DataNotFoundException;
}
