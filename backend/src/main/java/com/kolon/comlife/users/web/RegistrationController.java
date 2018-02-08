package com.kolon.comlife.users.web;

import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.complexes.model.ComplexSimpleInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.users.model.AgreementInfo;
import com.kolon.comlife.users.service.RegistrationService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/registration/*")
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Resource(name = "registrationService")
    private RegistrationService regService;

    @Resource(name = "complexService")
    private ComplexService complexService;

    @Autowired
    private CloseableHttpClient httpClient;

    /**
     * 0. "개인정보 취급 정책" 전송
     */
    @GetMapping(
            value="/agreement",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgreementInfo> getAgreement(HttpServletRequest request) {
        List<AgreementInfo> agreements;

        agreements = regService.getLatestAgreement();

        if( agreements.isEmpty() ) {
            logger.debug("return value is NULL");
            return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( null );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( agreements.get(0) );
    }


    /**
     * 1. 등록지점 선택 중, 모든 현장 목록 가져오기
     */
    @GetMapping(
            value="/complexes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getComplexList() {
        DataListInfo result = new DataListInfo();
        List<ComplexSimpleInfo> complexList = null;

        complexList = complexService.getComplexSimpleList();
        result.setData(complexList);

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    /**
     * 2-1. 동 목록 가져오기
     */
    @GetMapping(
            value="/complexes/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getComplexDongList( @PathVariable("id") int id ) {
        ObjectMapper mapper = new ObjectMapper();
        HttpGet hg;
        URIBuilder builder = new URIBuilder();
        URI uri = null;
        Map result;
        DataListInfo retBody;

        // todo: URI를 db table 또는 프로퍼티에서 로딩할 수 있도록 분리해야 함
        try {
            builder.setScheme("https")
                    .setHost("dev-master.smartiok.com")
                    .setPath("/mobile/controller/MobileUserController/listDongInfo.do")
                    .setParameter("cmplxId", String.valueOf(id) );
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body(null);
        }

        hg = new HttpGet(uri);
        hg.addHeader("Content-type", MediaType.APPLICATION_JSON_VALUE );

        try {
            HttpResponse hr = httpClient.execute(hg);
            result = mapper.readValue( hr.getEntity().getContent(), Map.class);
        } catch (IOException e) {
            logger.error( "Unable to make connection to " + uri + "\n\tMessage: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다"));
        }

        // IOK 반환 값 변환 및 에러 핸들링
        List hoList = (List)((Map)result.get("DATA")).get("DONG");
        if( hoList.isEmpty() )
        {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("해당하는 동이 없습니다"));
        }
        retBody = new DataListInfo(hoList);

        return ResponseEntity.status( HttpStatus.OK ).body( retBody );
    }

    /**
     * 2-2. 호 목록 가져오기
     */
    @GetMapping(
            value="/complexes/{id}/{dong}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getComplexDongHoList( @PathVariable("id") int id, @PathVariable("dong") String dong ) {
        ObjectMapper mapper = new ObjectMapper();
        HttpGet hg;
        URIBuilder builder = new URIBuilder();
        URI uri = null;
        Map result;
        DataListInfo retBody;

        // todo: URI를 db table 또는 프로퍼티에서 로딩할 수 있도록 분리해야 함
        try {
            builder.setScheme("https")
                    .setHost("dev-master.smartiok.com")
                    .setPath("/mobile/controller/MobileUserController/listHoInfo.do")
                    .setParameter("cmplxId", String.valueOf(id) )
                    .setParameter("dong", dong);
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleErrorInfo("잘못된 요청입니다"));  // todo: Error Message 한 곳으로 통합해야 함
        }

        hg = new HttpGet(uri);
        hg.addHeader("Content-type", MediaType.APPLICATION_JSON_VALUE );

        try {
            HttpResponse hr = httpClient.execute(hg);
            result = mapper.readValue( hr.getEntity().getContent(), Map.class);
        } catch (IOException e) {
            logger.error( "Unable to make connection to " + uri + "\n\tMessage: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다"));
        }

        // IOK 반환 값 변환 및 에러 핸들링
        List hoList = (List)((Map)result.get("DATA")).get("HO");
        if( hoList.isEmpty() )
        {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("해당하는 동/호가 없습니다"));
        }
        retBody = new DataListInfo(hoList);

        return ResponseEntity.status( HttpStatus.OK ).body( retBody );
    }

    /**
     * 2-3. 세대주 휴대폰 인증번호 요청
     */
    @GetMapping(
            value="/certHeadCellNo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity requestCertHeadCellNo( HttpServletRequest request ) {
        ObjectMapper mapper = new ObjectMapper();
        HttpGet hg;
        URIBuilder builder = new URIBuilder();
        URI uri = null;
        Map result;

        // todo: URI를 db table 또는 프로퍼티에서 로딩할 수 있도록 분리해야 함
        try {
            builder.setScheme("https")
                    .setHost("dev-master.smartiok.com")
                    .setPath("/mobile/controller/MobileUserCertNoController/reqHeadCertNumber.do")
                    .setParameter("cmplxId", request.getParameter("cmplxId"))
                    .setParameter("dong", request.getParameter("dong"))
                    .setParameter("ho", request.getParameter("ho"))
                    .setParameter("headNm", request.getParameter("headNm"))
                    .setParameter("headCell", request.getParameter("headCell"));
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body(null);
        }

        hg = new HttpGet(uri);
        hg.addHeader("Content-type", MediaType.APPLICATION_JSON_VALUE );

        try {
            HttpResponse hr = httpClient.execute(hg);
            result = mapper.readValue( hr.getEntity().getContent(), Map.class);
        } catch (IOException e) {
            logger.error( "Unable to make connection to " + uri + "\n\tMessage: " + e.getMessage() );
            return ResponseEntity.status( HttpStatus.SERVICE_UNAVAILABLE ).body(null);
        }

        if( !(boolean)result.get("resFlag")) {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( new SimpleErrorInfo( (String)result.get("msg") ) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }


    /**
     * 2-4. 세대주 휴대폰 인증번호 확인
     */
    @PostMapping(
            value="/certHeadCellNo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateCertHeadCellNo( HttpServletRequest request ) {
        ObjectMapper mapper = new ObjectMapper();
        HttpGet hg;
        URIBuilder builder = new URIBuilder();
        URI uri = null;
        Map result;

        // todo: URI를 db table 또는 프로퍼티에서 로딩할 수 있도록 분리해야 함
        try {
            builder.setScheme("https")
                    .setHost("dev-master.smartiok.com")
                    .setPath("/mobile/controller/MobileUserCertNoController/confirmHeadCertNumber.do")
                    .setParameter("cmplxId", request.getParameter("cmplxId"))
                    .setParameter("dong", request.getParameter("dong"))
                    .setParameter("ho", request.getParameter("ho"))
                    .setParameter("headNm", request.getParameter("headNm"))
                    .setParameter("headCell", request.getParameter("headCell"))
                    .setParameter("userCertId", request.getParameter("userCertId"))
                    .setParameter("headCertNum", request.getParameter("headCertNum"));
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body(null);
        }

        hg = new HttpGet(uri);
        hg.addHeader("Content-type", MediaType.APPLICATION_JSON_VALUE );

        try {
            HttpResponse hr = httpClient.execute(hg);
            result = mapper.readValue( hr.getEntity().getContent(), Map.class);
        } catch (IOException e) {
            logger.error( "Unable to make connection to " + uri + "\n\tMessage: " + e.getMessage() );
            return ResponseEntity.status( HttpStatus.SERVICE_UNAVAILABLE ).body(null);
        }

        if( !(boolean)result.get("resFlag")) {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( new SimpleErrorInfo( (String)result.get("MSG") ) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

}
