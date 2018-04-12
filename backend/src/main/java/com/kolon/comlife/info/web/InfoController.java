package com.kolon.comlife.info.web;

import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.info.model.CategoryInfo;
import com.kolon.comlife.info.model.InfoData;
import com.kolon.comlife.info.model.InfoMain;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.users.model.PostUserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    private List<CategoryInfo> getInfoLivingGuideMockValue() {

        List categoryList = new ArrayList();
        CategoryInfo category;

        category = new CategoryInfo();
        category.setCateIdx( 1 );
        category.setCmplxId( 125 );
        category.setCateNm( "유용한 정보" );
        category.setCateId(null);
        category.setImgSrc("cl_house-1");
        category.setDesc("~~~ ~~~");
        category.setDispOrder(1);
        category.setSetYn("Y");
        category.setDelYn("N");
        category.setRegDttm("2018-03-29 06:48:47.0");
        category.setUpdDttm("2018-03-29 06:48:47.0");
        categoryList.add(category);

        category = new CategoryInfo();
        category.setCateIdx( 2 );
        category.setCmplxId( 125 );
        category.setCateNm( "예약관련 안내" );
        category.setCateId(null);
        category.setImgSrc("cl_house-2");
        category.setDesc("~~~ ~~~");
        category.setDispOrder(2);
        category.setSetYn("Y");
        category.setDelYn("N");
        category.setRegDttm("2018-03-29 06:48:47.0");
        category.setUpdDttm("2018-03-29 06:48:47.0");
        categoryList.add(category);

        category = new CategoryInfo();
        category.setCateIdx( 3 );
        category.setCmplxId( 125 );
        category.setCateNm( "편의시설 안내" );
        category.setCateId(null);
        category.setImgSrc("cl_house-3");
        category.setDesc("~~~ ~~~");
        category.setDispOrder(3);
        category.setSetYn("Y");
        category.setDelYn("N");
        category.setRegDttm("2018-03-29 06:48:47.0");
        category.setUpdDttm("2018-03-29 06:48:47.0");
        categoryList.add(category);

        category = new CategoryInfo();
        category.setCateIdx( 4 );
        category.setCmplxId( 125 );
        category.setCateNm( "게스트관련 안내" );
        category.setCateId(null);
        category.setImgSrc("cl_house-1");
        category.setDesc("~~~ ~~~");
        category.setDispOrder(4);
        category.setSetYn("Y");
        category.setDelYn("N");
        category.setRegDttm("2018-03-29 06:48:47.0");
        category.setUpdDttm("2018-03-29 06:48:47.0");
        categoryList.add(category);

        category = new CategoryInfo();
        category.setCateIdx( 5 );
        category.setCmplxId( 125 );
        category.setCateNm( "개인정보 취급 정책 안내" );
        category.setCateId(null);
        category.setImgSrc("cl_house-2");
        category.setDesc("~~~ ~~~");
        category.setDispOrder( 5 );
        category.setSetYn("Y");
        category.setDelYn("N");
        category.setRegDttm("2018-03-29 06:48:47.0");
        category.setUpdDttm("2018-03-29 06:48:47.0");
        categoryList.add(category);

        category = new CategoryInfo();
        category.setCateIdx( 6 );
        category.setCmplxId( 125 );
        category.setCateNm( "서비스 약관" );
        category.setCateId(null);
        category.setImgSrc("cl_house-3");
        category.setDesc("~~~ ~~~");
        category.setDispOrder( 6 );
        category.setSetYn("Y");
        category.setDelYn("N");
        category.setRegDttm("2018-03-29 06:48:47.0");
        category.setUpdDttm("2018-03-29 06:48:47.0");
        categoryList.add(category);

        category = new CategoryInfo();
        category.setCateIdx( 6 );
        category.setCmplxId( 125 );
        category.setCateNm( "서비스 약관" );
        category.setCateId(null);
        category.setImgSrc("cl_house-3");
        category.setDesc("~~~ ~~~");
        category.setDispOrder( 6 );
        category.setSetYn("Y");
        category.setDelYn("N");
        category.setRegDttm("2018-03-29 06:48:47.0");
        category.setUpdDttm("2018-03-29 06:48:47.0");
        categoryList.add(category);

        category = new CategoryInfo();
        category.setCateIdx( 7 );
        category.setCmplxId( 125 );
        category.setCateNm( "커뮤니티 가이드라인" );
        category.setCateId(null);
        category.setImgSrc("cl_house-3");
        category.setDesc("~~~ ~~~");
        category.setDispOrder( 7 );
        category.setSetYn("Y");
        category.setDelYn("N");
        category.setRegDttm("2018-03-29 06:48:47.0");
        category.setUpdDttm("2018-03-29 06:48:47.0");
        categoryList.add(category);

        return categoryList;
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

    private List<CategoryInfo> getInfoBenefitsMockValue() {

        List categoryList = new ArrayList();
        CategoryInfo category;

        category = new CategoryInfo();
        category.setCateIdx( 10 );
        category.setCmplxId( 125 );
        category.setCateNm( "역삼휘트니스 센터 25%" );
        category.setCateId(null);
        category.setImgSrc("cl_house-1");
        category.setDesc("~~~ ~~~");
        category.setDispOrder(1);
        category.setSetYn("Y");
        category.setDelYn("N");
        category.setRegDttm("2018-03-29 06:48:47.0");
        category.setUpdDttm("2018-03-29 06:48:47.0");
        categoryList.add(category);

        category = new CategoryInfo();
        category.setCateIdx( 12 );
        category.setCmplxId( 125 );
        category.setCateNm( "프레쉬 코드 셀러드 - 25%" );
        category.setCateId(null);
        category.setImgSrc("cl_house-2");
        category.setDesc("~~~ ~~~");
        category.setDispOrder(2);
        category.setSetYn("Y");
        category.setDelYn("N");
        category.setRegDttm("2018-03-29 06:48:47.0");
        category.setUpdDttm("2018-03-29 06:48:47.0");
        categoryList.add(category);

        return categoryList;
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


}
