package com.kolon.comlife.info.web;

import com.kolon.comlife.info.model.InfoData;
import com.kolon.comlife.info.model.InfoMain;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.model.UserInfo;
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
        data.setInfoKey("benifits");
        data.setImgSrc("cl_life-9");
        data.setInfoNm("Benifits");
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

}
