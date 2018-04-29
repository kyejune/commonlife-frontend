package com.kolon.comlife.users.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.imageStore.service.ImageStoreService;
import com.kolon.comlife.info.exception.DataNotFoundException;
import com.kolon.comlife.users.exception.NotFoundException;
import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.model.UserExtInfo;
import com.kolon.comlife.users.model.UserInfo;
import com.kolon.comlife.users.model.UserProfileInfo;
import com.kolon.comlife.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserSerivceImpl implements UserService {
    final static Logger logger = LoggerFactory.getLogger(  UserSerivceImpl.class );

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ComplexDAO complexDAO;

    @Autowired
    private ImageStoreService imageStoreService;

    @Override
    public List<PostUserInfo> getUserListForPostById(List<Integer> ids) {
        return userDAO.getUserListForPostById( ids );
    }

    @Override
    public List<UserInfo> getUserListById( List<Integer> ids ) {
        return userDAO.getUserListById( ids );
    }

    @Override
    public UserInfo getUsrIdByUserIdAndPwd( String userId, String userPw ) {
        return userDAO.getUsrIdByUserIdAndPwd( userId, userPw );
    }

    @Override
    public boolean isExistedUser( String userId ) {
        return (userDAO.getUsrIdByUserId( userId ) != null);
    }

    @Override
    public UserProfileInfo getUserProfile( int usrId ) throws NotFoundException {
        return getUserProfileInternal( usrId );
    }

    private UserProfileInfo getUserProfileInternal( int usrId ) throws NotFoundException {
        ComplexInfo cmplxInfo;
        UserExtInfo userInfo;
        UserProfileInfo userProfile = new UserProfileInfo();
        String          imgFullPath;

        userInfo = userDAO.getUserExtById( usrId );
        if( userInfo == null ) {
            logger.error( "해당 사용자 정보를 찾을 수 없습니다 usrId: " +  usrId );
            throw new NotFoundException("사용자 정보를 찾을 수 없습니다" );
        }

        cmplxInfo = complexDAO.selectComplexById( userInfo.getCmplxId() );
        if( cmplxInfo == null ) {
            logger.error( "해당 사용자의 현장 정보를 찾을 수 없습니다 cmplxId: " +  userInfo.getCmplxId() );
            throw new NotFoundException("사용자 정보를 찾을 수 없습니다" );
        }

        imgFullPath = imageStoreService.getImageFullPathByIdx(
                userInfo.getImageIdx(),
                ImageInfoUtil.SIZE_SUFFIX_MEDIUM );
        userProfile.setUserImgSrc( imgFullPath );
        userProfile.setUserId( userInfo.getUserId() );
        userProfile.setUserNm( userInfo.getUserNm() );
        userProfile.setEmail( userInfo.getEmail() );
        userProfile.setCmplxNm( cmplxInfo.getClCmplxNm() );
        userProfile.setCmplxAddr( cmplxInfo.getClCmplxAddr() );

        return userProfile;
    }

}
