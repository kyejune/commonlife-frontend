package com.kolonbenit.benitware.common.util;

import com.kolonbenit.benitware.framework.xplaform.domain.ResultSetMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MobileSessionUtils {

	public static final String SESSION_USER_KEY = "mobileUserInfo";

	/**
	 * 로그인 사용자 정보를 세션에 저장
	 * @param obj
	 */
	public static void setSessionUserInfo(Object obj) {
		HttpServletRequest request	= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);

		session.setAttribute(SESSION_USER_KEY, obj);
		// 모바일 세션은 유효 타임아웃을 가지지 않음
		session.setMaxInactiveInterval(30*60);
	}

	/**
	 * 로그인 사용자 정보를 세션에서 갖고옴
	 * @return
	 */
	public static Object getSessionUserInfo() {
		HttpServletRequest request	= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		return (ResultSetMap) (session == null ? null : session.getAttribute(SESSION_USER_KEY));
	}

	/**
	 * 해당 키의 정보를 세션에 저장
	 * @param key
	 * @param obj
	 * @param nSecond
	 */
	public static void setSessionObject(String key, Object obj, int nSecond) {
		HttpServletRequest request	= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);

		session.setAttribute(key, obj);
		session.setMaxInactiveInterval(nSecond);
	}

	/**
	 * 해당 키의 정보를 세션에 저장
	 * @param key
	 * @param obj
	 */
	public static void setSessionObject(String key, Object obj) {
		HttpServletRequest request	= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);

		session.setAttribute(key, obj);
	}

	/**
	 * 해당 키의 정보를 세션에서 가지고옴
	 * @param key
	 * @return
	 */
	public static Object getSessionObject(String key) {
		HttpServletRequest request	= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		return (session == null ? null : session.getAttribute(key));
	}

	/**
	 * 해당 키의 정보를 세션에서 제거
	 * @param key
	 */
	public static void removeSessionObject(String key) {
		HttpServletRequest request	= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		session.removeAttribute(key);
	}

	/**
	 * 현재 세션의 고유 ID를 가지고옴
	 * @return
	 */
	public static String getSessionId() {
		HttpServletRequest request	= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		return session.getId();
	}
}
