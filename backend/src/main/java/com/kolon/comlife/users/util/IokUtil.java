package com.kolon.comlife.users.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;


/**
 * IOK Mobile Master서버의 결과값 및 파라미터 등의 정보를 CommonLife 서버의 결과 및 파라미터와 연결하기 위해 사용하는 Util
 */

public class IokUtil {
    final static private Logger logger = LoggerFactory.getLogger(IokUtil.class);

    public static final String IOK_RES_FLAG_STR = "resFlag";
    public static final String IOK_RES_TYPE_STR = "resType";
    public static final String IOK_RES_MSG_KEY = "MSG";
    public static final String IOK_RES_FLAG_ALREADY_LOGGED_IN_STR = "003";

    // unused the followings
    private static final String IOK_MASTER_HOST_PROP_GROUP = "IOK";
    private static final String IOK_MASTER_HOST_PROP_KEY = "MASTER_HOST";
    private static final String IOK_LIST_DONG_INFO_PATH = "/mobile/controller/MobileUserController/listDongInfo.do";
    private static final String IOK_LIST_HO_INFO_PATH = "/mobile/controller/MobileUserController/listHoInfo.do";
    private static final String IOK_REQ_HAED_CERT_NUM_PATH = "/mobile/controller/MobileUserCertNoController/reqHeadCertNumber.do";
    private static final String IOK_CFRM_HAED_CERT_NUM_PATH = "/mobile/controller/MobileUserCertNoController/confirmHeadCertNumber.do";
    private static final String IOK_CHECK_USER_ID_PATH = "/mobile/controller/MobileUserController/checkUserId.do";
    private static final String IOK_REQ_USER_CERT_NUM_PATH = "/mobile/controller/MobileUserCertNoController/reqUserCertNumber.do";
    private static final String IOK_CFRM_USER_CERT_NUM_PATH = "/mobile/controller/MobileUserCertNoController/confirmUserCertNumber.do";
    private static final String IOK_REGISTER_USER_PATH = "/mobile/controller/MobileUserController/registerMember.do";

    /**
     * 읿부 IOK의 반환 결과의 Key 값이 'MSG'와 같이 대문자로 반환 됨
     * 일관성 유지를 위해 따라서, 소문자로 변경함
     */
    static public Map lowerMsgKeyName(Map map) {
        map.put("msg", map.get("MSG"));
        map.remove("MSG");
        return map;
    }

    static public  boolean getResFlag( Map<String, Object> resultMap ) {
        return (boolean) resultMap.get( IOK_RES_FLAG_STR );
    }

    static public  String getResType( Map<String, Object> resultMap ) {
        return (String) resultMap.get( IOK_RES_TYPE_STR );
    }

    static public String getMsg( Map<String, Object> resultMap ) {
        return (String)resultMap.get( IOK_RES_MSG_KEY );
    }

    static public RequestParameter buildRequestParameter(HttpServletRequest request) {
        RequestParameter parameter = new RequestParameter();
        parameter.setRequest(request);

        Iterator e = request.getParameterMap().keySet().iterator();
        while(e.hasNext()) {
            String key = (String)e.next();
            logger.debug("Request.Parameter.KEY: " + key + ", VALUE:" + request.getParameter(key));
            parameter.put(key, request.getParameter(key));
        }

        return parameter;
    }


    static public ResponseEntity convertExceptionToResponse(Exception e ) {
        if( e instanceof UnsupportedEncodingException) {
            logger.error( e.getMessage() + ":" + e.getCause() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다.")); // todo: 적절한 error 값으로 변경 할 것
        } else if ( e instanceof JsonParseException) {
            logger.error( e.getMessage() + ":" + e.getCause() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다.")); // todo: ""
        } else if ( e instanceof JsonMappingException) {
            logger.error( e.getMessage() + ":" + e.getCause() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다.")); // todo: ""
        } else if ( e instanceof IOException) {
            logger.error( e.getMessage() + ":" + e.getCause() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다.")); // todo: ""
        }

        logger.error( e.getMessage() + ":" + e.getCause() );
        StackTraceElement[] elements = e.getStackTrace();
        StringBuilder strBuilder = new StringBuilder();
        for( StackTraceElement element : elements ) {
            strBuilder.append( "\t" );
            strBuilder.append( element.getClassName() );
            strBuilder.append( ":" );
            strBuilder.append( element.getMethodName() );
            strBuilder.append( ":" );
            strBuilder.append( element.getLineNumber() );
            strBuilder.append( "\n" );
        }
        logger.debug( strBuilder.toString() );

        return ResponseEntity
                .status( HttpStatus.SERVICE_UNAVAILABLE )
                .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다.")); // todo: ""
    }
}
