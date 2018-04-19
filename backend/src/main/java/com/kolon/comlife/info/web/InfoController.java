package com.kolon.comlife.info.web;

import com.amazonaws.services.xray.model.Http;
import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.imageStore.service.ImageStoreService;
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
import com.kolon.common.prop.ServicePropertiesMap;
import com.kolon.common.servlet.AuthUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    @CrossOrigin
    @GetMapping(
            value = "/guide/{itemIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoGuideDetail( HttpServletRequest request,
                                              @PathVariable("itemIdx") int itemIdx ) {

        InfoItem item = new InfoItem();
        ImageInfo imageInfo = new ImageInfo();
        Map imageInfoMap = new TreeMap();
        int imageIdx = 222;

        item.setItemIdx( itemIdx );
        item.setCmplxId( 125 );
        item.setItemNm( "유용한 정보" );
        item.setCateId("guide");
        item.setCateIdx(9);
        item.setCateNm("Living Guide");
        item.setImgSrc(imageStoreService.getImageFullPathByIdx( 212, ImageInfoUtil.SIZE_SUFFIX_SMALL ));
        item.setDesc("오설록, 차와 제주가 선사하는 삶의 아름다움.\n\n모든 메뉴 및 상품 10% 할인!\n\n구매시 직원에게 CommonLife 멤버쉽 키카드를 보여주시면 됩니다. 오설록 티하우스 강남점과 명동점에서만 혜택 적용 가능합니다.\n\nAbout Us: Osulloc, Beauty of life offered by tea and Jeju Island.\n\nOffer Details: Enjoy a 10% discount on all orders!\n\nHow to Redeem: Simply show your CommonLife keycard to the cashier when you pay. (Only applicable at Osulloc Teahouse Gangnam and Myeongdong locations)");
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");

        imageIdx = 222;
        imageInfoMap.put("imageIdx", imageIdx );
        imageInfoMap.put("parentIdx", itemIdx );
        imageInfoMap.put("mimeType", "image/jpeg");
        imageInfoMap.put("filePath", "origin/benefit/1522123476794.jpeg");
        imageInfoMap.put("originPath", imageStoreService.getImageFullPathByIdx( imageIdx ));
        imageInfoMap.put("smallPath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_SMALL ));
        imageInfoMap.put("mediumPath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_MEDIUM ));
        imageInfoMap.put("largePath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_LARGE ));

        imageInfoMap.put("regDttm", "2018-03-27 04:04:37.0");
        imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");
        imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");
        imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");

        List imageInfoList = new ArrayList();
        imageInfoList.add(imageInfoMap);

        item.setImageInfo( imageInfoList );

        return ResponseEntity.status( HttpStatus.OK ).body( item );
    }


    private List<InfoItem> getInfoBenefitsMockValue() {

        List itemList = new ArrayList();
        InfoItem item;

        item = new InfoItem();
        item.setItemIdx( 10 );
        item.setCmplxId( 125 );
        item.setItemNm( "10% 할인 프로모션 코드 제공 (10% Off Promotion)" );
        item.setCateIdx(10);
        item.setCateId("benefits");
        item.setCateNm("Benefits");
        item.setImgSrc(imageStoreService.getImageFullPathByIdx( 210, ImageInfoUtil.SIZE_SUFFIX_SMALL ));
        item.setDesc(
                "벅시는 예약기반의 공항 셔틀 밴 라이드 셰어링 서비스 입니다. 경쟁력 있는 가격에, 관리가 잘 된 밴/드라이버님이 약속한 시간에 편안한 라이드를 제공합니다. 집이나 사무실에서 공항까지, 공항에서 집이나 사무실까지 도어투도어 서비스를 제공합니다.\n" +
                        "라이드 예약 비용의 10%를 할인해 드립니다. 서비스 지역/가격 및 사용 방법은 앱과 웹사이트에서 확인하실 수 있습니다.\n" +
                        "- 구글 플레이스토어, 애플 앱스토어에서 벅 시앱을 인스톨\u2028- 계정 등록\u2028- 쿠폰 메뉴에서 커먼라이프 프로모션 코드 입력\u2028- 벅시 예약\u2028- 결제 화면에서 쿠폰 버튼을 눌러 쿠폰 사용\u2028- 프로모션 코드 : COMMON-BUXI\n" +
                        "About Us:\u2028BUXI provide a reservation based airport van ride-sharing service for competitive price. Highly maintained vans and drivers will give you a comfortable ride from your house/office to Incheon/Gimpo airport and vice versa, door-to-door.\n" +
                        "Details:\u202810% off for all ride reservations\n" +
                        "How to Redeem:\u2028- Install BUXI app from Google Play Store or Apple App Store.\u2028- Sign Up.\u2028- Register Commonlife promotion code at Coupon menu.\u2028- Book a ride.\u2028- Click the \"Coupon\" button at payment procedure and select the coupon.\u2028- Promotion code : COMMON-BUXI\n"
        );
        item.setDispOrder(2);
        item.setSetYn("Y");
        item.setDelYn("N");
        item.setRegDttm("2018-03-29 06:48:47.0");
        item.setUpdDttm("2018-03-29 06:48:47.0");
        itemList.add(item);

        item = new InfoItem();
        item.setItemIdx( 12 );
        item.setCmplxId( 125 );
        item.setItemNm( "모든 메뉴 및 상품 10% 할인 (10% off all orders)" );
        item.setCateIdx(10);
        item.setCateId("benefits");
        item.setCateNm("Benefits");
        item.setImgSrc(imageStoreService.getImageFullPathByIdx( 212, ImageInfoUtil.SIZE_SUFFIX_SMALL ));
        item.setDesc("오설록, 차와 제주가 선사하는 삶의 아름다움.\n\n모든 메뉴 및 상품 10% 할인!\n\n구매시 직원에게 CommonLife 멤버쉽 키카드를 보여주시면 됩니다. 오설록 티하우스 강남점과 명동점에서만 혜택 적용 가능합니다.\n\nAbout Us: Osulloc, Beauty of life offered by tea and Jeju Island.\n\nOffer Details: Enjoy a 10% discount on all orders!\n\nHow to Redeem: Simply show your CommonLife keycard to the cashier when you pay. (Only applicable at Osulloc Teahouse Gangnam and Myeongdong locations)");
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


    @CrossOrigin
    @GetMapping(
            value = "/benefits/{itemIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInfoBenefitsDetail( HttpServletRequest request,
                                                 @PathVariable("itemIdx") int itemIdx ) {

        InfoItem item = new InfoItem();
        ImageInfo imageInfo = new ImageInfo();
        Map imageInfoMap = new TreeMap();
        int imageIdx = 222;
        List imageInfoList = new ArrayList();

        switch( itemIdx ) {
            case 12:
                item.setItemIdx( 12 );
                item.setCmplxId( 125 );
                item.setItemNm( "모든 메뉴 및 상품 10% 할인 (10% off all orders)" );
                item.setCateIdx(10);
                item.setCateId("benefits");
                item.setCateNm("Benefits");
                item.setImgSrc(imageStoreService.getImageFullPathByIdx( 212, ImageInfoUtil.SIZE_SUFFIX_SMALL ));
                item.setDesc("오설록, 차와 제주가 선사하는 삶의 아름다움.\n\n모든 메뉴 및 상품 10% 할인!\n\n구매시 직원에게 CommonLife 멤버쉽 키카드를 보여주시면 됩니다. 오설록 티하우스 강남점과 명동점에서만 혜택 적용 가능합니다.\n\nAbout Us: Osulloc, Beauty of life offered by tea and Jeju Island.\n\nOffer Details: Enjoy a 10% discount on all orders!\n\nHow to Redeem: Simply show your CommonLife keycard to the cashier when you pay. (Only applicable at Osulloc Teahouse Gangnam and Myeongdong locations)");
                item.setDispOrder(2);
                item.setSetYn("Y");
                item.setDelYn("N");
                item.setRegDttm("2018-03-29 06:48:47.0");
                item.setUpdDttm("2018-03-29 06:48:47.0");

                imageIdx = 222;
                imageInfoMap.put("imageIdx", imageIdx );
                imageInfoMap.put("parentIdx", 12);
                imageInfoMap.put("mimeType", "image/jpeg");
                imageInfoMap.put("filePath", "origin/benefit/1522123476794.jpeg");
                imageInfoMap.put("originPath", imageStoreService.getImageFullPathByIdx( imageIdx ));
                imageInfoMap.put("smallPath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_SMALL ));
                imageInfoMap.put("mediumPath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_MEDIUM ));
                imageInfoMap.put("largePath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_LARGE ));

                imageInfoMap.put("regDttm", "2018-03-27 04:04:37.0");
                imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");
                imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");
                imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");

                imageInfoList.add(imageInfoMap);

                item.setImageInfo( imageInfoList );
                break;
            case 10:
                item.setItemIdx( 10 );
                item.setCmplxId( 125 );
                item.setItemNm( "10% 할인 프로모션 코드 제공 (10% Off Promotion)" );
                item.setCateIdx(10);
                item.setCateId("benefits");
                item.setCateNm("Benefits");
                item.setImgSrc(imageStoreService.getImageFullPathByIdx( 210, ImageInfoUtil.SIZE_SUFFIX_SMALL ));
                item.setDesc(
                        "벅시는 예약기반의 공항 셔틀 밴 라이드 셰어링 서비스 입니다. 경쟁력 있는 가격에, 관리가 잘 된 밴/드라이버님이 약속한 시간에 편안한 라이드를 제공합니다. 집이나 사무실에서 공항까지, 공항에서 집이나 사무실까지 도어투도어 서비스를 제공합니다.\n" +
                        "라이드 예약 비용의 10%를 할인해 드립니다. 서비스 지역/가격 및 사용 방법은 앱과 웹사이트에서 확인하실 수 있습니다.\n" +
                        "- 구글 플레이스토어, 애플 앱스토어에서 벅 시앱을 인스톨\u2028- 계정 등록\u2028- 쿠폰 메뉴에서 커먼라이프 프로모션 코드 입력\u2028- 벅시 예약\u2028- 결제 화면에서 쿠폰 버튼을 눌러 쿠폰 사용\u2028- 프로모션 코드 : COMMON-BUXI\n" +
                        "About Us:\u2028BUXI provide a reservation based airport van ride-sharing service for competitive price. Highly maintained vans and drivers will give you a comfortable ride from your house/office to Incheon/Gimpo airport and vice versa, door-to-door.\n" +
                        "Details:\u202810% off for all ride reservations\n" +
                        "How to Redeem:\u2028- Install BUXI app from Google Play Store or Apple App Store.\u2028- Sign Up.\u2028- Register Commonlife promotion code at Coupon menu.\u2028- Book a ride.\u2028- Click the \"Coupon\" button at payment procedure and select the coupon.\u2028- Promotion code : COMMON-BUXI\n"
                );
                item.setDispOrder(2);
                item.setSetYn("Y");
                item.setDelYn("N");
                item.setRegDttm("2018-03-29 06:48:47.0");
                item.setUpdDttm("2018-03-29 06:48:47.0");


                imageIdx = 220;
                imageInfoMap.put("imageIdx", imageIdx );
                imageInfoMap.put("parentIdx", 10);
                imageInfoMap.put("mimeType", "image/jpeg");
                imageInfoMap.put("filePath", "origin/benefit/1522123476794.jpeg");
                imageInfoMap.put("originPath", imageStoreService.getImageFullPathByIdx( imageIdx ));
                imageInfoMap.put("smallPath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_SMALL ));
                imageInfoMap.put("mediumPath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_MEDIUM ));
                imageInfoMap.put("largePath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_LARGE ));

                imageInfoMap.put("regDttm", "2018-03-27 04:04:37.0");
                imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");
                imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");
                imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");

                imageInfoList.add(imageInfoMap);

                item.setImageInfo( imageInfoList );

                break;
            default:
                item.setItemIdx( 12 );
                item.setCmplxId( 125 );
                item.setItemNm( "모든 메뉴 및 상품 10% 할인 (10% off all orders)" );
                item.setCateIdx(10);
                item.setCateId("benefits");
                item.setCateNm("Benefits");
                item.setImgSrc(imageStoreService.getImageFullPathByIdx( 212, ImageInfoUtil.SIZE_SUFFIX_SMALL ));
                item.setDesc("오설록, 차와 제주가 선사하는 삶의 아름다움.\n\n모든 메뉴 및 상품 10% 할인!\n\n구매시 직원에게 CommonLife 멤버쉽 키카드를 보여주시면 됩니다. 오설록 티하우스 강남점과 명동점에서만 혜택 적용 가능합니다.\n\nAbout Us: Osulloc, Beauty of life offered by tea and Jeju Island.\n\nOffer Details: Enjoy a 10% discount on all orders!\n\nHow to Redeem: Simply show your CommonLife keycard to the cashier when you pay. (Only applicable at Osulloc Teahouse Gangnam and Myeongdong locations)");
                item.setDispOrder(2);
                item.setSetYn("Y");
                item.setDelYn("N");
                item.setRegDttm("2018-03-29 06:48:47.0");
                item.setUpdDttm("2018-03-29 06:48:47.0");

                imageIdx = 222;
                imageInfoMap.put("imageIdx", imageIdx );
                imageInfoMap.put("parentIdx", 12);
                imageInfoMap.put("mimeType", "image/jpeg");
                imageInfoMap.put("filePath", "origin/benefit/1522123476794.jpeg");
                imageInfoMap.put("originPath", imageStoreService.getImageFullPathByIdx( imageIdx ));
                imageInfoMap.put("smallPath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_SMALL ));
                imageInfoMap.put("mediumPath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_MEDIUM ));
                imageInfoMap.put("largePath", imageStoreService.getImageFullPathByIdx( imageIdx, ImageInfoUtil.SIZE_SUFFIX_LARGE ));

                imageInfoMap.put("regDttm", "2018-03-27 04:04:37.0");
                imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");
                imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");
                imageInfoMap.put("updDttm", "2018-03-27 04:04:37.0");

                imageInfoList.add(imageInfoMap);

                item.setImageInfo( imageInfoList );
                break;
        }

        return ResponseEntity.status( HttpStatus.OK ).body( item );
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
