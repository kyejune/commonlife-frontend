package com.kolonbenit.iot.mobile.controller;

import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import com.kolonbenit.iot.mobile.service.MobileUserCertNoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/mobile/controller/MobileUserCertNoController")

public class MobileUserCertNoController {

	/**
	 * Logger for this class and subclasses
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MobileUserCertNoService mobileUserCertNoService;
	
	

	@Autowired
	private MessageSource messageSource;

	private final Locale locale = Locale.KOREA;
	
	
	

	/**
	 * @description  회원가입  세대주 인증번호 요청을 처리한다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호요청
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
//	@RequestMapping(value = "reqHeadCertNumber.do", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
	public Map<String, Object> reqHeadCertNumber(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = mobileUserCertNoService.sendHeadCertificationNumber(parameter);
		//if ( (boolean) resMap.get("resFlag") ) {
		//	String sSessionId = MobileSessionUtils.getSessionId();
		//	resMap.put("JSESSIONID", sSessionId);
		//}
		return resMap;
	}
	
	
	/**
	 * @description  회원가입  사용자 인증번호 요청을 처리한다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호요청
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "reqUserCertNumber.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> reqUserCertNumber(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = mobileUserCertNoService.sendUserCertificationNumber(parameter);
		//if ( (boolean) resMap.get("resFlag") ) {
		//	String sSessionId = MobileSessionUtils.getSessionId();
		//	resMap.put("JSESSIONID", sSessionId);
		//}
		return resMap;
	}


	/**
	 * @description  세재주 인증번호 확인을 처리힌다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호확인
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "confirmHeadCertNumber.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> confirmHeadCertNumber(RequestParameter parameter, ModelMap model) throws Exception {
		return mobileUserCertNoService.confirmHeadCertificationNumber(parameter);
	}
	
	
	/**
	 * @description  사용자 인증번호 확인을 처리힌다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호확인
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "confirmUserCertNumber.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> confirmUserCertNumber(RequestParameter parameter, ModelMap model) throws Exception {
		return mobileUserCertNoService.confirmUserCertificationNumber(parameter);
	}
	
}

