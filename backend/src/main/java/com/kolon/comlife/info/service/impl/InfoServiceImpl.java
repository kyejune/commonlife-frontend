package com.kolon.comlife.info.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.info.model.InfoMain;
import com.kolon.comlife.info.service.InfoService;
import com.kolon.comlife.postFile.service.exception.NoDataException;
import com.kolon.comlife.users.model.UserExtInfo;
import com.kolon.comlife.users.service.impl.UserDAO;
import com.kolon.common.model.AuthUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("infoService")
public class InfoServiceImpl implements InfoService {
    private static final Logger logger = LoggerFactory.getLogger( InfoServiceImpl.class );

    @Autowired
    UserDAO userDAO;

    @Autowired
    ComplexDAO complexDAO;

    public InfoMain getInfoMain( AuthUserInfo authUserInfo ) throws NoDataException {
        InfoMain    infoMain = new InfoMain();
        ComplexInfo cmplxInfo;
        int         cmplxId;
        UserExtInfo userInfo;

        cmplxId = authUserInfo.getCmplxId();
        userInfo = userDAO.getUserExtById( authUserInfo.getUsrId() );
        if( userInfo == null ) {
            logger.error( "해당 사용자를 찾을 수 없습니다. usrId: " +  authUserInfo.getUsrId() );
            throw new NoDataException("사용자 정보를 찾을 수 없습니다." );
        }

        cmplxInfo = complexDAO.selectComplexById( cmplxId );

        infoMain.setUserImgSrc( userInfo.getImageSrc() );
        infoMain.setUserNm( userInfo.getUserNm() );
        infoMain.setCmplxNm( cmplxInfo.getClCmplxNm() );
        infoMain.setDong( userInfo.getDong() );
        infoMain.setHo( userInfo.getHo() );
        infoMain.setStartDt( userInfo.getStartDt() );
        infoMain.setPoint( userInfo.getPoints() );

        return infoMain;
    }

}
