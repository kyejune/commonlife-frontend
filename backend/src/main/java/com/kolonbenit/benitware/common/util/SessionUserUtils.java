/**
 * COPYRIGHT ⓒ Kolon Benit CO. LTD. All rights reserved.
 * 코오롱베니트(주)의 사전 승인 없이 본 내용의 전부 또는 일부에 대한 복사, 배포,사용을 금합니다.
 */
package com.kolonbenit.benitware.common.util;

import com.benitware.framework.web.context.util.SessionUtils;
import com.benitware.framework.xplaform.domain.ResultSetMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @description 로그인 사용자의 세션을 가져오는 유틸 클래스
 * @author 장승호 
 * @version 1.0
 * @since 2014.01.19 
 * @date 2014.01.19 
 */
@Component
public class SessionUserUtils {
	
    public static String userKey;
	
    @Value("#{applicationProps['session.user.key']}")
	public void setUserKey(String userKey) {
		SessionUserUtils.userKey = userKey;
	}

    /**
	* 사용자 정보를 세션에 입력한다.
	* @param  userInfo 사용자 정보
	*/
	public static void setUserInfo(ResultSetMap resultSetMap) {
		SessionUtils.setAttribute(userKey, resultSetMap);
	}
	
	
	/**
	* 사용자 정보를 세션에서 갖고온다.
	* @return UserVO
	*/
	public static ResultSetMap getUserInfo() {
		return (ResultSetMap)SessionUtils.getAttribute(userKey);
	}
	
	
	/**
	 * 사용자ID
	 * @return
	 * @throws Exception
	 */
	public static String getUserId() throws Exception{
		
		String userId = null;
		
		ResultSetMap userInfo = (ResultSetMap) SessionUtils.getAttribute(userKey);
		
		if(userInfo != null){
			userId = (String) userInfo.get("userId");
		}
		
		return userId;
	}
	
	/**
	 * 사용자명
	 * @return
	 * @throws Exception
	 */
	public static String getUserNm() throws Exception{
		
		String userId = null;
		
		ResultSetMap userInfo = (ResultSetMap) SessionUtils.getAttribute(userKey);
		
		if(userInfo != null){
			userId = (String) userInfo.get("userNm");
		}
		
		return userId;
	}
	
	
}
