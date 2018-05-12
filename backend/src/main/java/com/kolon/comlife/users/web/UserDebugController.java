package com.kolon.comlife.users.web;

import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.users.service.UserDebugService;
import com.kolon.comlife.users.service.UserKeyService;
import com.kolon.comlife.users.util.IokUtil;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import com.kolonbenit.benitware.common.util.cipher.AESCipher;
import com.kolonbenit.benitware.common.util.cipher.HashFunctionCipherUtil;
import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import com.kolonbenit.iot.mobile.controller.MobileUserCertNoController;
import com.kolonbenit.iot.mobile.service.MobileUserCertNoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Test 및 debug 용 Controller
 *
 * todo: WARNING!!! 접근 권한 설정해야 하거나, Production에는 제거해야 됨
 */

@Controller
@RequestMapping("/users/debug/")
public class UserDebugController {

    private final static Logger logger = LoggerFactory.getLogger( UserDebugController.class );

    @Autowired
    private MobileUserCertNoService mobileUserCertNoService;

    @Autowired
    private UserDebugService userDebugService;


    /**
     * userPw의 암호화 방식으로 value를 encoding하고, 결과를 반환합니다.
     */
    @GetMapping(
            value="encode",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getEncodeValue( HttpServletRequest request ) {
        Map result;

        result = userDebugService.getEncodeValue(request.getParameter("value") );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     *
     * params : userCertId, headNm, headCell
     */
    @GetMapping(
            value = "headCertNum",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getHeadCertNumByUserId( HttpServletRequest request ) {
        Map result;
        RequestParameter parameter = IokUtil.buildRequestParameter( request );

        result = userDebugService.getHeadCert( parameter );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     *
     * params : userCertId, userCell
     */
    @GetMapping(
            value = "userCertNum",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getUserCertNumByUserId( HttpServletRequest request ) {
        Map result;
        RequestParameter parameter = IokUtil.buildRequestParameter( request );

        result = userDebugService.getUserCert( parameter );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /**
     *
     * 로그인 한 이후에 발행 된, 토큰 값을 http header로 전송한다.
     * 해당 토큰에 해당하는 사용자 정보가 반한된다.
     *
     * header params: token=abcd1234....
     */
    @GetMapping(
            value="authUserInfo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getAuthUserInfo( HttpServletRequest request ) {
        logger.debug(">>>>>>>>>>>>>>>>>> authUserInfo >>>>>>>");
        Map<String, String> result = new HashMap();

        if( AuthUserInfoUtil.isAuthUserInfoExisted( request ) ) {

        }
        AuthUserInfo userInfo = AuthUserInfoUtil.getAuthUserInfo( request );


        if( userInfo != null ) {
            logger.debug(">>>>  there are authUserInfo");
            result.put("cmplxId", String.valueOf( userInfo.getCmplxId() ));
            result.put("homeId", String.valueOf( userInfo.getHomeId() ));
            result.put("userId", userInfo.getUserId());
            result.put("usrId", String.valueOf( userInfo.getUsrId() ));
            result.put("headId", String.valueOf( userInfo.getHeadId() ));
            result.put("userNm", userInfo.getUserNm());
            result.put("tokenOrg", userInfo.getTokenOrg());
            result.put("secretKey", userInfo.getSecretKey());
            result.put("issueDate", userInfo.getIssueDate());
            result.put("expireDate", userInfo.getExpireDate());
        } else {
            logger.debug(">>>>  no authUserInfo");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(""));
        }
        // ...


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
