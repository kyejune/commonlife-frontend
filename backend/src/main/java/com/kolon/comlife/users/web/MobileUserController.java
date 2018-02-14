package com.kolon.comlife.users.web;

import com.benitware.framework.http.parameter.RequestParameter;
import com.benitware.framework.xplaform.domain.ResultSetMap;
import com.kolon.comlife.users.service.MobileApplianceService;
import com.kolon.comlife.users.service.MobileUserService;
import com.kolonbenit.benitware.common.util.MobileSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/mobile/controller/MobileUserController")
public class MobileUserController {

	/**
	 * Logger for this class and subclasses
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MobileUserService mobileUserService;
	
	@Autowired
	private MobileApplianceService mobileApplianceService;

	@Autowired
	private MessageSource messageSource;

	private final Locale locale = Locale.KOREA;
	
	
	
	
	
	
	

	/**
	 * @description  모바일에서 푸시 토큰이 갱신될 경우, DB 업데이트 처리한다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName GCM등록정보수정
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "modifyGcmRegInfoIntro.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> modifyGcmRegInfoIntro(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		if ( !(parameter.containsKey("osType")) ) {
			parameter.put("osType", "1");
		}

		int nCnt = mobileUserService.modifyGcmRegInfoIntro(parameter);
		if (nCnt > 0) {
			resMap.put("resFlag", true);
		} else {
			resMap.put("resFlag", false);
		}
		return resMap;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @description  모바일 로그인을 처리한다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 모바일사용자로그인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "mobileUserLogin.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
//	public Map<String, Object> mobileUserLogin(RequestParameter parameter, ModelMap model) throws Exception {
	public Map<String, Object> mobileUserLogin(HttpServletRequest request) throws Exception {

		RequestParameter parameter = new RequestParameter();
		parameter.setRequest(request);

		Iterator e = request.getParameterMap().keySet().iterator();
		while(e.hasNext()) {
			String key = (String)e.next();
			logger.debug("Request.Parameter.KEY: " + key + ", VALUE:" + request.getParameter(key));
			parameter.put(key, request.getParameter(key));
		}
//		// 안드로이드/아이폰 체크 코드
//		if ( !(parameter.containsKey("osType")) ) {
//			parameter.put("osType", "1");
//		}
//
		
		//  토큰 발행
		//  기존 로그인되어 있는 서비스 종료
		Map<String, Object> homeIdMap = mobileUserService.mobileUserLogin(parameter);

		boolean sessionFlag = false;

		if ( homeIdMap.containsKey("HOME_ID") ) {
			sessionFlag = true;
		}

		if (sessionFlag) {
			ResultSetMap resultSetMap = new ResultSetMap();
			resultSetMap.putNoLowerCase("USER_ID", homeIdMap.get("USER_ID"));
			resultSetMap.putNoLowerCase("USER_NM", homeIdMap.get("USER_NM"));
			resultSetMap.putNoLowerCase("CMPLX_ID", homeIdMap.get("CMPLX_ID"));
			resultSetMap.putNoLowerCase("HOME_ID", homeIdMap.get("HOME_ID"));
			resultSetMap.putNoLowerCase("GW_CD", homeIdMap.get("GW_CD"));

			RequestParameter param = new RequestParameter();
			param.put("cmplxId", homeIdMap.get("CMPLX_ID"));
			param.put("homeId", homeIdMap.get("HOME_ID"));
			param.put("expireDays", "90");
			
			//삼성 계정 refresh_token 조회
			Map<String, Object> refreshMap = mobileApplianceService.getTokenInfo(param);
			
			if (refreshMap != null && refreshMap.get("RE_TOKEN_KEY") != null && !"".equals(refreshMap.get("RE_TOKEN_KEY"))) {
				
				logger.info("refreshMap : {}", refreshMap.toString());
				
				//access token 요청
				Map<String, Object> accessTokenInfo = mobileApplianceService.remakeAccessToken(refreshMap);

				//token update
				if (accessTokenInfo != null && accessTokenInfo.get("access_token") != null && !"".equals(accessTokenInfo.get("access_token"))) {
					
					logger.info("remakeMap : {}", accessTokenInfo.toString());
					
					accessTokenInfo.put("cmplxId", refreshMap.get("CMPLX_ID"));
					accessTokenInfo.put("homeId", refreshMap.get("HOME_ID"));
					accessTokenInfo.put("vendorId", refreshMap.get("VENDOR_ID"));
					accessTokenInfo.put("userIdKey", refreshMap.get("USER_ID_KEY"));
					
					mobileApplianceService.updateToken(accessTokenInfo);
				}
			}
			
			// 사용정보 세션에 저장
			MobileSessionUtils.setSessionUserInfo(resultSetMap);

			String sSessionId = MobileSessionUtils.getSessionId();
			//homeIdMap.put("JSESSIONID", sSessionId);
		}
		return homeIdMap;
	}

	
	


	
	
	
	
	
	
	
	/**
	 * @description  모바일 로그인을 처리 전, 중복 로그인을 체크한다. (팝업 띄우기 위함)
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 중복로그인체크처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "mobileUserLoginConfirm.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> mobileUserLoginConfirm(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = mobileUserService.mobileUserLoginConfirm(parameter);
		return resMap;
	}
	
	
	
	
	
		
	/**
	 * @description  모바일 토큰을 업데이트한다. (팝업 띄우기 위함)
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 상태결과
	 * @throws Exception
	 * @methodKorName 로그인 상태처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "mobileUserTokenUpdate.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> mobileUserTokenUpdate(RequestParameter parameter, ModelMap model, @RequestHeader(value="token") String token ) throws Exception {
		//logger.info("token -----------> {}", token);
		parameter.put("token", token);
		Map<String, Object> resMap = mobileUserService.tokenUpdate(parameter);
		return resMap;
	}
	
	
	
	
	
	
	/**
	 * @description  모바일 로그인상태를 체크한다. (팝업 띄우기 위함)
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 상태결과
	 * @throws Exception
	 * @methodKorName 로그인 상태처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "mobileUserLoginStatus.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> mobileUserLoginStatus(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = mobileUserService.mobileUserLoginStatus(parameter);
		return resMap;
	}

	
	
	/**
	 * @description  좌측 슬라이드 메뉴의 로그아웃을 처리한다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 로그아웃처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "mobileUserLogout.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> mobileUserLogout(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		boolean isSuccess = false;
		isSuccess = mobileUserService.mobileUserLogout(parameter);
		if (isSuccess) {
			parameter.getRequest().getSession().invalidate();
		}

		resMap.put("resFlag", isSuccess);
		return resMap;
	}

	
	
	/**
	 * @description  회원가입 시, 단지 리스트를 조회한다. (콤보 박스)
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 단지리스트조회
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "listComplexInfo.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> listComplexInfo(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		List<Object> list = mobileUserService.listComplexInfo(parameter);
		resMap.put("DATA", list);

		return resMap;
	}

	
	
	/**
	 * @description  회원가입 시, 동 리스트를 조회한다. (콤보 박스)
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 동리스트조회
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "listDongInfo.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
//	public Map<String, Object> listDongInfo(RequestParameter parameter, ModelMap model) throws Exception {
	public Map<String, Object> listDongInfo(HttpServletRequest request) throws Exception {
	    logger.debug(">>>> " );
		RequestParameter parameter = new RequestParameter();
		parameter.setRequest(request);

		Map<String, Object> resMap = new HashMap<>();

		Iterator e = request.getParameterMap().keySet().iterator();
		while(e.hasNext()) {
			String key = (String)e.next();
			logger.debug("Request.Parameter.KEY: " + key + ", VALUE:" + request.getParameter(key));
			parameter.put(key, request.getParameter(key));
		}


		List<Object> list = mobileUserService.listDongInfo(parameter);

		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("DONG", list);
		resMap.put("DATA", dataMap);
		return resMap;
	}

	/**
	 * @description  회원가입 시, 호 리스트를 조회한다. (콤보 박스)
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 호리스트조회
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "listHoInfo.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> listHoInfo(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		List<Object> list = mobileUserService.listHoInfo(parameter);
		resMap.put("DATA", list);

		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("HO", list);
		resMap.put("DATA", dataMap);
		return resMap;
	}

	/**
	 * @description  회원가입 시, ID 중복확인을 처리한다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName ID중복확인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "checkUserId.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> checkUserId(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		String sMessage = "";
		boolean isSuccess = false;

		Map<String, Object> userIdMap = mobileUserService.checkUserId(parameter);
		if (userIdMap != null) {
			sMessage = messageSource.getMessage("mobile.check.userid.0001", null, locale);
		} else {
			isSuccess = true;
			String[] args = {parameter.getString("userId")};
			sMessage = messageSource.getMessage("mobile.check.userid.0002", args, locale);
		}
		resMap.put("MSG", sMessage);
		resMap.put("resFlag", isSuccess);

		return resMap;
	}

	/**
	 * @description  회원가입 시, ID 찾기를 처리한다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName ID찾기
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "searchUserId.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> searchUserId(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		Map<String, Object> userIdMap = mobileUserService.searchUserId(parameter);

		String sMessage = "";
		boolean isSuccess = false;
		if (userIdMap == null) {
			sMessage = messageSource.getMessage("mobile.common.not.found", null, locale);
		} else {
			sMessage = messageSource.getMessage("mobile.send.sms", new String[] {"아이디"}, locale);
			isSuccess = true;
		}

		resMap.put("MSG", sMessage);
		resMap.put("resFlag", isSuccess);
		return resMap;
	}

	/**
	 * @description  로그인 화면 및 설정-계정 설정 화면에서 비밀번호 찾기를 처리한다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 비밀번호찾기
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "searchUserPw.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> searchUserPw(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		Map<String, Object> userPwMap = mobileUserService.searchUserPw(parameter);

		String sMessage = "";
		boolean isSuccess = false;
		if (userPwMap == null) {
			sMessage = messageSource.getMessage("mobile.common.not.found", null, locale);
		} else {
			sMessage = messageSource.getMessage("mobile.send.sms", new String[] {"임시 비밀번호"}, locale);
			isSuccess = true;
		}

		resMap.put("MSG", sMessage);
		resMap.put("resFlag", isSuccess);
		return resMap;
	}

	/**
	 * @description  회원등록을 처리한다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 회원등록
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "registerMember.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> registerMember(RequestParameter parameter, ModelMap model) throws Exception {
		return mobileUserService.registerMember(parameter);
	}
	
	

	/**
	 * @description  회원가입 / 로그인-비밀번호 찾기 / 설정-계정 설정-비밀번호 찾기 시, 인증번호 요청을 처리한다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호요청
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "reqCertNumber.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> reqCertNumber(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = mobileUserService.sendCertificationNumber(parameter);
		//if ( (boolean) resMap.get("resFlag") ) {
		//	String sSessionId = MobileSessionUtils.getSessionId();
		//	resMap.put("JSESSIONID", sSessionId);
		//}
		return resMap;
	}

	/**
	 * @description  인증번호 확인을 처리힌다.
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호확인
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "confirmCertNumber.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> confirmCertNumber(RequestParameter parameter, ModelMap model) throws Exception {
		return mobileUserService.confirmCertificationNumber(parameter);
	}
	
	
	/**
	 * @description  회원가입 시, 개인정보 처리방안 url 리턴
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 처리결과
	 * @throws Exception
	 * @methodKorName 개인정보 처리방안 url 리턴
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@RequestMapping(value = "getUserInfoProcess.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getUserInfoProcess(RequestParameter parameter, ModelMap model) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		List<Object> list = mobileUserService.getUserInfoProcess(parameter);
		resMap.put("DATA", list);

		return resMap;
	}
	
}

