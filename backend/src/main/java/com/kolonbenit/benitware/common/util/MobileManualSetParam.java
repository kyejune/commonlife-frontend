package com.kolonbenit.benitware.common.util;

import com.benitware.framework.http.parameter.RequestParameter;
import com.benitware.framework.xplaform.domain.ResultSetMap;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class MobileManualSetParam {

	/**
	 * <pre>
	 * 페이지 반환
	 *   - pageNo 키만 있는 경우, pageNo=0, pageRow=10
	 * </pre>
	 * @param parameter
	 * @return
	 */
	public static void getPageSet(RequestParameter parameter, String className) {

		if ( parameter.containsKey("pageNo") ) {

			String sPageNo = parameter.getString("pageNo", "0");
			String sPageRow = parameter.getString("pageRow", "10");

			int pageNo  = Integer.parseInt(sPageNo);
			int pageRow = Integer.parseInt(sPageRow);

			int resultNo = pageNo * pageRow;

			parameter.put("pageNo", resultNo);
			parameter.put("pageRow", pageRow);
		} else {
			// TODO: 모바일에서 처리 중인 이력,알림만 페이징 기본 설정
			if ( ("MobileMsgHistController".equals(className)) || ("MobileNotificationController".equals(className)) ) {
				parameter.put("pageNo", "0");
				parameter.put("pageRow", "10");
			}
		}
	}

	/**
	 * <pre>
	 * 페이지 반환
	 *   - pageNo 키만 있는 경우, pageNo=0, pageRow=10
	 * </pre>
	 * @param paramMap
	 */
	public static void getPageSet(Map<String, Object> paramMap, String className) {

		if ( paramMap.containsKey("pageNo") ) {

			String sPageNo = StringUtils.defaultString(String.valueOf(paramMap.get("pageNo")), "0");
			String sPageRow = StringUtils.defaultString(String.valueOf(paramMap.get("pageRow")), "10");

			int pageNo  = Integer.parseInt(sPageNo);
			int pageRow = Integer.parseInt(sPageRow);

			int resultNo = pageNo * pageRow;

			paramMap.put("pageNo", resultNo);
			paramMap.put("pageRow", pageRow);
		} else {
			// TODO: 모바일에서 처리 중인 이력,알림만 페이징 기본 설정
			if ( ("MobileMsgHistController".equals(className)) || ("MobileNotificationController".equals(className)) ) {
				paramMap.put("pageNo", "0");
				paramMap.put("pageRow", "10");
			}
		}
	}

	/**
	 * <pre>
	 * 요청 폼 파라미터에 userId, cmplxId가 없고,
	 * 로그인 세션이 존재하는 경우에 세션의 USER_ID와 CMPLX_ID를 RequestParameter에 설정
	 * </pre>
	 * @param sessionMap
	 * @param parameter
	 */
	public static void setSessionParam(ResultSetMap sessionMap, RequestParameter parameter) {
		// RequestParameter.java 에 userId가 없고, 로그인 세션이 존재하는 경우, RequestParameter.java 에 userId 세팅
		if ( !parameter.containsKey("userId") ) {
			if (sessionMap != null) {
				if ( sessionMap.containsKey("USER_ID") ) {
					String sUserId = (String) sessionMap.get("USER_ID");
					parameter.put("userId", sUserId);
				}
			}
		}

		// RequestParameter.java 에 cmplxId가 없고, 로그인 세션이 존재하는 경우, RequestParameter.java 에 cmplxId 세팅
		if ( !parameter.containsKey("cmplxId") ) {
			if (sessionMap != null) {
				if ( sessionMap.containsKey("CMPLX_ID") ) {
					String sCmplxId = Integer.toString((int) sessionMap.get("CMPLX_ID"));
					parameter.put("cmplxId", sCmplxId);
				}
			}
		}

		// RequestParameter.java 에 userId, cmplxId가 없고, 로그인 세션이 존재하는 경우, RequestParameter.java 에 userId, cmplxId 세팅
		if ( (!parameter.containsKey("userId")) && (!parameter.containsKey("cmplxId")) ) {
			if (sessionMap != null) {
				if ( sessionMap.containsKey("USER_ID") && sessionMap.containsKey("CMPLX_ID") ) {
					String sUserId = (String) sessionMap.get("USER_ID");
					String sCmplxId = Integer.toString((int) sessionMap.get("CMPLX_ID"));

					parameter.put("userId", sUserId);
					parameter.put("cmplxId", sCmplxId);
				}
			}
		}

		// GW_CD 세팅
		if (sessionMap != null) {
			if ( sessionMap.containsKey("GW_CD") ) {
				parameter.put("gwCd", (String) sessionMap.get("GW_CD"));
			}
		}
		
		// HOME_ID 세팅
		if (sessionMap != null) {
			if ( sessionMap.containsKey("HOME_ID") ) {
				parameter.put("homeId", String.valueOf(sessionMap.get("HOME_ID")));
			}
		}
	}

	/**
	 * <pre>
	 * 요청 폼 파라미터에 userId, cmplxId가 없고,
	 * 로그인 세션이 존재하는 경우에 세션의 USER_ID와 CMPLX_ID를 paramMap에 설정
	 * </pre>
	 * @param sessionMap
	 * @param paramMap
	 */
	public static void setSessionParam(ResultSetMap sessionMap, Map<String, Object> paramMap) {
		// paramMap 에 userId가 없고, 로그인 세션이 존재하는 경우, RequestParameter.java 에 userId 세팅
		if ( !paramMap.containsKey("userId") ) {
			if (sessionMap != null) {
				if ( sessionMap.containsKey("USER_ID") ) {
					String sUserId = (String) sessionMap.get("USER_ID");
					paramMap.put("userId", sUserId);
				}
			}
		}

		// paramMap 에 cmplxId가 없고, 로그인 세션이 존재하는 경우, RequestParameter.java 에 cmplxId 세팅
		if ( !paramMap.containsKey("cmplxId") ) {
			if (sessionMap != null) {
				if ( sessionMap.containsKey("CMPLX_ID") ) {
					String sCmplxId = Integer.toString((int) sessionMap.get("CMPLX_ID"));
					paramMap.put("cmplxId", sCmplxId);
				}
			}
		}

		// paramMap 에 userId, cmplxId가 없고, 로그인 세션이 존재하는 경우, RequestParameter.java 에 userId, cmplxId 세팅
		if ( (!paramMap.containsKey("userId")) && (!paramMap.containsKey("cmplxId")) ) {
			if (sessionMap != null) {
				if ( sessionMap.containsKey("USER_ID") && sessionMap.containsKey("CMPLX_ID") ) {
					String sUserId = (String) sessionMap.get("USER_ID");
					String sCmplxId = Integer.toString((int) sessionMap.get("CMPLX_ID"));

					paramMap.put("userId", sUserId);
					paramMap.put("cmplxId", sCmplxId);
				}
			}
		}

		// GW_CD 세팅
		if (sessionMap != null) {
			if ( sessionMap.containsKey("GW_CD") ) {
				paramMap.put("gwCd", (String) sessionMap.get("GW_CD"));
			}
		}

		// HOME_ID 세팅
		if (sessionMap != null) {
			if ( sessionMap.containsKey("HOME_ID") ) {
				paramMap.put("homeId", String.valueOf(sessionMap.get("HOME_ID")));
			}
		}
	}

}
