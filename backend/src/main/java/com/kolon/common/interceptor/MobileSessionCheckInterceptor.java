package com.kolon.common.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kolon.common.util.JwtUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Claim;
// 	import com.benitware.framework.http.parameter.RequestParameter;
//	import com.benitware.framework.xplaform.domain.ResultSetMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
// 	import com.iot.mobile.service.MobileDeviceService;
//	import com.iot.mobile.service.MobileUserService;
//	import com.kolonbenit.benitware.common.util.MobileSessionUtils;
//	import com.kolonbenit.benitware.common.util.StringUtil;
import com.kolon.common.helper.JedisHelper;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

/**
 * 모바일 세션 체크 인터셉터
 */
public class MobileSessionCheckInterceptor extends HandlerInterceptorAdapter {

	
	private final Logger logger = LoggerFactory.getLogger(getClass());	
	private static final JedisHelper helper = JedisHelper.getInstance();
	
	private static String NAMESPACE = "mobile.UserMapper.";
	
	@Autowired
//	private MobileUserService mobileUserService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String url = request.getRequestURI();
		Map<String, String> mHeader = getHeadersInfo(request);
		ObjectMapper mapper = new ObjectMapper();
		
//		ResultSetMap resMap = new ResultSetMap();
//		resMap.putNoLowerCase("LOGIN_YN", "N");
//		String strResJson = new ObjectMapper().writeValueAsString(resMap);
//		byte[] bResJson = strResJson.getBytes();
//		String userId = "";
		
		
//		logger.info("/Header Informations-----------------------((( {} )))",mapper.writer().withDefaultPrettyPrinter().writeValueAsString(mHeader));		
//		logger.info("URL ------------- " + url);

		
		
		/*********************************************************************/
		/* 예외처리                                                                                           */
		/*********************************************************************/	
		String[] arrPermitUri = {"MobilePushController"};
		String[] arrLimitUri  = {"sendPush.do"};

		
		for (String sPermitUri : arrPermitUri) {
			if (url.indexOf(sPermitUri) > -1) {
				for (String sLimitUri : arrLimitUri) {
					if (url.indexOf(sLimitUri) > -1) {
						System.out.println("패스");
						return true;
					}
				}
			}
		}
		
//		System.out.println("패스");
//		System.out.println("패스");
//		System.out.println("패스");
		//if(true) return true;
		
		/*********************************************************************/
		/* Token  임시로 DB에서 받아오기 */
		/*********************************************************************/		
		String sToken = mHeader.get("token");
		String secretKey  ="";		
		
//		logger.info("토큰이다 우하하하하 : " + sToken);
		if( sToken == null )
		{
			Map<String, Object> returnMap = new HashMap<>();
//			RequestParameter parameter = new RequestParameter();
//			parameter.put("userId", "ys33");
	
			returnMap = null;//mobileUserService.getUserToken(parameter);
//			logger.info("Step 4. " + url);
			if (returnMap == null) {
				
			}else{
			logger.info("returnMap" + returnMap);
				sToken    = returnMap.get("TOKEN_ENCRYPT").toString();
				secretKey = returnMap.get("SECRET_KEY").toString();
			}
		}
		
		/*********************************************************************/
		/* Token 체크                                                                                      */
		/*********************************************************************/
		// 1. Header에서 token가져오기
		//    Token 값이 없으면  쩟화면으로 이동ㅋㅋ
		if(sToken == null || sToken.equals("")) {
			logger.info("sToken URL ------------- " + url);	
			response.setContentType("application/json");
//			response.getOutputStream().write(bResJson);
			return false;
		}else{
			// 토큰존재
		}		
		
		
		
		/*********************************************************************/
		/* Redis Token 게릿                                                                             */
		/*********************************************************************/
		Jedis jedis = null;
		String expireDate ="";
		String issueDate  ="";
		String tokenOrg   ="";
		
        try {
        	
        	logger.info("Step 0. Redis Ready!");
            jedis = helper.getConnection();
            logger.info("Step 1. Redis Connected");
            String tokenString = jedis.get(sToken);
            logger.info("Step 2. Token");
//            logger.info("Token ({}) is...({})",sToken, tokenString);
            
            if (tokenString == null) {     
            	logger.info("Token Out!!!" + sToken);
            	// Connection release
            	//helper.destoryPool();
                helper.returnResource(jedis);                
    			response.setContentType("application/json");
//    			response.getOutputStream().write(bResJson);
    			return false;
            }
            else {
                Gson gson = new Gson();
                JsonObject tokenRedis = gson.fromJson(tokenString, JsonObject.class);

        		// Redis Token Value
//                secretKey  = StringUtil.replace(tokenRedis.get("SECRET_KEY").toString(),"\"","");
//                expireDate = StringUtil.replace(tokenRedis.get("EXPIRE_DATE").toString(),"\"","");
//                issueDate  = StringUtil.replace(tokenRedis.get("ISSUE_DATE").toString(),"\"","");
//                tokenOrg   = StringUtil.replace(tokenRedis.get("TOKEN_ORG").toString(),"\"","");
//                userId     = StringUtil.replace(tokenRedis.get("USER_ID").toString(),"\"","");
                
//	                ObjectMapper jmapper = new ObjectMapper(); // create once, reuse
//	                JsonNode actualObj  = jmapper.readValue(tokenString, JsonNode.class);	                		
//	                logger.info("*************  SECRET_KEY {} ************** ",secretKey);
//	                logger.info("/EXPIRE_DATE {} ---",expireDate);
//	                logger.info("/ISSUE_DATE {} ---",issueDate);
//	                logger.info("/TOKEN_ORG {} ---",tokenOrg);
	                
	            // Connection release
                ///helper.destoryPool();
	            helper.returnResource(jedis);
            }
        }
        catch (Exception e) {
        	logger.info("Step 99. Redis Error!!");
        	
        	// Connection release
        	//helper.destoryPool();
            helper.returnResource(jedis);
			response.setContentType("application/json");
//			response.getOutputStream().write(bResJson);

			return false;
        }        
        finally{
        	
        }


        /*********************************************************************/
		/* 3. Token 검증하기                                                                             */
		/*********************************************************************/
//        logger.info("Authorization -> secretKey --- {} ", secretKey); // secretKey.toString());
//        logger.info("Authorization -> tokenOrg  --- {} ", tokenOrg); //tokenOrg);
        
        DecodedJWT decJwt = JwtUtil.decodeJwt(secretKey, tokenOrg);
        
		if (decJwt == null) {
			
//			logger.info("Decode Error -------------> " + sToken );
//			// Connection Release
//	        // helper.returnResource(jedis);
//			//logger.info("(( {} )) =============> (( {} ))",secretKey, tokenOrg );
//			response.setContentType("application/json");
//			response.getOutputStream().write(bResJson);
			return false;
//			
		} else {
//			//logging
			// Connection Release
	        
			//logger.info("오케이 토큰 -------------> " + sToken );
			//helper.destoryPool();
			//helper.returnResource(jedis);
			
//			Map<String, Claim> claims = decJwt.getClaims();			
//			for (Map.Entry<String, Claim> entry : claims.entrySet()) {
//				logger.info("\t claims key : {}, value : {}", entry.getKey(), entry.getValue().asString());
//			}
		}


		/*********************************************************************/
		/* Session Check */
		/*********************************************************************/		
//		ResultSetMap userInfo = (ResultSetMap) MobileSessionUtils.getSessionUserInfo();
//
//		if(userInfo == null) {
//			ResultSetMap resMap = new ResultSetMap();
//			resMap.putNoLowerCase("LOGIN_YN", "N");
//
//			String strResJson = new ObjectMapper().writeValueAsString(resMap);
//
//			byte[] bResJson = strResJson.getBytes();
//
//			response.setContentType("application/json");
//			response.getOutputStream().write(bResJson);
////
//			return false;
//		}else{
//			logger.info("<_____________ Session User Info " + MobileSessionUtils.getSessionId());
//			logger.info("<_____________ Session User Info " + userInfo);
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