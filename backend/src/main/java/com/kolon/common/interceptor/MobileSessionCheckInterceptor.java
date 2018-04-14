package com.kolon.common.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kolon.common.helper.JedisHelper;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
//import com.kolon.common.servlet.UserInfoHttpServletRequest;
import com.kolonbenit.benitware.framework.xplaform.domain.ResultSetMap;
import com.kolonbenit.benitware.common.util.JwtUtil;
import com.kolonbenit.benitware.common.util.StringUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.auth0.jwt.interfaces.DecodedJWT;
// 	import RequestParameter.java;
//	import ResultSetMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
// 	import com.iot.mobile.service.MobileDeviceService;
//	import com.iot.mobile.service.MobileUserService;
//	import com.kolonbenit.benitware.common.util.MobileSessionUtils;
//	import com.kolonbenit.benitware.common.util.StringUtil;

import redis.clients.jedis.Jedis;

/**
 * 모바일 세션 체크 인터셉터
 */

public class MobileSessionCheckInterceptor extends HandlerInterceptorAdapter {

	/* for DEVELOPMENT */
	private final static String FORCE_AUTH_TOKEN_YN = "forceAuthTokenYN";
	private final static String FORCE_AUTH_TOKEN = "forceAuthToken";
	private String forceAuthTokenYN = null;
	private String forceAuthToken = null;

	private final Logger logger = LoggerFactory.getLogger(MobileSessionCheckInterceptor.class);
	private static final JedisHelper helper = JedisHelper.getInstance();

	public void setForceAuthTokenYN(String forceAuthTokenYN) {
		this.forceAuthTokenYN = forceAuthTokenYN;
	}

	public void setForceAuthToken(String forceAuthToken) {
		this.forceAuthToken = forceAuthToken;
	}

	// 인증 없이 항상 접속이 허용됨 (token 체크 항상 안함)
	static String[] ALWAYS_PERMITTED_URI = {
			"/users/registration",    	// 회원 가입 Controller
			"/postFiles"				// Feed 파일 업로드/다운로드
	};

	// 인증 없이 접속 허용, 만약 인증토큰이 있다면 인증정보를 함께 Controller로 전달 (token이 있는 경우에 추가)
	static String[] PERMITTED_AND_AUTH_POPULATED_URI = {
			"/imageStore",    			// ImageStore
			"/users/"					// UserController
	};

	static String[] arrLimitUri = {
			"mobileUserLogin",
			"mobileUserLogout",
			"mobileUserLoginConfirm"
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String url = request.getRequestURI();
		
		// 1. 모든 OPTIONS 요청에 대해 api_key 체크하지 않음
		if ( request.getMethod().equals("OPTIONS") ) {
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}

		logger.debug("URL : " + url);

		for (String sPermitUri : ALWAYS_PERMITTED_URI) {
			if (url.indexOf(sPermitUri) > -1) {

				System.out.println("sPermitUri : " + sPermitUri);
//				for (String sLimitUri : arrLimitUri) {
//					if (url.indexOf(sLimitUri) > -1) {

//						System.out.println("sLimitUri : " + sLimitUri);
//						System.out.println("url.indexOf(sLimitUri) : " + url.indexOf(sLimitUri));
				System.out.println("토큰 체크 패스");
				return true;
//					}
//				}
			}
		}


		Map<String, String> mHeader = getHeadersInfo(request);
		ObjectMapper mapper = new ObjectMapper();

		ResultSetMap resMap = new ResultSetMap();
		resMap.putNoLowerCase("LOGIN_YN", "N");
		// ykim added
		resMap.put("msg", "로그인이 해제되었습니다. 다시 로그인 하세요.");  // todo: message 옮길 것
		response.setStatus( HttpStatus.UNAUTHORIZED.value() );
		//
		String strResJson = new ObjectMapper().writeValueAsString(resMap);
		byte[] bResJson = strResJson.getBytes();
		String userId = "";


		/*********************************************************************/
		/* Token  임시로 DB에서 받아오기 */
		/*********************************************************************/
		String sToken = mHeader.get("token");
		String secretKey  ="";

		// Header에 token이 없는 경우, 파라미터로 부터 token 값을 가져옴
		if( sToken == null ) {
			sToken = request.getParameter("token");
		}

		// TODO: 개발 디버깅 용도입니다. 개발 완료되면 삭제 할 것
		logger.debug("forceAuthTokenYN>>>> " + this.forceAuthTokenYN);
		logger.debug("forceAuthToken>>>> " + this.forceAuthToken);
		if( "Y".equals(this.forceAuthTokenYN) ) {
			logger.debug("===== ====== ====== ====== ======");
			logger.debug("===== DEVELOPEMENT MODE : Y =====");
			logger.debug("===== ====== ====== ====== ======");
			sToken = this.forceAuthToken;
		}


		/*********************************************************************/
		/* Redis Token 게릿                                                                             */
		/*********************************************************************/
		Jedis jedis = null;
		String expireDate ="";
		String issueDate  ="";
		String tokenOrg   ="";
		String cmplxId = "";
		String homeId = "";
		String userNm = "";
		String usrId = "";

		// PERMITTED_AND_AUTH_POPULATED_URI의 해당 값은 성공 처리
		if( sToken == null ) {
			for (String sPermitUri : PERMITTED_AND_AUTH_POPULATED_URI) {
				if (url.indexOf(sPermitUri) > -1) {
					logger.debug("Token out, request is accepted! " + sToken);
					response.setStatus( HttpStatus.OK.value() );
					return true;
				}
			}
		}

		try {

			logger.debug("◆◆◆◆  Master  ◆◆◆◆");
			logger.debug("Step 0. Redis Ready!");
			jedis = helper.getConnection();
			logger.debug("Step 1. Redis Connected");
			String tokenString = jedis.get(sToken);
			logger.debug("Step 2. Token");
//            logger.info("Token ({}) is...({})",sToken, tokenString);

            // token이 존재하지 않은 경우(로그인 되지 않은 경우), 체크
			if (tokenString == null) {
                // Connection release
				helper.returnResource(jedis);

				logger.debug("Token Out!!!" + sToken);
				response.setContentType("application/json");
				response.getOutputStream().write(bResJson);
				return false;
			}
			else {
				logger.debug("Step 3. Token exists in Redis!: " + tokenString);
				Gson gson = new Gson();
				JsonObject tokenRedis = gson.fromJson(tokenString, JsonObject.class);

				// Redis Token Value
				cmplxId = StringUtil.replace(tokenRedis.get("CMPLX_ID").toString(),"\"","");
				secretKey  = StringUtil.replace(tokenRedis.get("SECRET_KEY").toString(),"\"","");
				expireDate = StringUtil.replace(tokenRedis.get("EXPIRE_DATE").toString(),"\"","");
				issueDate  = StringUtil.replace(tokenRedis.get("ISSUE_DATE").toString(),"\"","");
				tokenOrg   = StringUtil.replace(tokenRedis.get("TOKEN_ORG").toString(),"\"","");
				homeId     = StringUtil.replace(tokenRedis.get("HOME_ID").toString(),"\"","");
				userId     = StringUtil.replace(tokenRedis.get("USER_ID").toString(),"\"","");
				userNm     = StringUtil.replace(tokenRedis.get("USER_NM").toString(),"\"","");
				usrId      = StringUtil.replace(tokenRedis.get("USR_ID").toString(),"\"","");

//	                ObjectMapper jmapper = new ObjectMapper(); // create once, reuse
//	                JsonNode actualObj  = jmapper.readValue(tokenString, JsonNode.class);
//	                logger.info("*************  SECRET_KEY {} ************** ",secretKey);
//	                logger.info("/EXPIRE_DATE {} ---",expireDate);
//	                logger.info("/ISSUE_DATE {} ---",issueDate);
//	                logger.info("/TOKEN_ORG {} ---",tokenOrg);
			}

			// Connection release
			helper.returnResource(jedis);
		}
		catch (Exception e) {
			logger.error( e.getMessage() );
			logger.debug("Step 99. Redis Error!!");

			// Connection release
			helper.returnResource(jedis);
			response.setContentType("application/json");
			response.getOutputStream().write(bResJson);

			return false;
		}


		/*********************************************************************/
		/* 3. Token 검증하기                                                                             */
		/*********************************************************************/
//        logger.info("Authorization -> secretKey --- {} ", secretKey); // secretKey.toString());
//        logger.info("Authorization -> tokenOrg  --- {} ", tokenOrg); //tokenOrg);

		DecodedJWT decJwt = JwtUtil.decodeJwt(secretKey, tokenOrg);

		if (decJwt == null) {
			logger.debug("(( {} )) =============> (( {} ))",secretKey, tokenOrg );

			// Connection release
			helper.returnResource(jedis);

			response.setContentType("application/json");
			response.getOutputStream().write(bResJson);
			return false;
//
		} else {
//			//logging
			logger.debug("오케이 토큰 -------------> " + sToken );

//			Map<String, Claim> claims = decJwt.getClaims();
//			for (Map.Entry<String, Claim> entry : claims.entrySet()) {
//				logger.info("\t claims key : {}, value : {}", entry.getKey(), entry.getValue().asString());
//			}
		}



		//아래 일단 막어

//		/*********************************************************************/
//		/* JWT AUTH */
//		/*********************************************************************/
////		logger.info("Header Informations-------------------------------------");
//		Map<String, String> mHeader = getHeadersInfo(request);
//		ObjectMapper mapper = new ObjectMapper();
//		ResultSetMap resMap = new ResultSetMap();
//		String strResJson = new ObjectMapper().writeValueAsString(resMap);
//		byte[] bResJson = strResJson.getBytes();
//
////		logger.info(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(mHeader));
////		logger.info("/Header Informations-------------------------------------");
//
//		// 1. Header에서 token가져오기
//		//    Token 값이 없으면 에러
//		String sToken = mHeader.get("token");
//		resMap.putNoLowerCase("LOGIN_YN", "N");
////		if(sToken == null || sToken.equals(""))
////		{
////			//sToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDM5MDY5NjgsIlVTRVJfSUQiOiJ5czMzIiwiaXNzIjoic21hcnRpb2sifQ.X5taYgrMtO0qzxPFiC9sRVmnuGyHw_mc021EfNszbeM";
////		}
//
//		if(sToken == null || sToken.equals("")) {
//			response.setContentType("application/json");
//			response.getOutputStream().write(bResJson);
//			return false;
//		}else{
//			// 토큰존재
//			logger.info("토큰이 존재해용 ~~~~~>");
//		}
//
//
//		// 2. Redis에서 값가져오기
//		Jedis jedis = null;
//		String secretKey  ="";
//		String expireDate ="";
//		String issueDate  ="";
//		String tokenOrg   ="";
//
//        try {
//            jedis = helper.getConnection();
//            String tokenString = jedis.get(sToken);
//
//            logger.info("토큰 {} 은 말이죠 {}",sToken, tokenString);
//
//            if (tokenString == null) {
//    			response.setContentType("application/json");
//    			response.getOutputStream().write(bResJson);
//    			return false;
//            }
//            else {
//	                Gson gson = new Gson();
//	                JsonObject tokenRedis = gson.fromJson(tokenString, JsonObject.class);
//
//            		// Redis Token Value
//	                secretKey  = StringUtil.replace(tokenRedis.get("SECRET_KEY").toString(),"\"","");
//	                expireDate = StringUtil.replace(tokenRedis.get("EXPIRE_DATE").toString(),"\"","");
//	                issueDate  = StringUtil.replace(tokenRedis.get("ISSUE_DATE").toString(),"\"","");
//	                tokenOrg   = StringUtil.replace(tokenRedis.get("TOKEN_ORG").toString(),"\"","");
//
////	                logger.info("/SECRET_KEY {} ---",secretKey);
////	                logger.info("/EXPIRE_DATE {} ---",expireDate);
////	                logger.info("/ISSUE_DATE {} ---",issueDate);
////	                logger.info("/TOKEN_ORG {} ---",tokenOrg);
//            }
//        }
//        catch (Exception e) {
//            helper.returnResource(jedis);
//			response.setContentType("application/json");
//			response.getOutputStream().write(bResJson);
//			return false;
//        }
//
//
//        // 3. Token 인증
////        logger.info("");
////        logger.info("");
////        logger.info("/secretKey --- {} ", "a"); // secretKey.toString());
////        logger.info("/tokenOrg  --- {} ", "b"); //tokenOrg);
//
//        DecodedJWT decJwt = JwtUtil.decodeJwt("1","1");//secretKey, tokenOrg);
//
//		if (decJwt == null) {
//			response.setContentType("application/json");
//			response.getOutputStream().write(bResJson);
//			return false;
//		} else {
//			//logging
////			Map<String, Claim> claims = decJwt.getClaims();
////
////			for (Map.Entry<String, Claim> entry : claims.entrySet()) {
////				logger.info("\t claims key : {}, value : {}", entry.getKey(), entry.getValue().asString());
////			}
//		}
//
//
//		/*********************************************************************/
//		/* Session Check */
//		/*********************************************************************/
//		ResultSetMap userInfo = null; //(ResultSetMap) MobileSessionUtils.getSessionUserInfo();
//
//		if(userInfo == null) {
////			response.setContentType("application/json");
////			response.getOutputStream().write(bResJson);
////			return false;
//		}else{
////			logger.info("<_____________ Session User Info " + MobileSessionUtils.getSessionId());
////			logger.info("<_____________ Session User Info " + userInfo);
//		}


		logger.debug("Step 4. put into AuthUserInfo");
		logger.debug("cmplxId: " + cmplxId);
		logger.debug("usrId: " + usrId);
		logger.debug("homeId: " + homeId);
		AuthUserInfo userInfo  = new AuthUserInfo(
				Integer.valueOf( cmplxId ),
				Integer.valueOf( usrId ),
				Integer.valueOf( homeId ),
				userId,
				userNm,
				tokenOrg,
				secretKey,
				issueDate,
				expireDate,
				null,
				null,
				null );
		AuthUserInfoUtil.setAuthUserInfo( request, userInfo );

		return true;

	}

	private Map<String, String> getHeadersInfo(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}
}