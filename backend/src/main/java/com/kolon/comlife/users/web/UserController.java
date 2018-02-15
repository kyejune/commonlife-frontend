package com.kolon.comlife.users.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.users.util.IokUtil;
import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import com.kolonbenit.iot.mobile.controller.MobileUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/users/")
public class UserController {
    private static final String IOK_HEADER_TOKEN_KEY = "token";

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    protected MobileUserController mobileUserController;

    /**
     * @description 중복 로그인을 체크하고 모바일 로그인을 처리한다.
     * @param request
     * @return
     */
    @GetMapping(
            value="/login",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginUser(HttpServletRequest request) {

        RequestParameter    parameter;
        Map<String, Object> result;
        String              retMsg;
        boolean             resFlag;
        String              resType;

        parameter = IokUtil.buildRequestParameter(request);

        try {
            // 1. 기존 다른 기기에서의 로그인 여부 확인
            result = mobileUserController.mobileUserLoginConfirm( parameter, null );
            resFlag = IokUtil.getResFlag( result );
            resType = IokUtil.getResType( result );

            if( !(resFlag) && !(resType.equals(IokUtil.IOK_RES_FLAG_ALREADY_LOGGED_IN_STR)) ) {
                return ResponseEntity
                        .status( HttpStatus.UNAUTHORIZED )
                        .body( new SimpleErrorInfo( IokUtil.getMsg(result) ));
            }

            // 2. 새롭게 로그인
            result = mobileUserController.mobileUserLogin( parameter, null );
            resFlag = IokUtil.getResFlag( result );
            if( !resFlag ) {
                return ResponseEntity
                        .status( HttpStatus.UNAUTHORIZED )
                        .body( new SimpleErrorInfo( IokUtil.getMsg(result) ));
            }

            if( resType != null && resType.equals(IokUtil.IOK_RES_FLAG_ALREADY_LOGGED_IN_STR) ) {
                // (resType: "003" == already logged in other device) ==> 바로 로그인 합니다.
                retMsg = "로그인에 성공하였습니다. 다른 기기는 자동으로 로그아웃 됩니다.";     // todo: message 옮기기
            } else {
                retMsg = "로그인에 성공하였습니다."; // todo: message 옮기기
            }
        } catch( Exception e ) {
            return IokUtil.convertExceptionToResponse( e );
        }

        result.put("msg", retMsg);
        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }


    /**
     * @description  모바일에서 푸시 토큰이 갱신될 경우, DB 업데이트 처리한다.
     *
     * Params : gcmRegId, deviceId, osType(선택)
     * Return :
     *  200 - 업데이트 성공
     *  404 - 해당하는 deviceId가 없음
     *  500 - 기타 실패
     */
    @PutMapping(
            value="/pushToken",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateGcmRegId( HttpServletRequest request )
    {
        RequestParameter    parameter;
        Map<String, Object> result;
        boolean             resFlag;

        parameter = IokUtil.buildRequestParameter( request );

        try {
            result = mobileUserController.modifyGcmRegInfoIntro( parameter, null );
            resFlag = IokUtil.getResFlag( result );

            if( !resFlag ) {
                return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( result );
            }
        } catch( Exception e ) {
            return IokUtil.convertExceptionToResponse( e );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }


    /**
     * @description  모바일 토큰을 업데이트한다. (팝업 띄우기 위함)
     * Headers : token - 인증 토큰
     * todo: BUGBUG: 사용 권한 체크 안하나...?
     */
    @PutMapping(
            value="/token",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUserToken( HttpServletRequest request ) {
        RequestParameter    parameter;
        Map<String, Object> result;
        boolean             resFlag;
        String              token;

        parameter = IokUtil.buildRequestParameter( request );
        token = request.getHeader( IOK_HEADER_TOKEN_KEY );
        if( token == null ) {
            return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body( "인증토큰을 포함해야 합니다." );
        }

        try {
            parameter.put( IOK_HEADER_TOKEN_KEY, token );
            result = mobileUserController.mobileUserTokenUpdate( parameter, null , token);
            resFlag = IokUtil.getResFlag( result );

            if( !resFlag ) {
                return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( result );
            }
        } catch( Exception e ) {
            return IokUtil.convertExceptionToResponse( e );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    /**
     * @description  모바일 로그인상태를 체크한다. (팝업 띄우기 위함)
     * params : userId - 사용자 ID
     * Return :
     *   200 - userId에 대한 사용자 정보 상태 반환
     *       - "msg" 사용자 정보 상태 반환
     *              e.g. 로그인 됨(0001), 로그아웃 상태(0002),
     *                   자동 로그아웃 된 상태(0003), 사용자 정보가 없음(0004) or 사용자 정보가 없음(0005)
     *       - "status" : 사용자 정보 상태 코드: "0001" ~ "0005"
     *   500 - 그 외의 시스템 문제
     * todo: BUGBUG: 사용 권한 체크 안하나...?
     * todo: userID를 PATH로 변경할 것
     */
    @GetMapping(
            value="/status",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserLoginStatus( HttpServletRequest request ) {
        RequestParameter    parameter;
        Map<String, Object> result;
        boolean             resFlag;
        String              resType;

        parameter = IokUtil.buildRequestParameter( request );
        try {
            result = mobileUserController.mobileUserLoginStatus( parameter, null );
            // todo: refactoring
            resType = IokUtil.getResType( result );
            result.put("status", resType);
            result.remove("resType");
            result = IokUtil.lowerMsgKeyName( result );
        } catch( Exception e ) {
            return IokUtil.convertExceptionToResponse( e );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    /**
     * @description  사용자를 로그아웃 합니다.
     * params : userId - 사용자 ID
     * Return :
     *   200 - userId에 대한 사용자 정보 상태 반환
     *       - "msg" 사용자 정보 상태 반환
     *              e.g. 로그인 됨(0001), 로그아웃 상태(0002),
     *                   자동 로그아웃 된 상태(0003), 사용자 정보가 없음(0004) or 사용자 정보가 없음(0005)
     *       - "status" : 사용자 정보 상태 코드: "0001" ~ "0005"
     *   500 - 그 외의 시스템 문제
     * todo: BUGBUG: 사용 권한 체크 안하나...?
     * todo: userID를 PATH로 변경할 것
     */

    @GetMapping(
            value="/logout",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity logoutUser( HttpServletRequest request ) {

        RequestParameter    parameter;
        Map<String, Object> result;
        boolean             resFlag;
        String              resType;

        parameter = IokUtil.buildRequestParameter( request );

        try {
            result = mobileUserController.mobileUserLogout( parameter, null);
            resFlag = IokUtil.getResFlag( result );
        } catch( Exception e ) {
            return IokUtil.convertExceptionToResponse( e );
        }

        // todo: message 통합
        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("로그아웃 되었습니다."));
    }


}
