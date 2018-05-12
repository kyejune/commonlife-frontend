package com.kolon.comlife.info.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.imageStore.exception.ImageNotFoundException;
import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.imageStore.model.ImageInfoExt;
import com.kolon.comlife.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.imageStore.service.ImageStoreService;
import com.kolon.comlife.info.exception.DataNotFoundException;
import com.kolon.comlife.info.exception.OperationFailedException;
import com.kolon.comlife.info.model.InfoData;
import com.kolon.comlife.info.model.InfoItem;
import com.kolon.comlife.info.model.InfoMain;
import com.kolon.comlife.users.model.UserProfileInfo;
import com.kolon.comlife.info.service.InfoService;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.impl.PostDAO;
import com.kolon.comlife.post.model.PostFileInfo;
import com.kolon.comlife.post.service.impl.PostFileDAO;
import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.model.UserExtInfo;
import com.kolon.comlife.users.service.impl.UserDAO;
import com.kolon.common.model.AuthUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.kolon.comlife.imageStore.model.ImageInfoUtil.*;


@Service("infoService")
public class InfoServiceImpl implements InfoService {
    private static final Logger logger = LoggerFactory.getLogger( InfoServiceImpl.class );

    private String NO_NOTICE = null; // 공지사항 없는 경우, null 값 전송

    @Autowired
    UserDAO userDAO;

    @Autowired
    ComplexDAO complexDAO;

    @Autowired
    InfoDAO infoDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    PostFileDAO postFileDAO;

    @Autowired
    ImageStoreService imageStoreService;

    @Override
    public InfoMain getInfoMain( AuthUserInfo authUserInfo ) throws DataNotFoundException {
        InfoMain       infoMain = new InfoMain();
        ComplexInfo    cmplxInfo;
        int            cmplxId;
        UserExtInfo    userInfo;
        List<InfoData> infoDataList;
        PostInfo       postInfo;
        String         imgFullPath;

        cmplxId = authUserInfo.getCmplxId();
        userInfo = userDAO.getUserExtById( authUserInfo.getUsrId() );
        if( userInfo == null ) {
            logger.error( "해당 사용자를 찾을 수 없습니다 usrId: " +  authUserInfo.getUsrId() );
            throw new DataNotFoundException("사용자 정보를 찾을 수 없습니다" );
        }

        cmplxInfo = complexDAO.selectComplexById( cmplxId );
        infoDataList = infoDAO.getInfoDataListByCmplxId( cmplxId );
        if( infoDataList == null ) {
            // 빈배열 전달
            infoDataList = new ArrayList<>();
        }

        imgFullPath = imageStoreService.getImageFullPathByIdx(
                                userInfo.getImageIdx(),
                                ImageInfoUtil.SIZE_SUFFIX_MEDIUM );

        // Info Main 기본 정보
        infoMain.setUserImgSrc( imgFullPath );
        infoMain.setUserNm( userInfo.getUserNm() );
        infoMain.setCmplxNm( cmplxInfo.getClCmplxNm() );
        infoMain.setDong( userInfo.getDong() );
        infoMain.setHo( userInfo.getHo() );
        infoMain.setStartDt( userInfo.getStartDt() );
        infoMain.setPoint( userInfo.getPoints() );

        // Notice
        logger.debug(">>>>>>>> cmplxInfo.getCmplxId():" + cmplxInfo.getCmplxId());
        logger.debug(">>>>>>>> cmplxInfo.getNoticeYn():" + cmplxInfo.getNoticeYn());
        if( "Y".equals( cmplxInfo.getNoticeYn() ) ) {
            postInfo = postDAO.selectPostContentOnly( cmplxInfo.getNoticeIdx() );
            if( postInfo != null ) {
                infoMain.setNotice( postInfo.getContent() );
            } else {
                infoMain.setNotice( NO_NOTICE );
            }
        } else {
            infoMain.setNotice( NO_NOTICE );
        }

        // Info 버튼 목록 표시
        infoMain.setInfoList( infoDataList );

        return infoMain;
    }


    @Override
    public PostInfo getInfoNoticeByComplexId( int cmplxId ) throws DataNotFoundException {

        int                noticeIdx;
        ComplexInfo        cmplxInfo;
        PostInfo           postInfo;
        PostFileInfo       postFile;
        List<PostFileInfo> postFileList;
        List<Integer>      userIds;
        List<PostUserInfo> userList;

        cmplxInfo = complexDAO.selectComplexById( cmplxId );

        if( "Y".equals( cmplxInfo.getNoticeYn() )) {
            userIds = new ArrayList<>();

            // usrId는 상관 없음
            noticeIdx = cmplxInfo.getNoticeIdx();

            postInfo = postDAO.selectPost( noticeIdx, -1 );

            userIds.add(postInfo.getUsrId());
            userList = userDAO.getUserListForPostById( userIds );
            if( userList == null || userList.size() < 1 ) {
                throw new DataNotFoundException("해당 게시물 정보를 가져올 수 없습니다");
            }
            postInfo.setUser( userList.get(0) );

            postFileList = postFileDAO.getPostFilesByPostId( noticeIdx );
            if( postFileList != null && postFileList.size() > 0 ) {
                postFile = postFileList.get(0);

                // PostInfo에 이미지 파일 연결
                postInfo.getPostFiles().add( postFile );
            }
        } else {
            postInfo = new PostInfo();
            postInfo.setContent( NO_NOTICE );
        }

        return postInfo;
    }

    @Override
    public List<InfoItem> getInfoGuideItemList( int cmplxId ) throws DataNotFoundException {

        List<InfoItem> infoItemList;

        infoItemList = infoDAO.getInfoItemListByCmplxIdAndCategory(cmplxId, "GUIDE");
        if (infoItemList == null) {
            throw new DataNotFoundException("가져올 목록이 없습니다");
        }

        return infoItemList;
    }

    @Override
    public InfoItem getInfoGuideItem( int cmplxId, int itemIdx ) throws DataNotFoundException, OperationFailedException {
        InfoItem           infoItem;
        ImageInfo          imageInfo;
        ImageInfoExt       imageInfoExt;
        List<ImageInfoExt> imageInfoList = new ArrayList<>();

        infoItem = infoDAO.getInfoItemByCmplxIdAndCategory(cmplxId, "GUIDE", itemIdx);
        if (infoItem == null) {
            throw new DataNotFoundException("가져올 내용이 없습니다");
        }

        /* NOTE:
         infoItem.getImageIdx()은 목록 앞에 붙는 아이콘 아미지, 따라서 Guide에는 목록에 표시할 이미지가 없음
         본문 내에 포함되는 이미지는 infoItem.getImageIdx2()를 참조함
        */

        try {
            if (infoItem.getImageInfo() == null) {
                if (infoItem.getImageIdx2() > -1) {
                    imageInfoExt = new ImageInfoExt( );

                    imageInfo = imageStoreService.getImageInfoByIdx( infoItem.getImageIdx2() );

                    imageInfoExt.setMimeType( imageInfo.getMimeType() );
                    imageInfoExt.setOriginPath( imageStoreService.getImageFullPathByIdx( infoItem.getImageIdx2() ));
                    imageInfoExt.setLargePath( imageStoreService.getImageFullPathByIdx( infoItem.getImageIdx2(), SIZE_SUFFIX_LARGE ));
                    imageInfoExt.setMediumPath( imageStoreService.getImageFullPathByIdx( infoItem.getImageIdx2(), SIZE_SUFFIX_MEDIUM ));
                    imageInfoExt.setSmallPath( imageStoreService.getImageFullPathByIdx( infoItem.getImageIdx2(), SIZE_SUFFIX_SMALL ));
                    imageInfoList.add( imageInfoExt );
                }
            }
        } catch( ImageNotFoundException e ) {
            throw new OperationFailedException("이미지를 가져올 수 없습니다");
        }


        infoItem.setImageInfo( imageInfoList );

        return infoItem;
    }

    @Override
    public List<InfoItem> getInfoBenefitsItemList( int cmplxId ) throws DataNotFoundException {

        List<InfoItem> infoItemList;

        infoItemList = infoDAO.getInfoItemListByCmplxIdAndCategory(cmplxId, "BENEFITS");
        if (infoItemList == null) {
            throw new DataNotFoundException("가져올 목록이 없습니다");
        }

        for( InfoItem i : infoItemList ) {
            if( (i.getImageIdx() > -1) &&
                (i.getImgSrc() == null) )
            {
                i.setImgSrc(
                        imageStoreService.getImageFullPathByIdx(
                                i.getImageIdx(),
                                ImageInfoUtil.SIZE_SUFFIX_SMALL ) );
            }
        }

        return infoItemList;
    }

    @Override
    public InfoItem getInfoBenefitsItem( int cmplxId, int itemIdx ) throws DataNotFoundException, OperationFailedException  {
        InfoItem           infoItem;
        ImageInfo          imageInfo;
        ImageInfoExt       imageInfoExt;
        List<ImageInfoExt> imageInfoList = new ArrayList<>();

        infoItem = infoDAO.getInfoItemByCmplxIdAndCategory(cmplxId, "BENEFITS", itemIdx);
        if (infoItem == null) {
            throw new DataNotFoundException("가져올 내용이 없습니다");
        }

        try {
            // Logo 이미지 설정
            logger.debug("getItemIdx>>>> " + infoItem.getItemIdx() );
            logger.debug("getImageId>>>> " + infoItem.getImageIdx() );
            logger.debug("getImageIdx2>>>> " + infoItem.getImageIdx2() );
            if( (infoItem.getImageIdx() > -1) &&
                    (infoItem.getImgSrc() == null) )
            {
                infoItem.setImgSrc(
                        imageStoreService.getImageFullPathByIdx(
                                infoItem.getImageIdx(),
                                ImageInfoUtil.SIZE_SUFFIX_SMALL ) );
            }

            // 본문 이미지 설정
            if (infoItem.getImageInfo() == null) {
                if (infoItem.getImageIdx2() > -1) {
                    imageInfoExt = new ImageInfoExt( );

                    imageInfo = imageStoreService.getImageInfoByIdx( infoItem.getImageIdx2() );

                    imageInfoExt.setMimeType( imageInfo.getMimeType() );
                    imageInfoExt.setOriginPath( imageStoreService.getImageFullPathByIdx( infoItem.getImageIdx2() ));
                    imageInfoExt.setLargePath( imageStoreService.getImageFullPathByIdx( infoItem.getImageIdx2(), SIZE_SUFFIX_LARGE ));
                    imageInfoExt.setMediumPath( imageStoreService.getImageFullPathByIdx( infoItem.getImageIdx2(), SIZE_SUFFIX_MEDIUM ));
                    imageInfoExt.setSmallPath( imageStoreService.getImageFullPathByIdx( infoItem.getImageIdx2(), SIZE_SUFFIX_SMALL ));
                    imageInfoList.add( imageInfoExt );
                }
            }
        } catch( ImageNotFoundException e ) {
            throw new OperationFailedException("이미지를 가져올 수 없습니다");
        }

        infoItem.setImageInfo( imageInfoList );

        return infoItem;
    }

    private UserProfileInfo getInfoProfileInternal(AuthUserInfo authUserInfo ) throws DataNotFoundException {
        ComplexInfo     cmplxInfo;
        UserExtInfo     userInfo;
        UserProfileInfo userProfile = new UserProfileInfo();
        String          imgFullPath;

        userInfo = userDAO.getUserExtById( authUserInfo.getUsrId() );
        if( userInfo == null ) {
            logger.error( "해당 사용자 정보를 찾을 수 없습니다 usrId: " +  authUserInfo.getUsrId() );
            throw new DataNotFoundException("사용자 정보를 찾을 수 없습니다" );
        }

        cmplxInfo = complexDAO.selectComplexById( userInfo.getCmplxId() );
        if( userInfo == null ) {
            logger.error( "해당 사용자의 현장 정보를 찾을 수 없습니다 cmplxId: " +  authUserInfo.getCmplxId() );
            throw new DataNotFoundException("사용자 정보를 찾을 수 없습니다" );
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

    @Override
    public UserProfileInfo getInfoProfile(AuthUserInfo authUserInfo ) throws DataNotFoundException {
        return this.getInfoProfileInternal( authUserInfo );
    }

    @Override
    public UserProfileInfo updateInfoProfileEmail(AuthUserInfo authUserInfo, String newEmail )
            throws DataNotFoundException
    {
        int updatedCnt ;
        updatedCnt = userDAO.updateUserEmail( newEmail, authUserInfo.getUsrId(), authUserInfo.getUserId() );
        if(updatedCnt < 1) {
            throw new DataNotFoundException("이메일 주소의 업데이트를 실패하였습니다 입력값을 다시 한 번 확인하세요.");
        }

        return this.getInfoProfileInternal( authUserInfo );
    }

    @Override
    public UserProfileInfo updateInfoProfileUserPw(AuthUserInfo authUserInfo, String oldUserPw, String newUserPw )
            throws DataNotFoundException
    {
        // todo: password의 암호화 처리 부분 체크 할 것
        int updatedCnt ;
        updatedCnt = userDAO.updateUserPw( oldUserPw, newUserPw, authUserInfo.getUsrId(), authUserInfo.getUserId() );
        if(updatedCnt < 1) {
            throw new DataNotFoundException("사용자 암호의 업데이트를 실패하였습니다 입력값을 다시 한 번 확인하세요.");
        }

        return this.getInfoProfileInternal( authUserInfo );
    }

}
