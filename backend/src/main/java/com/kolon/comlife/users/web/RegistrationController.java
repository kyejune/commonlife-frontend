package com.kolon.comlife.users.web;

import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.complexes.model.ComplexSimpleInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.users.model.AgreementInfo;
import com.kolon.comlife.users.service.RegistrationService;
import com.kolon.common.http.HttpRequestFailedException;
import com.kolon.common.http.HttpGetRequester;
import org.apache.http.impl.client.CloseableHttpClient;
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
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/registration/*")
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private static final String IOK_MASTER_HOST         = "https://dev-master.smartiok.com";
    private static final String IOK_LIST_DONG_INFO_PATH = "/mobile/controller/MobileUserController/listDongInfo.do";
    private static final String IOK_LIST_HO_INFO_PATH = "/mobile/controller/MobileUserController/listHoInfo.do";
    private static final String IOK_REQ_HAED_CERT_NUM_PATH = "/mobile/controller/MobileUserCertNoController/reqHeadCertNumber.do";
    private static final String IOK_CFRM_HAED_CERT_NUM_PATH = "/mobile/controller/MobileUserCertNoController/confirmHeadCertNumber.do";
    private static final String IOK_CHECK_USER_ID_PATH = "/mobile/controller/MobileUserController/checkUserId.do";
    private static final String IOK_REQ_USER_CERT_NUM_PATH = "/mobile/controller/MobileUserCertNoController/reqUserCertNumber.do";
    private static final String IOK_CFRM_USER_CERT_NUM_PATH = "/mobile/controller/MobileUserCertNoController/confirmUserCertNumber.do";
    private static final String IOK_REGISTER_USER_PATH = "/mobile/controller/MobileUserController/registerMember.do";


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
    public ResponseEntity getAgreement(HttpServletRequest request) {
        List<AgreementInfo> agreements;

        agreements = regService.getLatestAgreement();

        if( agreements.isEmpty() ) {
            logger.debug("return value is NULL");
            return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( new SimpleMsgInfo("표시할 정보가 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( agreements.get(0) );
    }


    /**
     * 1. 등록지점 선택 중, 모든 현장 목록 가져오기
     */
    @GetMapping(
            value="/complexes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataListInfo> getComplexList() {
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
        HttpGetRequester requester;
        Map<String, Map> result;
        List<String>     dongList;    // 동 목록
        DataListInfo     retBody;

        try {
            requester = new HttpGetRequester( httpClient,
                                              IOK_MASTER_HOST,
                                              IOK_LIST_DONG_INFO_PATH );
            requester.setParameter("cmplxId", String.valueOf(id) );
            result = requester.execute();
        }
        catch (URISyntaxException e) {
            logger.error( "Invalid URI: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
        }
        catch (IOException e) {
            logger.error( "Failed to request: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }
        catch (HttpRequestFailedException e) {
            logger.error( "Failed response[StatusCode:" + e.getStatusCode() + "]:" + e.getMessage() );
            if (e.getStatusCode() >= 500 ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
            }
        }

        dongList = (List)result.get("DATA").get("DONG");
        if( dongList.isEmpty() )
        {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("해당하는 동이 없습니다"));
        }

        retBody = new DataListInfo(dongList);

        return ResponseEntity.status( HttpStatus.OK ).body( retBody );
    }

    /**
     * 2-2. 호 목록 가져오기
     */
    @GetMapping(
            value="/complexes/{id}/{dong}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getComplexDongHoList( @PathVariable("id") int id, @PathVariable("dong") String dong ) {
        HttpGetRequester requester;
        Map<String, Map> result;
        List<String>     hoList;    // 호 목록
        DataListInfo     retBody;

        try {
            requester = new HttpGetRequester( httpClient,
                                              IOK_MASTER_HOST,
                                              IOK_LIST_HO_INFO_PATH );
            requester.setParameter("cmplxId", String.valueOf(id) );
            requester.setParameter("dong", String.valueOf(dong) );
            result = requester.execute();
        }
        catch (URISyntaxException e) {
            logger.error( "Invalid URI: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
        }
        catch (IOException e) {
            logger.error( "Failed to request: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }
        catch (HttpRequestFailedException e) {
            logger.error( "Failed response[StatusCode:" + e.getStatusCode() + "]:" + e.getMessage() );
            if (e.getStatusCode() >= 500 ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
            }
        }

        // IOK 반환 값 변환 및 에러 핸들링
        hoList = (List)result.get("DATA").get("HO");
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
        HttpGetRequester requester;
        Map              result;

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    IOK_MASTER_HOST,
                    IOK_REQ_HAED_CERT_NUM_PATH );
            requester
                .setParameter("cmplxId", request.getParameter("cmplxId"))
                .setParameter("dong", request.getParameter("dong"))
                .setParameter("ho", request.getParameter("ho"))
                .setParameter("headNm", request.getParameter("headNm"))
                .setParameter("headCell", request.getParameter("headCell"));
            result = requester.execute();
        }
        catch (URISyntaxException e) {
            logger.error( "Invalid URI: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleMsgInfo("경로 또는 입력값이 잘못 되었습니다."));
        }
        catch (IOException e) {
            logger.error( "Failed to request: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }
        catch (HttpRequestFailedException e) {
            logger.error( "Failed response[StatusCode:" + e.getStatusCode() + "]:" + e.getMessage() );
            if (e.getStatusCode() >= 500 ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
            }
        }

        if( !(boolean)result.get("resFlag") ) {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( new SimpleErrorInfo((String)result.get("msg")));
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
        HttpGetRequester requester;
        Map              result;

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    IOK_MASTER_HOST,
                    IOK_CFRM_HAED_CERT_NUM_PATH );
            requester
                    .setParameter("cmplxId", request.getParameter("cmplxId"))
                    .setParameter("dong", request.getParameter("dong"))
                    .setParameter("ho", request.getParameter("ho"))
                    .setParameter("headNm", request.getParameter("headNm"))
                    .setParameter("headCell", request.getParameter("headCell"))
                    .setParameter("userCertId", request.getParameter("userCertId"))
                    .setParameter("headCertNum", request.getParameter("headCertNum"));
            result = requester.execute();
        }
        catch (URISyntaxException e) {
            logger.error( "Invalid URI: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleMsgInfo("경로 또는 입력값이 잘못 되었습니다."));
        }
        catch (IOException e) {
            logger.error( "Failed to request: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }
        catch (HttpRequestFailedException e) {
            logger.error( "Failed response[StatusCode:" + e.getStatusCode() + "]:" + e.getMessage() );
            if (e.getStatusCode() >= 500 ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
            }
        }

        if( !(boolean)result.get("resFlag") ) {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( new SimpleErrorInfo((String)result.get("msg")));
        }

        return ResponseEntity.status( HttpStatus.OK ).body( lowerMsgKeyName(result) );
    }




    /**
     * 3-1. 사용자 아이디 중복 확인
     */
    @GetMapping(
            value="/existedUser/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkExistedUser( @PathVariable("userId") String userId ) {
        HttpGetRequester requester;
        Map              result;

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    IOK_MASTER_HOST,
                    IOK_CHECK_USER_ID_PATH );
            requester
                    .setParameter("userId", userId );
            result = requester.execute();
        }
        catch (URISyntaxException e) {
            logger.error( "Invalid URI: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleMsgInfo("경로 또는 입력값이 잘못 되었습니다."));
        }
        catch (IOException e) {
            logger.error( "Failed to request: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }
        catch (HttpRequestFailedException e) {
            logger.error( "Failed response[StatusCode:" + e.getStatusCode() + "]:" + e.getMessage() );
            if (e.getStatusCode() >= 500 ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
            }
        }

        if( !(boolean)result.get("resFlag") ) {
            return ResponseEntity.status( HttpStatus.CONFLICT ).body( lowerMsgKeyName(result) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( lowerMsgKeyName(result) );
    }


    /**
     * 3-2. 사용자 휴대폰 인증번호 요청
     */
    @GetMapping(
            value="/certUserCellNo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity requestCertUserCellNo( HttpServletRequest request ) {
        HttpGetRequester requester;
        Map              result;

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    IOK_MASTER_HOST,
                    IOK_REQ_USER_CERT_NUM_PATH );
            requester
                    .setParameter("cmplxId", request.getParameter("cmplxId"))
                    .setParameter("dong", request.getParameter("dong"))
                    .setParameter("ho", request.getParameter("ho"))
                    .setParameter("headNm", request.getParameter("headNm"))
                    .setParameter("headCell", request.getParameter("headCell"))
                    .setParameter("userNm", request.getParameter("userNm"))
                    .setParameter("userCell", request.getParameter("userCell"))
                    .setParameter("userCertId", request.getParameter("userCertId"));
            result = requester.execute();
        }
        catch (URISyntaxException e) {
            logger.error( "Invalid URI: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleMsgInfo("경로 또는 입력값이 잘못 되었습니다."));
        }
        catch (IOException e) {
            logger.error( "Failed to request: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }
        catch (HttpRequestFailedException e) {
            logger.error( "Failed response[StatusCode:" + e.getStatusCode() + "]:" + e.getMessage() );
            if (e.getStatusCode() >= 500 ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
            }
        }

        if( !(boolean)result.get("resFlag")) {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( result );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    /**
     * 3-3. 사용자 휴대폰 인증번호 확인
     */
    @PostMapping(
            value="/certUserCellNo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateCertUserCellNo( HttpServletRequest request ) {
        HttpGetRequester requester;
        Map              result;

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    IOK_MASTER_HOST,
                    IOK_CFRM_USER_CERT_NUM_PATH );
            requester
                    .setParameter("userCell", request.getParameter("userCell"))
                    .setParameter("userCertId", request.getParameter("userCertId"))
                    .setParameter("userCertNum", request.getParameter("userCertNum"));
            result = requester.execute();
        }
        catch (URISyntaxException e) {
            logger.error( "Invalid URI: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleMsgInfo("경로 또는 입력값이 잘못 되었습니다."));
        }
        catch (IOException e) {
            logger.error( "Failed to request: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }
        catch (HttpRequestFailedException e) {
            logger.error( "Failed response[StatusCode:" + e.getStatusCode() + "]:" + e.getMessage() );
            if (e.getStatusCode() >= 500 ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
            }
        }

        if( !(boolean)result.get("resFlag")) {
            return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( lowerMsgKeyName(result) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( lowerMsgKeyName(result) );
    }


    /**
     * 3-4. 회원 가입
     */
    @PostMapping(
            value="/newUser",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerNewUser( HttpServletRequest request ) {
        HttpGetRequester requester;
        Map              result;

        try {
            requester = new HttpGetRequester(
                    httpClient,
                    IOK_MASTER_HOST,
                    IOK_REGISTER_USER_PATH );
            requester
                    .setParameter("cmplxId", request.getParameter("cmplxId"))
                    .setParameter("dong", request.getParameter("dong"))
                    .setParameter("ho", request.getParameter("ho"))
                    .setParameter("headNm", request.getParameter("headNm"))
                    .setParameter("headCell", request.getParameter("headCell"))
                    .setParameter("userNm", request.getParameter("userNm"))
                    .setParameter("userCell", request.getParameter("userCell"))
                    .setParameter("certNum", request.getParameter("userCertId"))  // 주의!: certNum과 userCertNum이 다름
                    .setParameter("smsChkYn", request.getParameter("smsChkYn"))
                    .setParameter("smsChkDt", request.getParameter("smsChkDt"))
                    .setParameter("userId", request.getParameter("userId"))
                    .setParameter("userPw", request.getParameter("userPw"));
            result = requester.execute();
        }
        catch (URISyntaxException e) {
            logger.error( "Invalid URI: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleMsgInfo("경로 또는 입력값이 잘못 되었습니다."));
        }
        catch (IOException e) {
            logger.error( "Failed to request: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }
        catch (HttpRequestFailedException e) {
            logger.error( "Failed response[StatusCode:" + e.getStatusCode() + "]:" + e.getMessage() );
            if (e.getStatusCode() >= 500 ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
            }
        }

        if( !(boolean)result.get("resFlag")) {
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( lowerMsgKeyName(result) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( lowerMsgKeyName(result) );
    }

    /**
     * 읿부 IOK의 반환 결과의 Key 값이 'MSG'와 같이 대문자로 반환 됨
     * 일관성 유지를 위해 따라서, 소문자로 변경함
     */
    private Map lowerMsgKeyName(Map map) {
        map.put("msg", map.get("MSG"));
        map.remove("MSG");
        return map;
    }
}
