package com.kolon.comlife.info.web;

import com.kolon.comlife.info.model.InfoData;
import com.kolon.comlife.info.model.InfoMain;
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

}
