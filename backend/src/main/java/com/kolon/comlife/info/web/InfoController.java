package com.kolon.comlife.info.web;

import com.amazonaws.services.xray.model.Http;
import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.info.model.*;
import com.kolon.comlife.info.service.InfoService;
import com.kolon.comlife.info.service.impl.InfoServiceImpl;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.exception.NoDataException;
import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.model.UserExtInfo;
import com.kolon.comlife.users.service.UserService;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoMain(HttpServletRequest request) {

        AuthUserInfo authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );
        InfoMain     infoMain;

        try {
            infoMain = infoService.getInfoMain( authUserInfo );
        } catch( NoDataException e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( e.getMessage() );
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
        } catch( NoDataException e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                        .status( HttpStatus.BAD_REQUEST )
                    .body( e.getMessage() );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( postInfo );
    }

    private List<InfoItem> getInfoLivingGuideMockValue() {

        List itemList = new ArrayList();
        InfoItem item;

        item = new InfoItem();
        item.setItemIdx( 1 );
        item.setCmplxId( 125 );
        item.setItemNm( "유용한 정보" );
        item.setCateId("guide");
        item.setCateIdx(9);
        item.setCateNm("Living Guide");
        item.setImgSrc("cl_house-1");
        item.setDesc("~~~ ~~~");
        item.setDispOrder(1);
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        item = new InfoItem();
        item.setItemIdx( 2 );
        item.setCmplxId( 125 );
        item.setItemNm( "예약관련 안내" );
        item.setCateId("guide");
        item.setCateIdx(9);
        item.setCateNm("Living Guide");
        item.setImgSrc("cl_house-2");
        item.setDesc("~~~ ~~~");
        item.setDispOrder(2);
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        item = new InfoItem();
        item.setItemIdx( 3 );
        item.setCmplxId( 125 );
        item.setItemNm( "편의시설 안내" );
        item.setCateId("guide");
        item.setCateIdx(9);
        item.setCateNm("Living Guide");
        item.setImgSrc("cl_house-3");
        item.setDesc("~~~ ~~~");
        item.setDispOrder(3);
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        item = new InfoItem();
        item.setItemIdx( 4 );
        item.setCmplxId( 125 );
        item.setItemNm( "게스트관련 안내" );
        item.setCateId("guide");
        item.setCateIdx(9);
        item.setCateNm("Living Guide");
        item.setImgSrc("cl_house-1");
        item.setDesc("~~~ ~~~");
        item.setDispOrder(4);
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        item = new InfoItem();
        item.setItemIdx( 5 );
        item.setCmplxId( 125 );
        item.setItemNm( "개인정보 취급 정책 안내" );
        item.setCateId("guide");
        item.setCateIdx(9);
        item.setCateNm("Living Guide");
        item.setImgSrc("cl_house-2");
        item.setDesc("~~~ ~~~");
        item.setDispOrder( 5 );
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        item = new InfoItem();
        item.setItemIdx( 6 );
        item.setCmplxId( 125 );
        item.setItemNm( "서비스 약관" );
        item.setCateId("guide");
        item.setCateIdx(9);
        item.setCateNm("Living Guide");
        item.setImgSrc("cl_house-3");
        item.setDesc("~~~ ~~~");
        item.setDispOrder( 6 );
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        item = new InfoItem();
        item.setItemIdx( 6 );
        item.setCmplxId( 125 );
        item.setItemNm( "서비스 약관" );
        item.setCateId("guide");
        item.setCateIdx(9);
        item.setCateNm("Living Guide");
        item.setImgSrc("cl_house-3");
        item.setDesc("~~~ ~~~");
        item.setDispOrder( 6 );
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        item = new InfoItem();
        item.setItemIdx( 7 );
        item.setCmplxId( 125 );
        item.setItemNm( "커뮤니티 가이드라인" );
        item.setCateId("guide");
        item.setCateIdx(9);
        item.setCateNm("Living Guide");
        item.setImgSrc("cl_house-3");
        item.setDesc("~~~ ~~~");
        item.setDispOrder( 7 );
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        return itemList;
    }

    @CrossOrigin
    @GetMapping(
            value = "/guide",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoGuide(HttpServletRequest request) {
        DataListInfo dataListInfo = new DataListInfo();

        dataListInfo.setData( this.getInfoLivingGuideMockValue() );
        dataListInfo.setMsg("Living Guide 목록 가져오기");

        return ResponseEntity.status( HttpStatus.OK ).body( dataListInfo );
    }

    private List<InfoItem> getInfoBenefitsMockValue() {

        List itemList = new ArrayList();
        InfoItem item;

        item = new InfoItem();
        item.setItemIdx( 10 );
        item.setCmplxId( 125 );
        item.setItemNm( "역삼휘트니스 센터 25%" );
        item.setCateIdx(10);
        item.setCateId("benefits");
        item.setCateNm("Benefits");
        item.setImgSrc("cl_house-1");
        item.setDesc("~~~ ~~~");
        item.setDispOrder(1);
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        item = new InfoItem();
        item.setItemIdx( 12 );
        item.setCmplxId( 125 );
        item.setItemNm( "프레쉬 코드 셀러드 - 25%" );
        item.setCateIdx(10);
        item.setCateId("benefits");
        item.setCateNm("Benefits");
        item.setImgSrc("cl_house-2");
        item.setDesc("~~~ ~~~");
        item.setDispOrder(2);
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        return itemList;
    }

    @CrossOrigin
    @GetMapping(
            value = "/benefits",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoBenefits(HttpServletRequest request) {

        DataListInfo dataListInfo = new DataListInfo();

        dataListInfo.setData( this.getInfoBenefitsMockValue() );
        dataListInfo.setMsg("Benefits 목록 가져오기");

        return ResponseEntity.status( HttpStatus.OK ).body( dataListInfo );
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

        myStatusInfo = new MyStatusInfo();
        myStatusInfo.setMyStatusIdx( 8 );
        myStatusInfo.setMyStatusNm("1월 (2018년)");
        infoList.add(myStatusInfo);

        myStatusInfo = new MyStatusInfo();
        myStatusInfo.setMyStatusIdx( 7 );
        myStatusInfo.setMyStatusNm("12월 (2017년)");
        infoList.add(myStatusInfo);

        myStatusInfo = new MyStatusInfo();
        myStatusInfo.setMyStatusIdx( 6 );
        myStatusInfo.setMyStatusNm("11월 (2017년)");
        infoList.add(myStatusInfo);

        myStatusInfo = new MyStatusInfo();
        myStatusInfo.setMyStatusIdx( 5 );
        myStatusInfo.setMyStatusNm("10월 (2017년)");
        infoList.add(myStatusInfo);

        myStatusInfo = new MyStatusInfo();
        myStatusInfo.setMyStatusIdx( 4 );
        myStatusInfo.setMyStatusNm("9월 (2017년)");
        infoList.add(myStatusInfo);

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
        myStatus.setMyStatusNm( "12월 (2017년)" );
        myStatus.setChargesDate("2018년 3월");
        myStatus.setTotalCharges(865900);
        myStatus.setRentalCharges(805280);
        myStatus.setElectricityCharges(15280);
        myStatus.setGasCharges(15280);
        myStatus.setWaterCharges(15280);
        myStatus.setParkingCharges(15280);

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
        } catch( NoDataException e ) {
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
        } catch( NoDataException e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo(e.getMessage()) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( userProfile );
    }

}
