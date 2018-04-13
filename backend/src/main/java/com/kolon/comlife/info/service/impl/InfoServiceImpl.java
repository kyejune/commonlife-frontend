package com.kolon.comlife.info.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.info.model.InfoData;
import com.kolon.comlife.info.model.InfoMain;
import com.kolon.comlife.info.service.InfoService;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.impl.PostDAO;
import com.kolon.comlife.postFile.service.exception.NoDataException;
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

    private String NO_NOTICE = "공지 사항이 없습니다.";

    @Autowired
    UserDAO userDAO;

    @Autowired
    ComplexDAO complexDAO;

    @Autowired
    InfoDAO infoDAO;

    @Autowired
    PostDAO postDAO;

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

}
