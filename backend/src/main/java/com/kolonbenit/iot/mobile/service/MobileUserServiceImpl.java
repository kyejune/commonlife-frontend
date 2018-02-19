package com.kolonbenit.iot.mobile.service;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
//import org.apache.poi.ss.formula.functions.Index;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import com.kolonbenit.benitware.framework.orm.mybatis.BaseIbatisDao;
import com.kolonbenit.benitware.framework.xplaform.domain.ResultSetMap;
//import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
//import com.iot.mobile.cvnet.ImageMovie;
import com.kolonbenit.benitware.common.util.CommandUtil;
import com.kolonbenit.benitware.common.util.FormattingUtil;
import com.kolonbenit.benitware.common.util.JwtUtil;
//import com.kolonbenit.benitware.common.util.MobileSessionUtils;
import com.kolonbenit.benitware.common.util.cipher.AESCipher;
import com.kolonbenit.benitware.common.util.cipher.HashFunctionCipherUtil;
//import com.kolonbenit.benitware.common.util.httpClient.HttpClientUtil;
import com.kolonbenit.benitware.common.util.push.FcmPushUtil;
//import com.kolonbenit.benitware.common.util.xml.XstreamConverterUtil;

//import iot.iot.netty.client.cvnet.telegram.image.vo.ImageMovieRes;
//import com.kolonbenit.benitware.common.util.mcache.JedisHelper;
//import com.kolonbenit.benitware.common.util.JwtUtil;
import redis.clients.jedis.Jedis;
import com.kolonbenit.benitware.common.util.token.KeyMaker;
import com.kolonbenit.benitware.common.util.token.TokenKey;
import com.kolonbenit.benitware.common.util.StringUtil;
import com.kolonbenit.benitware.common.util.httpClient.HttpClientUtil;

import redis.clients.jedis.Jedis;
import com.kolon.common.helper.JedisHelper;

@Service
public class MobileUserServiceImpl extends BaseIbatisDao<Object, Object> implements MobileUserService {

	/**
	 * Logger for this class and subclasses
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired @Qualifier("messagesProps")
	private Properties props;

	// cipher keydata
	@Value("#{applicationProps['cipher.keydata']}")
	public String keyData;

	private static String NAMESPACE = "mobile.UserMapper.";
	private final String CERTIFICATION_NUMBER_SESSION_KEY = "_CERT_NUM_";
	
	
	// Partner Site
	// 운영
	private static String PARTNER_URL          = "partner.smartiok.com";
	// 회원가입
	private static String CVNET_JOIN_PATH      = "/cvnet/registerUserId.do";
	// 아이디체크
	private static String CVNET_ID_CHECK_PATH  = "/cvnet/checkUserId.do";	
	// token 발행
	private static String CVNET_TOKEN_PATH     = "/cvnet/accessTokenRequest.do";
	// CVNET 로그인
	private static String CVNET_LOGIN_PATH     = "/cvnet/login.do";
	
	
	private final static String DATA_PATH = "data";
	private final static String RESULT_PATH = "result";
	
	
	// 개발
//	private static String PARTNER_URL = "dev-partner.smartiok.com";

	private static final JedisHelper helper = JedisHelper.getInstance();
	
	

	@Override
	public int modifyGcmRegInfoIntro(RequestParameter parameter) throws Exception {
		return this.updateBySqlId(NAMESPACE+"updateGcmRegInfoIntro", parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> mobileUserLogin(RequestParameter parameter) throws Exception {
		
		boolean isSuccess = false;
		String resMsg = "";	
		
		HashFunctionCipherUtil hf = new HashFunctionCipherUtil();
		AESCipher ac = new AESCipher();
		
		String strUserPw = parameter.getString("userPw").replaceAll(" ", "+");
		//String desResult = AESCipher.decodeAES(strUserPw, keyData);
		parameter.put("userPw", strUserPw);
		
		// deviceId, gcmRegId 없는 경우, 로그인 불가 처리로 변경됨
//		logger.info("Step 1.1 parameter.containsKey(deviceId)");
		if ( (parameter.containsKey("deviceId")) && !"".equals(parameter.getString("deviceId")) ) {
			isSuccess = true;
		} else {
			resMsg = props.getProperty("mobile.login.0005");
		}

		if (isSuccess) {
			isSuccess = false;
			if ( (parameter.containsKey("gcmRegId")) && !"".equals(parameter.getString("gcmRegId")) ) {
				isSuccess = true;
			} else {
				resMsg = props.getProperty("mobile.login.0006");
			}
		}

		Map<String, Object> homeIdMap = null;
		if (isSuccess) {
			logger.info("Step 1.2 (Map<String, Object>) this.getBySqlId(NAMESPACE+getHomeId, parameter) " + parameter.toString());
			homeIdMap = (Map<String, Object>) this.getBySqlId(NAMESPACE+"getHomeId", parameter);			

			if ( homeIdMap != null ) {
				//AWS 사용여부
				if("Y".equals((String)homeIdMap.get("AWS_USE_YN"))){				
				// 세대주가 입주 상태이고, 사용자 계정이 입주완료인 경우만 로그인 가능
					if ( "AD00901".equals((String) homeIdMap.get("STS")) && "AD00404".equals((String) homeIdMap.get("APRV_STS")) ) {
						isSuccess = true;
	
						HttpServletRequest request = parameter.getRequest();
						String sReqIp = request.getHeader("X-FORWARDED-FOR");
						if (sReqIp == null) {
							sReqIp = request.getRemoteAddr();
						}
						parameter.put("accIp", sReqIp);
	
						// 중복 로그인 처리					
	//					logger.info("Step 1.3 dup login Check");
						ResultSetMap loginMap = (ResultSetMap) this.getBySqlId(NAMESPACE+"getDuplicateLogin", parameter);					
						if ( loginMap != null ) {
							
							String sParamDeviceId = parameter.getString("deviceId");
							String sLogindedDeviceId = (String) loginMap.get("deviceId");
							
							logger.info("sParamDeviceId    : " + sParamDeviceId);
							logger.info("sLogindedDeviceId : " + sLogindedDeviceId);
							logger.info("loginMap          : " + loginMap.toString());							
	
							// 디바이스ID 비교
							if ( !sParamDeviceId.equals(sLogindedDeviceId) ) {
								// 푸시 메시지 구성
								ObjectNode json = this.setJsonMsg(parameter, loginMap);
	
								String regId = (String) loginMap.get("gcmRegId");
								String osType = (String) loginMap.get("osType");
	
								List<String> regIdList = new ArrayList<>();
								regIdList.add(regId);
	
	//							logger.info("=-=-=-=-=-= Push 시작 -=-=-=-=-=-=-=-=-=");
								// 기 로그인 되어 있는 폰에 푸시 발송
								logger.info("killing token : " + (String) loginMap.get("token"));
								tokenDestory((String) loginMap.get("token"));
								
								FcmPushUtil.sendPush(regIdList, json.toString(), osType);
	//							logger.info("=-=-=-=-=-= Push 아웃 -=-=-=-=-=-=-=-=-=");
	
								// 디바이스ID, GCM토큰이 항상 내려온다는 보장이 없어서 삭제 처리 후 진행
	//							logger.info("Step 1.4 this.deleteBySqlId(NAMESPACE+deleteGcmRegInfoLogout, parameter);");								
								this.deleteBySqlId(NAMESPACE+"deleteGcmRegInfoLogout", parameter);
							}
						}
	
						Map<String, String> claims = new HashMap<String, String>();
						claims.put("USER_ID", String.valueOf(homeIdMap.get("USER_ID")));					
						
					    // Time Create
						Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
						Calendar endCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
				        SimpleDateFormat sdf = new SimpleDateFormat();
				        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
				        
				        
				        // 120분 10초
				        endCal.add( Calendar.MINUTE, 24000 );  // 30분
				        endCal.add( Calendar.SECOND, 30 );  // 10초
//				        
				        // 120분 10초
//				        endCal.add( Calendar.MINUTE, 120 );  // 30분
//				        endCal.add( Calendar.SECOND, 30 );  // 10초
				        
				        // 30분 10초
//				        endCal.add( Calendar.MINUTE, 30 );  // 30분
//				        endCal.add( Calendar.SECOND, 10 );  // 10초
				        
				        // 10분 30초
//				        endCal.add( Calendar.MINUTE, 10 );
//				        endCal.add( Calendar.SECOND, 30 );
				        
				        String nowDate  = sdf.format(startCal.getTime());			        
				        String nextDate = sdf.format(endCal.getTime()) ;
				        
				        
				        logger.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				        logger.info("■ Token Time  :  {} ~  {}", nowDate, nextDate );
				        logger.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				        
				        
				        // Create Token.
				        long issueDate = System.currentTimeMillis() / 1000;	  
						KeyMaker tokenKey = new TokenKey(homeIdMap.get("USER_ID").toString(), issueDate);
						String secretKey  = tokenKey.getKey();
						
						
	//					logger.info("Start JWT Encode");
						String sEncJwt = JwtUtil.encodeJwt(secretKey, claims);
	//					logger.info("{}'s JWT : {}", homeIdMap.get("USER_ID"), sEncJwt);
						
						homeIdMap.put("TOKEN_ORG", sEncJwt);
						homeIdMap.put("SECRET_KEY" , secretKey);
						homeIdMap.put("ISSUE_DATE" , nowDate);
						homeIdMap.put("EXPIRE_DATE", nextDate);
						
						String[] sArrayJwt = StringUtil.split(sEncJwt, ".");
	
						
						
						
	//					logger.info("Step 2. ac.encodeAES(hf.hashingMD5(sArrayJwt[1]),keyData)");
						// Encoded "Not Encoded";//
						String encPayload = ac.encodeAES(hf.hashingMD5(sArrayJwt[1]),keyData);
						
						//JWT 처리
						parameter.put("secretKey",             secretKey);
						parameter.put("jsonWebToken",          sEncJwt);
						parameter.put("jsonWebTokenHeader",    sArrayJwt[0]);
						parameter.put("jsonWebTokenPayload",   sArrayJwt[1]);
						parameter.put("jsonWebTokenSignature", sArrayJwt[2]);
						parameter.put("jsonWebTokenEncrypt",   encPayload);
						parameter.put("jsonWebTokenIssueDt",   nowDate);
						parameter.put("jsonWebTokenValidDt",   nextDate);
						parameter.put("loginFlag", "Y");
						
						
						// JWT 
						parameter.put("jsonWebToken", sEncJwt);
						homeIdMap.put("TOKEN", encPayload);
						
						// 로그인,로그아웃 여부 처리		
	//					logger.info("Step 3. this.updateBySqlId(NAMESPACE+updateLoginYn, parameter); ");
						this.updateBySqlId(NAMESPACE+"updateLoginYn", parameter);
	
						// 로그인 내역 처리
	//					logger.info("Step 4. this.getBySqlId(NAMESPACE+insertUserInfoHist, parameter);");
						this.getBySqlId(NAMESPACE+"insertUserInfoHist", parameter);
						
						// 디바이스ID, GCM토큰 처리
	//					logger.info("Step 5. this.syncroPushRegKey(parameter, homeIdMap)");
						this.syncroPushRegKey(parameter, homeIdMap);					
						// Token 처리
						//this.prcssToken(homeIdMap);
	//					logger.info("step 6. this.tokenIssue(homeIdMap)");
						this.tokenIssue(homeIdMap);
						
						// Secret Key는
						homeIdMap.remove("SECRET_KEY");
						homeIdMap.remove("TOKEN_ORG");
						
					} else {
						isSuccess = false;
						resMsg = props.getProperty("mobile.login.0001");
					}
				}else{
					isSuccess = false;
					resMsg = props.getProperty("mobile.login.0008");
				}
				
			} else {
				isSuccess = false;
				resMsg = props.getProperty("mobile.login.0002");
			}
		}

		if (isSuccess) {
			homeIdMap.remove("STS");
			homeIdMap.remove("APRV_STS");
			homeIdMap.remove("LOGIN_YN");
			homeIdMap.put("resFlag", isSuccess);
		} else {
			homeIdMap = new HashMap<>();
			homeIdMap.put("resFlag", isSuccess);
			homeIdMap.put("MSG", resMsg);
		}
		return homeIdMap;
	}

	
	
	
	/**
	 * @description  Token을 발행한다. (로그인 세션 유지)                         
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 상태결과
	 * @throws Exception
	 * @methodKorName 모바일사용자로그인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 */
	@Override
	public void tokenIssue(Map<String, Object> phomeIdMap) throws Exception {
		// TODO Auto-generated method stub
		Jedis jedis = null;
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode userToken = mapper.createObjectNode();
			ObjectNode detailToken = mapper.createObjectNode();
			ObjectNode authListToken = mapper.createObjectNode();
			
			final long justTenMinute = 60 * 10 * 300;
//			int limitTenMinute = 60 * 10 + 30; // 10분 30초
//			int limitTenMinute = 60 * 30 + 10; // 30분 10초
//			int limitTenMinute = 60 * 120 + 10; // 120분 10초
			int limitTenMinute = 60 * 2400 * 10 + 10; // 600분 10초
	        long issueDate = System.currentTimeMillis() / 1000;	        
	
//	        logger.info("Step 1. helper.getConnection() ");
	        // token용 랜덤키 만들기.
	        
	        logger.info("Step A-1 Redis Ready ");
	        jedis = helper.getConnection();
	        logger.info("Step B-1 Redis Connected ");
	        
	        // Token List
	        detailToken.put("CMPLX_ID", phomeIdMap.get("CMPLX_ID").toString());
	        detailToken.put("USER_ID", phomeIdMap.get("USER_ID").toString());
	        detailToken.put("TOKEN_ORG", phomeIdMap.get("TOKEN_ORG").toString());
	        detailToken.put("SECRET_KEY", phomeIdMap.get("SECRET_KEY").toString());
	        detailToken.put("ISSUE_DATE", phomeIdMap.get("ISSUE_DATE").toString());
	        detailToken.put("EXPIRE_DATE", phomeIdMap.get("EXPIRE_DATE").toString());


	        // CommonLife 용도, 확장 정보
			detailToken.put("USR_ID", "123");
			detailToken.put("CMPLX_GRP_TYPE_ID", "123");
			detailToken.put("HEAD_ID", "123");
	        
	        // Token Auth List
	        authListToken.put("DEFAULT","Y");
	        authListToken.put("USER_INFO","Y");
	        authListToken.put("SYSTEM","Y");	        
	        detailToken.set("AUTHORIZATIONS", authListToken);	        
	        
//	        logger.info("Step 2. Redis Save ");
	        // 이놈은 사용을 안합니다.ㅋㅋㅋㅋㅋㅋㅋㅋㅋ	        
	        logger.info("Step C. Before Redis ");
	        logger.info("phomeIdMap.get(TOKEN).toString() : " + phomeIdMap.get("TOKEN").toString());
//	        jedis.setex("a", limitTenMinute, "What time is it now? " + phomeIdMap.get("ISSUE_DATE").toString());
	        jedis.setex(phomeIdMap.get("TOKEN").toString(), limitTenMinute, detailToken.toString());
	        //jedis.psetex(key, milliseconds, value)
//	        jedis.setex(phomeIdMap.get("TOKEN").toString(), limitTenMinute, userToken.toString());
	        logger.info("Step D. After Redis ");
	        
	        // Connection release
            helper.returnResource(jedis);
	        
		}
        catch (Exception e) {
            helper.returnResource(jedis);    
//            logger.info("ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ 레디스 저장 앙대또열");
            logger.info(e.getMessage());
            logger.info("Step ◆◆◆◆◆◆◆◆◆◆  Redis Error ◆◆◆◆◆◆◆◆◆◆ ");
            
        }
	}
	
	
	
	
	/**
	 * @description  Token을 업데이트한다. (로그인 세션 유지)
	 *               Case 1 :            
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 상태결과
	 * @throws Exception
	 * @methodKorName 모바일사용자로그인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Map<String, Object> tokenUpdate(RequestParameter parameter) throws Exception {
		// TODO Auto-generated method stub
		
		Jedis jedis = null;
		Map<String, Object> rtnMap     = new HashMap();
		Map<String, Object> paramToken = new HashMap();		
		
		String sToken = parameter.getString("token");
		String secretKey ="";
		String expireDate ="";		
		String tokenOrg   ="";
		String cmplxId    ="";
		String userId = "";
		long issueDate;
		
		// Time Create
		Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
		Calendar endCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        
        // 30분 30초
//        endCal.add( Calendar.MINUTE, 30 );
//        endCal.add( Calendar.SECOND, 10 );
        
        // 두시간 30분
//        endCal.add( Calendar.MINUTE, 120 );
//        endCal.add( Calendar.SECOND, 30 );
        
        endCal.add( Calendar.MINUTE, 24000 );
        endCal.add( Calendar.SECOND, 30 );
        
//        endCal.add( Calendar.MINUTE, 10 );
//        endCal.add( Calendar.SECOND, 30 );
        
        String nowDate  = sdf.format(startCal.getTime());			        
        String nextDate = sdf.format(endCal.getTime()) ;
        
        issueDate = System.currentTimeMillis() / 1000;
        rtnMap.put("result", false);
                
        logger.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        logger.info("■ Update Token Time  :  {} ~  {}", nowDate, nextDate );
        logger.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
		if (sToken == null){			
			return rtnMap;
		}

		
        try {        	
        	logger.info("Update 0. Redis Ready!");
            jedis = helper.getConnection();
            logger.info("Update 1. Redis Connected");
            String tokenString = jedis.get(sToken);
            logger.info("Update 2. Token");
            
            if (tokenString == null) {
            	helper.returnResource(jedis);
    			return rtnMap;
            }
            else {
	                Gson gson = new Gson();
	                JsonObject tokenRedis = gson.fromJson(tokenString, JsonObject.class);

            		// Redis Token Value
	                userId  = StringUtil.replace(tokenRedis.get("USER_ID").toString(),"\"","");
	                cmplxId = StringUtil.replace(tokenRedis.get("CMPLX_ID").toString(),"\"","");
            }
           
            
            //================================
            // Hash Secret Key 값 가져오기
            //================================
            Map<String, String> claims = new HashMap<String, String>();
    		claims.put("USER_ID", userId);
            
            // Create Token.			  
    		KeyMaker tokenKey = new TokenKey(userId, issueDate);
    		secretKey  = tokenKey.getKey();		
    		
    		// logger.info("Start JWT Encode");
    		String sEncJwt = JwtUtil.encodeJwt(secretKey, claims);
    		String[] sArrayJwt = StringUtil.split(sEncJwt, ".");
    		
    		//================================
            // DB Update
            //================================
    		parameter.put("loginFlag",              "Y");
			parameter.put("secretKey",              secretKey);
			parameter.put("jsonWebToken",           sEncJwt);
			parameter.put("jsonWebTokenHeader",     sArrayJwt[0]);
			parameter.put("jsonWebTokenPayload",    sArrayJwt[1]);
			parameter.put("jsonWebTokenSignature",  sArrayJwt[2]);
			parameter.put("jsonWebTokenIssueDt",    nowDate);
			parameter.put("jsonWebTokenValidDt",    nextDate);
			parameter.put("jsonWebTokenEncrypt",    sToken);
			parameter.put("userId",                 userId);
			
			this.updateBySqlId(NAMESPACE+"updateLoginYn", parameter);			
			
    		//================================
            // Token Detail Setting
            //================================
	        paramToken.put("CMPLX_ID",    cmplxId);
	        paramToken.put("USER_ID",     userId);
	        paramToken.put("TOKEN",       sToken);
	        paramToken.put("TOKEN_ORG",   sEncJwt);
	        paramToken.put("SECRET_KEY",  secretKey);
	        paramToken.put("ISSUE_DATE",  nowDate);
	        paramToken.put("EXPIRE_DATE", nextDate);
	        
	        // Token 저장
	        this.tokenIssue(paramToken);
            
            // Connection release
            helper.returnResource(jedis);
            
        }
        catch (Exception e) {
        	logger.info("Step 99. Redis Error!!");
        	
        	// Connection release
            helper.returnResource(jedis);			
			return rtnMap;
        }
        
        rtnMap.put("result", true);
		return rtnMap;
	}

	


	
	/**
	 * @description  Token을 검증한다. (로그인 세션 유지)
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 상태결과
	 * @throws Exception
	 * @methodKorName 모바일사용자로그인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 */
	@Override
	public Map<String, Object> tokenVerify(RequestParameter parameter) throws Exception {
		// TODO Auto-generated method stub
		
		 Jedis jedis = null;
        try {
            jedis = helper.getConnection();
            String tokenString = jedis.get("token");

            if (tokenString == null) {         
            }
            else {
//                Gson gson = new Gson();
//                JsonObject token = gson.fromJson(tokenString, JsonObject.class);
                // helper.                
            }

        }
        catch (Exception e) {
            helper.returnResource(jedis);
        }
		
		return null;
	}








	




	/**
	 * @description  Token을 삭제한다. (로그인 세션 유지)                         
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 상태결과
	 * @throws Exception
	 * @methodKorName 모바일사용자로그인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 */
	@Override
	public String tokenDestory(String pToken) throws Exception {
		// TODO Auto-generated method stub
		String sResult = "Killed";
		
		Jedis jedis = null;
        try {
            // token 삭제
            jedis = helper.getConnection();
            long result = jedis.del(pToken);
            System.out.println("Delete Result : "  + result);
            helper.returnResource(jedis);
            // helper.            
        }
        catch (Exception e) {
        	sResult = "Error";
            helper.returnResource(jedis);
        }
		return null;
	}

	
	
	

	@Override
	public Map<String, Object> mobileUserLoginConfirm(RequestParameter parameter) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		boolean isSuccess = true;
		String resMsg = "";
		String sType = "";

		ResultSetMap loginMap = (ResultSetMap) this.getBySqlId(NAMESPACE+"getDuplicateLogin", parameter);
		logger.info("#### parameter : " + parameter);
//		logger.info("#### parameter : " + loginMap.toString());
		if ( loginMap != null ) {
			logger.info("#### loginMap : " + loginMap.toString());
			logger.info("step 1. ");
			if ( parameter.containsKey("userPw") ) {
				logger.info("step 2. ");				
				logger.info("RAW userPw:"+parameter.getString("userPw"));
				
				String strUserPw = parameter.getString("userPw").replaceAll(" ", "+");
				/*String desResult = AESCipher.decodeAES(strUserPw, keyData);*/
				
				logger.info("Replace userPw:"+strUserPw);
				
				String sUserPw = (String) loginMap.get("userPw");
				// 패스워드 비교
				if ( !strUserPw.equals(sUserPw) ) {
					isSuccess = false;
					resMsg = props.getProperty("mobile.login.0002");
					sType = "002";
				}
			}

			logger.info("step 3. ");
			if (isSuccess) {
				logger.info("step 4. ");
				String sParamDeviceId = parameter.getString("deviceId");
				String sLogindedDeviceId = (String) loginMap.get("deviceId");

				// 디바이스ID 비교
				logger.info("step 5. ");
				if ( !sParamDeviceId.equals(sLogindedDeviceId) ) {
					isSuccess = false;
					resMsg = props.getProperty("mobile.login.0003");
					sType = "003";
				}
			}
		}

		logger.info("step 6. ");
		resMap.put("resFlag", isSuccess);
		if ( !isSuccess ) {
			resMap.put("MSG", resMsg);
			resMap.put("resType", sType);
		}
		return resMap;
	}

	/**
	 * @description  푸시 메시지 구성을 처리한다.
	 * @param RequestParameter parameter, ResultSetMap loginMap
	 * @return ObjectNode json 처리결과
	 * @methodKorName 푸시메시지구성
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ObjectNode setJsonMsg(RequestParameter parameter, ResultSetMap loginMap) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();

		json.put("title", props.getProperty("mobile.push.title.login"));
		json.put("img_src", "");
		json.put("message", props.getProperty("mobile.login.0004"));
		json.put("button_type", "");
		json.put("cmplxId", Integer.toString((int) loginMap.get("cmplxId")) );
		json.put("homeId", Integer.toString((int) loginMap.get("homeId")) );
		json.put("aplMode", "");	// 생활모드 시에만 값 세팅
		json.put("returnYN", "");	// 비상푸시 시에만 값 세팅
		json.put("proc", "logout");

		return json;
	}
	
	
	

	@Override
	public boolean mobileUserLogout(RequestParameter parameter) throws Exception {
		boolean isSuccess = false;

		int nCnt = 0;
		try {
			
			String sLoginFlag = parameter.getString("loginFlag");
			
			if (StringUtils.isBlank(sLoginFlag)) {
				sLoginFlag = "N";
			}
			
			parameter.put("loginFlag", sLoginFlag);

			nCnt = this.updateBySqlId(NAMESPACE+"updateLoginYn", parameter);
			if (nCnt > 0 && "N".equals(sLoginFlag)) {
				this.deleteBySqlId(NAMESPACE+"deleteGcmRegInfoLogout", parameter);
				isSuccess = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	
	@Override
	public Map<String, Object> mobileUserLoginStatus(RequestParameter parameter) throws Exception {
		
		Map<String, Object> resMap = new HashMap<>();

		String resMsg = "";
		String sType = "";

		logger.info("parameter:"+parameter.toString());
		
		ResultSetMap loginMap = (ResultSetMap) this.getBySqlId(NAMESPACE+"getDuplicateLogin", parameter);

		if (loginMap != null) {
			
			String sLoginYn = String.valueOf(loginMap.get("loginYn"));
			
			if ("Y".equals(sLoginYn)) {
				resMsg = props.getProperty("mobile.login.status.0001");
				sType = "0001";
			} else if ("N".equals(sLoginYn)) {
				resMsg = props.getProperty("mobile.login.status.0002");
				sType = "0002";
			} else if ("A".equals(sLoginYn)) {
				resMsg = props.getProperty("mobile.login.status.0003");
				sType = "0003";
			} else {
				resMsg = props.getProperty("mobile.login.status.0004");
				sType = "0004";
			}
		} else {
			resMsg = props.getProperty("mobile.login.status.0004");
			sType = "0005";
		}

		resMap.put("MSG", resMsg);
		resMap.put("resType", sType);
			
		return resMap;
	}
	
	
	/**
	 * @description  푸시 토큰 동기화를 처리한다.
	 * @param RequestParameter parameter, Map<String, Object> homeIdMap
	 * @methodKorName 푸시토큰동기화
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
//	@Transactional(propagation= Propagation.REQUIRED, rollbackFor={Exception.class})
	private void syncroPushRegKey(RequestParameter parameter, Map<String, Object> homeIdMap) {
		if ( (parameter.containsKey("gcmRegId") && parameter.containsKey("deviceId")) ) {
			if ( (!"".equals(parameter.getString("gcmRegId")) && !"".equals(parameter.getString("deviceId"))) ) {

				parameter.put("cmplxId", homeIdMap.get("CMPLX_ID"));
				parameter.put("homeId", homeIdMap.get("HOME_ID"));

				ResultSetMap gcmRegInfo = (ResultSetMap) this.getBySqlId(NAMESPACE+"getGcmRegInfo", parameter);
				if (gcmRegInfo != null) {
					String sParamGcmRegId = parameter.getString("gcmRegId");
					String sParamDeviceId = parameter.getString("deviceId");

					String sMapGcmRegId = (String) gcmRegInfo.get("gcmRegId");
					String sMapDeviceId = (String) gcmRegInfo.get("deviceId");

					if ( (!sMapGcmRegId.equals(sParamGcmRegId)) || (!sMapDeviceId.equals(sParamDeviceId)) ) {
						this.updateBySqlId(NAMESPACE+"updateGcmRegInfo", parameter);
					}
				} else {
					this.insertBySqlId(NAMESPACE+"insertGcmRegInfo", parameter);
				}

				this.deleteBySqlId(NAMESPACE+"deleteGcmRegInfo", parameter);
				this.deleteBySqlId(NAMESPACE+"deleteGcmRegInfo2", parameter);
			}
		}
	}

	@Override
	public List<Object> listComplexInfo(RequestParameter parameter) throws Exception {
		return this.listBySqlId(NAMESPACE+"listComplexInfo", parameter);
	}

	@Override
	public List<Object> listDongInfo(RequestParameter parameter) throws Exception {
		return this.listBySqlId(NAMESPACE+"listDongInfo", parameter);
	}

	@Override
	public List<Object> listHoInfo(RequestParameter parameter) throws Exception {
		return this.listBySqlId(NAMESPACE+"listHoInfo", parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> checkUserId(RequestParameter parameter) throws Exception {
		return (Map<String, Object>) this.getBySqlId(NAMESPACE+"checkUserId", parameter);
	}

	@Override
	public Map<String, Object> searchUserId(RequestParameter parameter) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		String strOrgUserCell = parameter.getString("userCell");
		String strOrgHeadCell = parameter.getString("headCell");
		
		String strOrgCell = "";
		
		String strUserCell = FormattingUtil.cellPhoneNumHyphen(strOrgUserCell);
		String strHeadCell = FormattingUtil.cellPhoneNumHyphen(strOrgHeadCell);
		
		//String strCell = FormattingUtil.cellPhoneNumHyphen(strOrgCell);
		parameter.put("userCell", strUserCell);
		parameter.put("headCell", strHeadCell);

		List<Object> resList = this.listBySqlId(NAMESPACE+"searchUserId", parameter);
		if (resList != null) {
			int nSize = resList.size();
			String sUserId = "";

			StringBuffer sb = new StringBuffer();
			sb.append(props.getProperty("mobile.send.sms.search.id")).append(" ");
			for (int i=0; i < nSize; i++) {
				sUserId = (String) resList.get(i);

				if (i < resList.size()-1) {
					sb.append("ID : ").append(sUserId).append(", ");
				} else {
					sb.append("ID : ").append(sUserId);
				}
			}
			String message = sb.toString();

			CommandUtil.runCommandSMS(strUserCell, message);
		}
		return resMap;
	}

	@Override
	public Map<String, Object> searchUserPw(RequestParameter parameter) throws Exception {
		
		String strOrgUserCell = parameter.getString("userCell");
		String strOrgHeadCell = parameter.getString("headCell");
		
		String strOrgCell = "";
				
		//String strCell = FormattingUtil.cellPhoneNumHyphen(strOrgCell);
		
		String strUserCell = FormattingUtil.cellPhoneNumHyphen(strOrgUserCell);
		String strHeadCell = FormattingUtil.cellPhoneNumHyphen(strOrgHeadCell);
				
		//parameter.put("cell", strCell);
		
		parameter.put("userCell", strUserCell);
		parameter.put("headCell", strHeadCell);
		
		String strTempPlainPw = RandomStringUtils.randomAlphanumeric(6).toLowerCase();
		String strTempHashPw = HashFunctionCipherUtil.hashingMD5(strTempPlainPw);
		
		String desResult = AESCipher.encodeAES(strTempHashPw, keyData);
		
		parameter.put("tempPw", desResult);

		Map<String, Object> resMap = null;

		int nCnt = this.updateBySqlId(NAMESPACE+"updateUserTempPw", parameter);
		if (nCnt > 0) {
			resMap = new HashMap<>();
			resMap.put("USER_PW", strTempPlainPw);

			String msg = props.getProperty("mobile.send.sms.search.pwd");
			String message = MessageFormat.format(msg, strTempPlainPw);

			CommandUtil.runCommandSMS(strUserCell, message);
		}
		return resMap;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	@Override
	public Map<String, Object> registerMember(RequestParameter parameter) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		String strOrgUserCell = parameter.getString("userCell");
		String strOrgHeadCell = parameter.getString("headCell");
		
		//String strOrgCell = parameter.getString("userCell");
		//String strCell = FormattingUtil.cellPhoneNumHyphen(strOrgCell);
		
		String strUserCell = FormattingUtil.cellPhoneNumHyphen(strOrgUserCell);
		String strHeadCell = FormattingUtil.cellPhoneNumHyphen(strOrgHeadCell);
		
		String strUserPw = parameter.getString("userPw").replaceAll(" ", "+");
		//String desResult = AESCipher.decodeAES(strUserPw, keyData);
		parameter.put("userPw", strUserPw);
		
		parameter.put("userCell", strUserCell);
		parameter.put("headCell", strHeadCell);

		String resMsg = "";
		boolean isSuccess = false;
		

		// 1. 선택한 단지에 해당 동,호가 존재하는지 체크
		ResultSetMap checkDongHoMap = (ResultSetMap) this.getBySqlId(NAMESPACE+"checkDongHo", parameter);
		if (checkDongHoMap != null) {
			isSuccess = true;
			
//			if(checkDongHoMap.get("gwcd").toString().equalsIgnoreCase("cvnet")){
//				// Partner CV.Net 가입연동
//				
//				String rtnjson = HttpClientUtil.post(PARTNER_URL + CVNET_JOIN_PATH, parameter);				
//				if (rtnjson == null){					
//					isSuccess = false;
//				}else{
//					//
//					ObjectMapper mapper = new ObjectMapper();		
//					JsonNode callbackNode = mapper.readTree(rtnjson);
//					
//					
//					//JsonNode cvnetDataNode = callbackNode.path(DATA_PATH);
//					JsonNode cvnetResultNode = callbackNode.path(RESULT_PATH);
//					
//					if(cvnetResultNode.get("message").asText().equalsIgnoreCase("ok")){
//						isSuccess = true;
//					}else{
//						isSuccess = false;
//					}
//				}				
//			}
		}

		
		
		if (isSuccess) {
			// 2. 선택한 단지의 해당 동,호의 가입자 수 체크
			int nCheckMemCnt = (int) this.getBySqlId(NAMESPACE+"checkHomeId", parameter);
			if (nCheckMemCnt >= 4) {
				isSuccess = false;
			}

			if (isSuccess) {
				// 3. 세대주가 신규인지 기가입자인지 체크
				int nCheckRegCnt = (int) this.getBySqlId(NAMESPACE+"checkNewUserId", parameter);
				int nHeadId = 0;
				switch (nCheckRegCnt) {
				case 0:
					// 4-1. 세대주 정보 체크 (신규 가입자)
					nHeadId = (int) this.getBySqlId(NAMESPACE+"checkHomeHeadNew", parameter);
					if (nHeadId == 0) {
						isSuccess = false;
					}
					break;
				default:
					// 4-2. 세대주 정보 체크 (기 가입 세대주가 있는 경우)
					nHeadId = (int) this.getBySqlId(NAMESPACE+"checkHomeHead", parameter);
					if (nHeadId == 0) {
						isSuccess = false;
					}
					break;
				}

				if (isSuccess) {
					parameter.put("headId", nHeadId);
					// 5. 사용자정보 테이블 등록
					this.insertBySqlId(NAMESPACE+"registerMember", parameter);

					// 6. 계약정보 테이블 등록
					this.insertBySqlId(NAMESPACE+"insertContM", parameter);
					
					//초기 푸시설정
					this.insertBySqlId(NAMESPACE+"insertInitPush", parameter);

					resMsg = props.getProperty("mobile.common.success");
				} else {
					resMsg = props.getProperty("mobile.common.not.found");
				}

			} else {
				resMsg = props.getProperty("mobile.register.member.0001");
			}

		} else {
			resMsg = props.getProperty("mobile.register.member.0002");
		}

		resMap.put("resFlag", isSuccess);
		resMap.put("MSG", resMsg);
		return resMap;
	}

	@Override
	public Map<String, Object> sendCertificationNumber(RequestParameter parameter) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		boolean isHeadSuccess = false;
		boolean isMemberSuccess = false;
		boolean rtnSuccess = false;
		String resMsg = "";

		String strOrgHeadCell = parameter.getString("headCell");
		String strOrgUserCell = parameter.getString("userCell");
		
		String strOrgCell = "";
		
		String strHeadCell = FormattingUtil.cellPhoneNumHyphen(strOrgHeadCell);
		String strUserCell = FormattingUtil.cellPhoneNumHyphen(strOrgUserCell);
		
		String strCell = FormattingUtil.cellPhoneNumHyphen(strOrgCell);
		//parameter.put("cell", strCell);
		parameter.put("headCell", strHeadCell);
		parameter.put("userCell", strUserCell);
		
		String certType =  parameter.getString("certType");

		if ( !parameter.containsKey("userId") ) {
			parameter.put("userId", "");
		}

		if ( !parameter.containsKey("cmplxId") ) {
			parameter.put("cmplxId", "");
		}
		if ( !parameter.containsKey("dong") ) {
			parameter.put("dong", "");
		}
		if ( !parameter.containsKey("ho") ) {
			parameter.put("ho", "");
		}

		// ID 찾기, PW 찾기, 회원가입
		ResultSetMap checkHeadMap = null; //(ResultSetMap) this.getBySqlId(NAMESPACE+"checkMember" + certType, parameter);
		ResultSetMap checkMemberMap = null;
		
		//회원가입시
		if(certType.equals("1")){
			strCell = strHeadCell;
			checkHeadMap = (ResultSetMap) this.getBySqlId(NAMESPACE+"checkMember" , parameter);	//동호/단지에 해당하는 세대주 정보
			isMemberSuccess = true;	//회원 가입시에는 사용자정보 체크가 필요없어 무조건 true;
		}else{	//
			strCell = strUserCell;
			checkHeadMap = (ResultSetMap) this.getBySqlId(NAMESPACE+"checkHead", parameter);	//이름 ,전화번호로만 세대주 정보 확인
			checkMemberMap = (ResultSetMap) this.getBySqlId(NAMESPACE+"checkMember" + certType, parameter);	//사용자 정보 존재확인
			
			if (checkMemberMap != null) {
				isMemberSuccess = true;
			}
		}
		
		if (checkHeadMap != null) {
			isHeadSuccess = true;
		}

		if (isHeadSuccess) {
			
			if(isMemberSuccess){
				String sCertNum = RandomStringUtils.randomNumeric(6).toString();
				Date expireDate = DateUtils.addMinutes(new Date(), 2);
	
				Map<String, Object> certMap = new HashMap<>();
				certMap.put("certNum", sCertNum);
				certMap.put("expireDate", expireDate);
	
				//MobileSessionUtils.setSessionObject(this.CERTIFICATION_NUMBER_SESSION_KEY, certMap, 0);
				//2017.11.20 인증번호 세션관리에서 DB관리로 변경함(서버 2대로 운영시 센션 공유 문제발생)
				
				parameter.put("certNo", sCertNum);
				parameter.put("certDt", expireDate);
				
				int nCheckCertM = (int) this.getBySqlId(NAMESPACE+"countUserCertM", parameter);
								
				if(nCheckCertM > 0){
					this.insertBySqlId(NAMESPACE+"updateUserCertM", parameter);
				}else{
					this.insertBySqlId(NAMESPACE+"insertUserCertM", parameter);
				}
					
				
				String msg = props.getProperty("mobile.send.sms.cert.num");
	
				String sRegistNm = parameter.getString("userNm");
				if (sRegistNm != null && !"".equals(sRegistNm)) {
					msg += " [" +sRegistNm+ "]";
				}
				
				String sMessage = MessageFormat.format(msg, sCertNum);
				
				try {
					CommandUtil.runCommandSMS(strCell, sMessage);
					resMsg = props.getProperty("mobile.send.cert.0001");	//인증번호가 SMS로 발송되었습니다.
					rtnSuccess = true;
				} catch (Exception e) {
					isHeadSuccess = false;
					resMsg = props.getProperty("mobile.send.cert.0002");	//인증번호 발송 처리중 오류가 발생했습니다.
				}
								
			}else{
				resMsg = props.getProperty("mobile.send.cert.0003");		//사용자 정보가 일치하지 않습니다.	
			}
		} else {
			resMsg = props.getProperty("mobile.common.not.found");			//세대주 정보가 존재하지 않습니다.
		}
			
		resMap.put("resFlag", rtnSuccess);
		resMap.put("MSG", resMsg);
		return resMap;
	}

	
	
	@Override
	public Map<String, Object> confirmCertificationNumber(RequestParameter parameter) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		boolean isSuccess = false;
		String sMessage = "";

		//@SuppressWarnings("unchecked")
		//Map<String, Object> certMap = (Map<String, Object>) MobileSessionUtils.getSessionObject(this.CERTIFICATION_NUMBER_SESSION_KEY);

		String strOrgHeadCell = parameter.getString("headCell");
		String strOrgUserCell = parameter.getString("userCell");

		String strHeadCell = FormattingUtil.cellPhoneNumHyphen(strOrgHeadCell);
		String strUserCell = FormattingUtil.cellPhoneNumHyphen(strOrgUserCell);

		//parameter.put("cell", strCell);
		parameter.put("headCell", strHeadCell);
		parameter.put("userCell", strUserCell);
		
		Map<String, Object> certMap =(Map<String, Object>)this.getBySqlId(NAMESPACE+"getUserCertM", parameter);
		
		if (certMap != null) {
			isSuccess = true;
			
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> isSuccess" + isSuccess);
		}

		if (isSuccess) {
			//String sOrgCertNum = (String) certMap.get("certNum");
			//Date expireDate = (Date) certMap.get("expireDate");
			
			String sOrgCertNum = (String) certMap.get("CERT_NO");
			Date expireDate = (Date) certMap.get("CERT_DT");

			String sCertNum = parameter.getString("certNum");
			Date date = new Date();
			int nCompare = date.compareTo(expireDate);

			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> nCompare" + nCompare);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sOrgCertNum" + sOrgCertNum);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sCertNum" + sCertNum);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> expireDate" + expireDate);
			
			
			if ( sCertNum.equals(sOrgCertNum) ) {
				//MobileSessionUtils.removeSessionObject(this.CERTIFICATION_NUMBER_SESSION_KEY);
				this.deleteBySqlId(NAMESPACE+"deleteUserCertM", parameter);
				isSuccess = true;
				String sSmsChkDt = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
				if ( nCompare <= 0 ) {
					sMessage = props.getProperty("mobile.confirm.cert.0001");
					resMap.put("smsChkDt", sSmsChkDt);
				} else {
					isSuccess = false;
					sMessage = props.getProperty("mobile.confirm.cert.0003");
				}
				
				
			} else {
				isSuccess = false;
				sMessage = props.getProperty("mobile.confirm.cert.0002");
			}
						
		} else {
			sMessage = props.getProperty("mobile.confirm.cert.0003");
		}

		resMap.put("MSG", sMessage);
		resMap.put("resFlag", isSuccess);
		return resMap;
	}
	
	@Override
	public List<Object> getUserInfoProcess(RequestParameter parameter) throws Exception {
		return this.listBySqlId(NAMESPACE+"getUserInfoProcess", parameter);
	}


}
