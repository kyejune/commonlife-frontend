package com.kolon.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
