package com.kolon.comlife.info.web;

import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.info.model.*;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.users.model.PostUserInfo;
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


    private InfoMain getInfoMockValue( ) {
        InfoMain infoMainData = new InfoMain();

        infoMainData.setUserImgSrc("https://clback.cyville.net/imageStore/55");
        infoMainData.setUserNm("김연아");
        infoMainData.setCmplxNm("역삼하우스");
        infoMainData.setDong("101");
        infoMainData.setHo("202");
        infoMainData.setStartDt("2017-04-01");
        infoMainData.setPoint(125);
        infoMainData.setNotice(
                "안녕하세요. 3월 29일에 엘리베이터 정기 점검이 있습니다. " +
                "점검으로 인해 엘리베이터 사용이 불가능할 수 있습니다. " +
                "불편하시겠지만 양해 부탁드립니다.  - 점검 예상 시간 9:00 ~ 14:00 중 1시간");

        InfoData data;
        List dataList = new ArrayList<>();

        data = new InfoData();
        data.setInfoKey("event");
        data.setImgSrc("cl_life-10");
        data.setInfoNm("Event Feed");
        dataList.add( data );

        data = new InfoData();
        data.setInfoKey("support");
        data.setImgSrc("cl_life-11");
        data.setInfoNm("Living Support");
        dataList.add( data );

        data = new InfoData();
        data.setInfoKey("guide");
        data.setImgSrc("cl_life-12");
        data.setInfoNm("Living Guide");
        dataList.add( data );

        data = new InfoData();
        data.setInfoKey("benefits");
        data.setImgSrc("cl_life-9");
        data.setInfoNm("Benefits");
        dataList.add( data );

        data = new InfoData();
        data.setInfoKey("status");
        data.setImgSrc("cl_life-7");
        data.setInfoNm("My Status");
        dataList.add( data );

        data = new InfoData();
        data.setInfoKey("profile");
        data.setImgSrc("cl_life-8");
        data.setInfoNm("Profile");
        dataList.add( data );

        infoMainData.setInfoList(dataList);

        return infoMainData;
    }

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoMain(HttpServletRequest request) {

        return ResponseEntity.status( HttpStatus.OK ).body( this.getInfoMockValue() );
    }

    private PostInfo getInfoNoticeMockValue( ) {
        PostInfo postInfo = new PostInfo();

        postInfo.setPostIdx(73);
        postInfo.setUsrId(693);
        postInfo.setCmplxId(125);
        postInfo.setPostType("notice");
        postInfo.setContent( "안녕하세요. 3월 29일에 엘리베이터 정기 점검이 있습니다." +
                             "점검으로 인해 엘리베이터 사용이 불가능할 수 있습니다. 불편하시겠지만 양해 부탁드립니다." +
                             "\n- 점검 예상 시간 9:00 ~ 14:00 중 1시간 ");
        postInfo.setTitle("'역삼동 하우스' 엘리베이터 점검 안내");
        postInfo.setDelYn("N");
        postInfo.setLikesCount(1);
        postInfo.setMyLikeFlag(true);
        postInfo.setRegDttm("2018-03-27 07:34:59.0");
        postInfo.setUpdDttm("2018-03-27 07:34:59.0");
        postInfo.setRsvYn("N");
        postInfo.setRsvMaxCnt(0);
        postInfo.setRsvCount(0);
        postInfo.setRsvFlag(false);
        postInfo.setShareYn("N");
        postInfo.setEventBeginDttm(null);
        postInfo.setEventEndDttm(null);
        postInfo.setEventCmplxNm(null);
        postInfo.setEventPlaceNm(null);
        postInfo.setInquiryYn("Y");
        postInfo.setInquiryType("P");
        postInfo.setInquiryInfo("010-1234-0000");

        PostUserInfo user = new PostUserInfo();
        user.setUsrId(693);
        user.setUserNm("백동원");
        user.setUserAlias("백동원");
        user.setUserAlias("백동원");
        user.setCmplxNm("동원테스트현장");
        user.setImgSrc(null);

        postInfo.setUser(user);

        PostFileInfo postFileInfo = new PostFileInfo();
        postFileInfo.setPostFileIdx(21);
        postFileInfo.setPostIdx(73);
        postFileInfo.setUsrId(693);
        postFileInfo.setHost(null);
        postFileInfo.setMimeType("image/jpeg");
        postFileInfo.setFileName(null);
        postFileInfo.setFilePath("origin/article/1522136097813.jpeg");
        postFileInfo.setDelYn("N");
        postFileInfo.setRegDttm("2018-03-27 07:34:59.0");
        postFileInfo.setUpdDttm("2018-03-27 07:34:59.0");
        postFileInfo.setPost(null);

        List postFiles = new ArrayList();
        postFiles.add(postFileInfo);
        postInfo.setPostFiles(postFiles);

        return postInfo;
    }

    @CrossOrigin
    @GetMapping(
            value = "/notice",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoNotice(HttpServletRequest request) {

        return ResponseEntity.status( HttpStatus.OK ).body( this.getInfoNoticeMockValue() );
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

    private Map<String, Object> getInfoProfileMockValue() {
        Map ret = new HashMap();

        ret.put("userImgSrc" , "https://clback.cyville.net/imageStore/55");
        ret.put("userId", "yunakim");
        ret.put("userNm", "김유나");
        ret.put("email", "yunakim@gmail.com");

        ret.put("cmplxNm", "역삼동 하우징");
        ret.put("cmplxAddr", "서울시 강남구 역삼로 123");

        return ret;
    }

    @CrossOrigin
    @GetMapping(
            value = "/profile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoProfile(HttpServletRequest request ) {
        return ResponseEntity.status( HttpStatus.OK ).body( this.getInfoProfileMockValue() );
    }

    @CrossOrigin
    @PutMapping(
            value = "/profile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setInfoProfile(HttpServletRequest request ) {
        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("업데이트 되었습니다"));
    }

}
