package com.kolon.comlife.info.web;

import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.imageStore.service.ImageStoreService;
import com.kolon.comlife.info.exception.DataNotFoundException;
import com.kolon.comlife.info.exception.OperationFailedException;
import com.kolon.comlife.info.model.*;
import com.kolon.comlife.info.service.InfoService;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.postFile.service.exception.NoDataException;
import com.kolon.comlife.users.service.UserService;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/info/*")
public class InfoController {
    private static final Logger logger = LoggerFactory.getLogger( InfoController.class );

    @Autowired
    InfoService infoService;

    @Autowired
    UserService userService;

    @Autowired
    ComplexService complexService;


    @Autowired
    ImageStoreService imageStoreService;

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoMain(HttpServletRequest request) {

        AuthUserInfo authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );
        InfoMain     infoMain;

        try {
            infoMain = infoService.getInfoMain( authUserInfo );
        } catch( DataNotFoundException e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( e.getMessage() ) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( infoMain );
    }

    @CrossOrigin
    @GetMapping(
            value = "/notice",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoNotice(HttpServletRequest request) {

        AuthUserInfo authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );
        PostInfo     postInfo;

        try {
            postInfo = infoService.getInfoNoticeByComplexId( authUserInfo.getCmplxId() );
        } catch( DataNotFoundException e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                        .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( e.getMessage() ) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( postInfo );
    }

    @CrossOrigin
    @GetMapping(
            value = "/guide",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoGuide(HttpServletRequest request) {
        AuthUserInfo    authUserInfo;
        DataListInfo    dataListInfo = new DataListInfo();
        List<InfoItem>  infoItemList;

        if( AuthUserInfoUtil.isAuthUserInfoExisted( request ) ){
            authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo("인증정보를 찾을 수 없습니다. 다시 로그인하세요."));
        }

        try {
            infoItemList = infoService.getInfoGuideItemList( authUserInfo.getCmplxId() );
        } catch( DataNotFoundException e ) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body( e.getMessage() );
        }

        dataListInfo.setData( infoItemList );
        dataListInfo.setMsg("Living Guide 목록 가져오기");

        return ResponseEntity.status( HttpStatus.OK ).body( dataListInfo );
    }


    @CrossOrigin
    @GetMapping(
            value = "/guide/{itemIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoGuideDetail( HttpServletRequest request,
                                              @PathVariable("itemIdx") int itemIdx ) {
        AuthUserInfo  authUserInfo;
        InfoItem      infoItem;

        if( AuthUserInfoUtil.isAuthUserInfoExisted( request ) ){
            authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo("인증정보를 찾을 수 없습니다. 다시 로그인하세요."));
        }

        try {
            infoItem = infoService.getInfoGuideItem(authUserInfo.getCmplxId(), itemIdx);
        } catch( DataNotFoundException e ) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body( new SimpleErrorInfo( e.getMessage() ) );
        } catch( OperationFailedException e ) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body( new SimpleErrorInfo( e.getMessage() ) );
        }

        infoItem.setMsg("Guide 상세정보 가져오기");

        return ResponseEntity.status( HttpStatus.OK ).body( infoItem );
    }

    @CrossOrigin
    @GetMapping(
            value = "/benefits",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoBenefits(HttpServletRequest request) {
        AuthUserInfo    authUserInfo;
        DataListInfo    dataListInfo = new DataListInfo();
        List<InfoItem>  infoItemList;

        if( AuthUserInfoUtil.isAuthUserInfoExisted( request ) ){
            authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo("인증정보를 찾을 수 없습니다. 다시 로그인하세요."));
        }

        try {
            infoItemList = infoService.getInfoBenefitsItemList( authUserInfo.getCmplxId() );
        } catch( DataNotFoundException e ) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body( new SimpleErrorInfo( e.getMessage() ));
        }

        dataListInfo.setData( infoItemList );
        dataListInfo.setMsg("Benefits 목록 가져오기");

        return ResponseEntity.status( HttpStatus.OK ).body( dataListInfo );
    }


    @CrossOrigin
    @GetMapping(
            value = "/benefits/{itemIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoBenefitsDetail( HttpServletRequest request,
                                                 @PathVariable("itemIdx") int itemIdx ) {

        AuthUserInfo  authUserInfo;
        InfoItem      infoItem;

        if( AuthUserInfoUtil.isAuthUserInfoExisted( request ) ){
            authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo("인증정보를 찾을 수 없습니다. 다시 로그인하세요."));
        }

        try {
            infoItem = infoService.getInfoBenefitsItem(authUserInfo.getCmplxId(), itemIdx);
        } catch( DataNotFoundException e ) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body( new SimpleErrorInfo( e.getMessage() ) );
        } catch( OperationFailedException e ) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body( new SimpleErrorInfo( e.getMessage() ) );
        }

        infoItem.setMsg("Benefits 상세정보 가져오기");

        return ResponseEntity.status( HttpStatus.OK ).body( infoItem );
    }



    private MyStatusMain getMyStatusMainMockValue() {
        MyStatusInfo myStatusInfo;
        MyStatusMain myStatusMain = new MyStatusMain();
        List infoList = new ArrayList();

        myStatusMain.setCmplxNm("역삼동 하우징 따복하우스");
        myStatusMain.setHeadNm("조성우");
        myStatusMain.setStartDt("2017-06-20");

        myStatusInfo = new MyStatusInfo();
        myStatusInfo.setMyStatusIdx( 10 );
        myStatusInfo.setMyStatusNm("3월 (2018년)");
        infoList.add(myStatusInfo);

        myStatusInfo = new MyStatusInfo();
        myStatusInfo.setMyStatusIdx( 9 );
        myStatusInfo.setMyStatusNm("2월 (2018년)");
        infoList.add(myStatusInfo);

//        myStatusInfo = new MyStatusInfo();
//        myStatusInfo.setMyStatusIdx( 8 );
//        myStatusInfo.setMyStatusNm("1월 (2018년)");
//        infoList.add(myStatusInfo);
//
//        myStatusInfo = new MyStatusInfo();
//        myStatusInfo.setMyStatusIdx( 7 );
//        myStatusInfo.setMyStatusNm("12월 (2017년)");
//        infoList.add(myStatusInfo);
//
//        myStatusInfo = new MyStatusInfo();
//        myStatusInfo.setMyStatusIdx( 6 );
//        myStatusInfo.setMyStatusNm("11월 (2017년)");
//        infoList.add(myStatusInfo);
//
//        myStatusInfo = new MyStatusInfo();
//        myStatusInfo.setMyStatusIdx( 5 );
//        myStatusInfo.setMyStatusNm("10월 (2017년)");
//        infoList.add(myStatusInfo);
//
//        myStatusInfo = new MyStatusInfo();
//        myStatusInfo.setMyStatusIdx( 4 );
//        myStatusInfo.setMyStatusNm("9월 (2017년)");
//        infoList.add(myStatusInfo);

        myStatusMain.setInfoList( infoList );
        return myStatusMain;
    }

    @CrossOrigin
    @GetMapping(
            value = "/status",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoMyStatus(HttpServletRequest request) {

        MyStatusMain myStatusMain;

        myStatusMain = this.getMyStatusMainMockValue();

        myStatusMain.setMsg("MyStatus 첫 페이지");

        return ResponseEntity.status( HttpStatus.OK ).body( myStatusMain );
    }

    private MyStatusInfoDetail getInfoMyStatusDetailMockValue(int myStatusIdx ) {
        MyStatusInfoDetail myStatus = new MyStatusInfoDetail();

        myStatus.setDownloadLink(
                "http://www.kolonglobal.com/_inc/lib/filedown.asp" +
                "?idx=1471&fname=%ED%95%9C%ED%99%94%ED%88%AC%EC%9E%90%EC%A6%9D%EA%B6" +
                "%8C%5F%EC%BD%94%EC%98%A4%EB%A1%B1%EA%B8%80%EB%A1%9C%EB%B2%8C%5F20170306084157%2Epdf");
        myStatus.setMyStatusIdx( myStatusIdx );
        switch( myStatusIdx ) {
            case 10:
                myStatus.setMyStatusNm( "3월 (2018년)" );
                myStatus.setChargesDate("2018년 3월");
                myStatus.setTotalCharges(865900);
                myStatus.setRentalCharges(805280);
                myStatus.setElectricityCharges(15280);
                myStatus.setGasCharges(15280);
                myStatus.setWaterCharges(15280);
                myStatus.setParkingCharges(15280);

                break;
            case 9:
                myStatus.setMyStatusNm( "2월 (2018년)" );
                myStatus.setChargesDate("2018년 2월");
                myStatus.setTotalCharges(765900);
                myStatus.setRentalCharges(705280);
                myStatus.setElectricityCharges(15280);
                myStatus.setGasCharges(15280);
                myStatus.setWaterCharges(15280);
                myStatus.setParkingCharges(15280);

                break;
            default :
                myStatus.setMyStatusNm( "3월 (2018년)" );
                myStatus.setChargesDate("2018년 3월");
                myStatus.setTotalCharges(865900);
                myStatus.setRentalCharges(805280);
                myStatus.setElectricityCharges(15280);
                myStatus.setGasCharges(15280);
                myStatus.setWaterCharges(15280);
                myStatus.setParkingCharges(15280);

                break;
        }

        return myStatus;
    }

    @CrossOrigin
    @GetMapping(
            value = "/status/{myStatusIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoMyStatusDetail(HttpServletRequest request,
                                                @PathVariable("myStatusIdx") int myStatusIdx ) {
        return ResponseEntity.status( HttpStatus.OK ).body( this.getInfoMyStatusDetailMockValue( myStatusIdx ) );
    }

    @CrossOrigin
    @GetMapping(
            value = "/profile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoProfile(HttpServletRequest request ) {

        AuthUserInfo authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );
        InfoUserProfile userProfile;

        try {
            userProfile = infoService.getInfoProfile( authUserInfo );
        } catch( DataNotFoundException e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( e.getMessage() );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( userProfile  );
    }

    @CrossOrigin
    @PutMapping(
            value = "/profile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setInfoProfile( HttpServletRequest request,
                                          @RequestBody Map<String, String> bodyMap ) {

        AuthUserInfo authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );
        InfoUserProfile userProfile;
        String newEmail = bodyMap.get("newEmail");
        String oldUserPw = bodyMap.get("oldUserPw");
        String newUserPw = bodyMap.get("newUserPw");

        try {
            if( newEmail != null && (oldUserPw == null || newUserPw == null) ) {
                // Email 변경
                logger.debug("email 변경!");
                userProfile = infoService.updateInfoProfileEmail( authUserInfo, newEmail );
            } else if( (oldUserPw != null && newUserPw != null) && newEmail == null ) {
                // 암호 변경
                logger.debug("암호 변경!");
                userProfile = infoService.updateInfoProfileUserPw( authUserInfo, oldUserPw, newUserPw );
            } else {
                logger.error("잘못된 파라미터입니다.");
                return ResponseEntity
                        .status( HttpStatus.BAD_REQUEST )
                        .body( new SimpleErrorInfo("잘못된 파라미터입니다.") );
            }
        } catch( DataNotFoundException e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo(e.getMessage()) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( userProfile );
    }

}
