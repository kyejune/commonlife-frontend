package com.kolon.common.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kolon.common.helper.JedisHelper;
import com.kolonbenit.benitware.framework.xplaform.domain.ResultSetMap;
import com.kolonbenit.benitware.common.util.JwtUtil;
import com.kolonbenit.benitware.common.util.StringUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


	private final Logger logger = LoggerFactory.getLogger(MobileSessionCheckInterceptor.class);
	private static final JedisHelper helper = JedisHelper.getInstance();




	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String url = request.getRequestURI();


		String[] arrPermitUri = {"MobileUserController", "MobileUserCertNoController"};
		String[] arrLimitUri = {"mobileUserLogin", "mobileUserLogout", "mobileUserLoginConfirm"};

		System.out.println("URL : " + url);

		for (String sPermitUri : arrPermitUri) {
			if (url.indexOf(sPermitUri) > -1) {

				System.out.println("sPermitUri : " + sPermitUri);
//				for (String sLimitUri : arrLimitUri) {
//					if (url.indexOf(sLimitUri) > -1) {

//						System.out.println("sLimitUri : " + sLimitUri);
//						System.out.println("url.indexOf(sLimitUri) : " + url.indexOf(sLimitUri));
				System.out.println("패스");
				return true;
//					}
//				}
			}
		}





		Map<String, String> mHeader = getHeadersInfo(request);
		ObjectMapper mapper = new ObjectMapper();

		ResultSetMap resMap = new ResultSetMap();
		resMap.putNoLowerCase("LOGIN_YN", "N");
		String strResJson = new ObjectMapper().writeValueAsString(resMap);
		byte[] bResJson = strResJson.getBytes();
		String userId = "";



		/*********************************************************************/
		/* Token  임시로 DB에서 받아오기 */
		/*********************************************************************/
		String sToken = mHeader.get("token");
		String secretKey  ="";

		/*********************************************************************/
		/* Redis Token 게릿                                                                             */
		/*********************************************************************/
		Jedis jedis = null;
		String expireDate ="";
		String issueDate  ="";
		String tokenOrg   ="";

		try {

			logger.info("◆◆◆◆  Master  ◆◆◆◆");
			logger.info("Step 0. Redis Ready!");
			jedis = helper.getConnection();
			logger.info("Step 1. Redis Connected");
			String tokenString = jedis.get(sToken);
			logger.info("Step 2. Token");
//            logger.info("Token ({}) is...({})",sToken, tokenString);

			if (tokenString == null) {
				// Connection release
				helper.returnResource(jedis);

				logger.info("Token Out!!!" + sToken);
				response.setContentType("application/json");
				response.getOutputStream().write(bResJson);
				return false;
			}
			else {
				Gson gson = new Gson();
				JsonObject tokenRedis = gson.fromJson(tokenString, JsonObject.class);

				// Redis Token Value
				secretKey  = StringUtil.replace(tokenRedis.get("SECRET_KEY").toString(),"\"","");
				expireDate = StringUtil.replace(tokenRedis.get("EXPIRE_DATE").toString(),"\"","");
				issueDate  = StringUtil.replace(tokenRedis.get("ISSUE_DATE").toString(),"\"","");
				tokenOrg   = StringUtil.replace(tokenRedis.get("TOKEN_ORG").toString(),"\"","");
				userId     = StringUtil.replace(tokenRedis.get("USER_ID").toString(),"\"","");

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
			logger.info("Step 99. Redis Error!!");

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
			logger.info("(( {} )) =============> (( {} ))",secretKey, tokenOrg );

			// Connection release
			helper.returnResource(jedis);

			response.setContentType("application/json");
			response.getOutputStream().write(bResJson);
			return false;
//
		} else {
//			//logging
			logger.info("오케이 토큰 -------------> " + sToken );

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