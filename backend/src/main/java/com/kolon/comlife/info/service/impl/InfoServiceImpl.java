package com.kolon.comlife.info.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.info.model.InfoData;
import com.kolon.comlife.info.model.InfoMain;
import com.kolon.comlife.info.model.InfoUserProfile;
import com.kolon.comlife.info.service.InfoService;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.impl.PostDAO;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.exception.NoDataException;
import com.kolon.comlife.postFile.service.impl.PostFileDAO;
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

    @Override
    public InfoMain getInfoMain( AuthUserInfo authUserInfo ) throws NoDataException {
        InfoMain       infoMain = new InfoMain();
        ComplexInfo    cmplxInfo;
        int            cmplxId;
        UserExtInfo    userInfo;
        List<InfoData> infoDataList;
        PostInfo       postInfo;

        cmplxId = authUserInfo.getCmplxId();
        userInfo = userDAO.getUserExtById( authUserInfo.getUsrId() );
        if( userInfo == null ) {
            logger.error( "해당 사용자를 찾을 수 없습니다. usrId: " +  authUserInfo.getUsrId() );
            throw new NoDataException("사용자 정보를 찾을 수 없습니다." );
        }

        cmplxInfo = complexDAO.selectComplexById( cmplxId );
        infoDataList = infoDAO.getInfoDataListByCmplxId( cmplxId );
        if( infoDataList == null ) {
            // 빈배열 전달
            infoDataList = new ArrayList<>();
        }

        // Info Main 기본 정보
        infoMain.setUserImgSrc( userInfo.getImageSrc() );
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
    public PostInfo getInfoNoticeByComplexId( int cmplxId ) throws NoDataException {

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
                throw new NoDataException("해당 게시물 정보를 가져올 수 없습니다.");
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

    private InfoUserProfile getInfoProfileInternal( AuthUserInfo authUserInfo ) throws NoDataException {
        ComplexInfo     cmplxInfo;
        UserExtInfo     userInfo;
        InfoUserProfile userProfile = new InfoUserProfile();

        userInfo = userDAO.getUserExtById( authUserInfo.getUsrId() );
        if( userInfo == null ) {
            logger.error( "해당 사용자 정보를 찾을 수 없습니다. usrId: " +  authUserInfo.getUsrId() );
            throw new NoDataException("사용자 정보를 찾을 수 없습니다." );
        }

        cmplxInfo = complexDAO.selectComplexById( userInfo.getCmplxId() );
        if( userInfo == null ) {
            logger.error( "해당 사용자의 현장 정보를 찾을 수 없습니다. cmplxId: " +  authUserInfo.getCmplxId() );
            throw new NoDataException("사용자 정보를 찾을 수 없습니다." );
        }

        userProfile.setUserImgSrc( userInfo.getImageSrc() );
        userProfile.setUserId( userInfo.getUserId() );
        userProfile.setUserNm( userInfo.getUserNm() );
        userProfile.setEmail( userInfo.getEmail() );
        userProfile.setCmplxNm( cmplxInfo.getClCmplxNm() );
        userProfile.setCmplxAddr( cmplxInfo.getClCmplxAddr() );

        return userProfile;
    }

    @Override
    public InfoUserProfile getInfoProfile(AuthUserInfo authUserInfo ) throws NoDataException {
        return this.getInfoProfileInternal( authUserInfo );
    }

    @Override
    public InfoUserProfile updateInfoProfileEmail( AuthUserInfo authUserInfo, String newEmail )
            throws NoDataException
    {
        int updatedCnt ;
        updatedCnt = userDAO.updateUserEmail( newEmail, authUserInfo.getUsrId(), authUserInfo.getUserId() );
        if(updatedCnt < 1) {
            throw new NoDataException("이메일 주소의 업데이트를 실패하였습니다. 입력값을 다시 한 번 확인하세요.");
        }

        return this.getInfoProfileInternal( authUserInfo );
    }

    @Override
    public InfoUserProfile updateInfoProfileUserPw( AuthUserInfo authUserInfo, String oldUserPw, String newUserPw )
            throws NoDataException
    {
        // todo: password의 암호화 처리 부분 체크 할 것
        int updatedCnt ;
        updatedCnt = userDAO.updateUserPw( oldUserPw, newUserPw, authUserInfo.getUsrId(), authUserInfo.getUserId() );
        if(updatedCnt < 1) {
            throw new NoDataException("사용자 암호의 업데이트를 실패하였습니다. 입력값을 다시 한 번 확인하세요.");
        }

        return this.getInfoProfileInternal( authUserInfo );
    }

}
