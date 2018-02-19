package com.kolonbenit.iot.mobile.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolonbenit.benitware.common.util.CommandUtil;
import com.kolonbenit.benitware.common.util.FormattingUtil;
import com.kolonbenit.benitware.common.util.httpClient.HttpClientUtil;
import com.kolonbenit.benitware.common.util.mcache.JedisHelper;
import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import com.kolonbenit.benitware.framework.orm.mybatis.BaseIbatisDao;
import com.kolonbenit.benitware.framework.xplaform.domain.ResultSetMap;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import com.iot.mobile.cvnet.ImageMovie;
//import iot.iot.netty.client.cvnet.telegram.image.vo.ImageMovieRes;

@Service
public class MobileUserCertNoServiceImpl extends BaseIbatisDao<Object, Object> implements MobileUserCertNoService {

	/**
	 * Logger for this class and subclasses
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    @Qualifier("messagesProps")
	private Properties props;

	// cipher keydata
	@Value("#{applicationProps['cipher.keydata']}")
	public String keyData;

	private static String NAMESPACE = "mobile.UserCertNoMapper.";
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
	
	

	
	@Transactional(propagation= Propagation.REQUIRED, rollbackFor={Exception.class})
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
			
			if(checkDongHoMap.get("gwcd").toString().equalsIgnoreCase("cvnet")){
				// Partner CV.Net 가입연동
				
				String rtnjson = HttpClientUtil.post(PARTNER_URL + CVNET_JOIN_PATH, parameter);
				if (rtnjson == null){					
					isSuccess = false;
				}else{
					//
					ObjectMapper mapper = new ObjectMapper();
					JsonNode callbackNode = mapper.readTree(rtnjson);
					
					
					//JsonNode cvnetDataNode = callbackNode.path(DATA_PATH);
					JsonNode cvnetResultNode = callbackNode.path(RESULT_PATH);
					
					if(cvnetResultNode.get("message").asText().equalsIgnoreCase("ok")){
						isSuccess = true;
					}else{
						isSuccess = false;
					}
				}				
			}
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
	public Map<String, Object> sendHeadCertificationNumber(RequestParameter parameter) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		boolean isHeadSuccess = false;
		boolean isMemberSuccess = false;
		boolean rtnSuccess = false;
		String resMsg = "";

		String strOrgHeadCell = parameter.getString("headCell");		
		String strHeadCell = FormattingUtil.cellPhoneNumHyphen(strOrgHeadCell);

		//parameter.put("cell", strCell);
		parameter.put("headCell", strHeadCell);


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
		
		checkHeadMap = (ResultSetMap) this.getBySqlId(NAMESPACE+"checkHead" , parameter);	//동호/단지에 해당하는 세대주 정보
		
		
		
		if (checkHeadMap != null) {
			isHeadSuccess = true;
		}

		if (isHeadSuccess) {
			
			String sCertNum = RandomStringUtils.randomNumeric(6).toString();
			Date expireDate = DateUtils.addMinutes(new Date(), 2);

			Map<String, Object> certMap = new HashMap<>();
			certMap.put("certNum", sCertNum);
			certMap.put("expireDate", expireDate);

			//MobileSessionUtils.setSessionObject(this.CERTIFICATION_NUMBER_SESSION_KEY, certMap, 0);
			//2017.11.20 인증번호 세션관리에서 DB관리로 변경함(서버 2대로 운영시 센션 공유 문제발생)
			
			parameter.put("certNo", sCertNum);
			parameter.put("certDt", expireDate);
			parameter.put("headId", checkHeadMap.get("headId"));

			this.insertBySqlId(NAMESPACE+"insertUserRegCertM", parameter);
			
			
			String msg = props.getProperty("mobile.send.sms.cert.num");

			//String sRegistNm = parameter.getString("userNm");
			//if (sRegistNm != null && !"".equals(sRegistNm)) {
			//	msg += " [" +sRegistNm+ "]";
			//}
			
			String sMessage = MessageFormat.format(msg, sCertNum);
			
			try {
				CommandUtil.runCommandSMS(strHeadCell, sMessage);
				resMsg = props.getProperty("mobile.send.cert.0001");	//인증번호가 SMS로 발송되었습니다.
				rtnSuccess = true;
			} catch (Exception e) {
				isHeadSuccess = false;
				resMsg = props.getProperty("mobile.send.cert.0002");	//인증번호 발송 처리중 오류가 발생했습니다.
			}
								
			
		} else {
			resMsg = props.getProperty("mobile.common.not.found");			//세대주 정보가 존재하지 않습니다.
		}
			
		resMap.put("userCertId", parameter.get("userCertId"));
		resMap.put("resFlag", rtnSuccess);
		resMap.put("msg", resMsg);
		return resMap;
	}
	
	
	@Override
	public Map<String, Object> sendUserCertificationNumber(RequestParameter parameter) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		boolean isHeadSuccess = false;
		boolean isMemberSuccess = false;
		boolean rtnSuccess = false;
		String resMsg = "";

		String strOrgUserCell = parameter.getString("userCell");
		String strOrgHeadCell = parameter.getString("headCell");		
		String strUserCell = FormattingUtil.cellPhoneNumHyphen(strOrgUserCell);
		String strHeadCell = FormattingUtil.cellPhoneNumHyphen(strOrgHeadCell);

		//parameter.put("cell", strCell);
		parameter.put("userCell", strUserCell);
		parameter.put("headCell", strHeadCell);


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
		
		checkHeadMap = (ResultSetMap) this.getBySqlId(NAMESPACE+"checkHeadCert" , parameter);	//동호/단지에 해당하는 세대주 정보
		
		
		
		if (checkHeadMap != null) {
			isHeadSuccess = true;
		}

		if (isHeadSuccess) {
			
			String sCertNum = RandomStringUtils.randomNumeric(6).toString();
			Date expireDate = DateUtils.addMinutes(new Date(), 2);

			Map<String, Object> certMap = new HashMap<>();
			certMap.put("certNum", sCertNum);
			certMap.put("expireDate", expireDate);

			//MobileSessionUtils.setSessionObject(this.CERTIFICATION_NUMBER_SESSION_KEY, certMap, 0);
			//2017.11.20 인증번호 세션관리에서 DB관리로 변경함(서버 2대로 운영시 센션 공유 문제발생)
			
			parameter.put("certNo", sCertNum);
			parameter.put("certDt", expireDate);
			parameter.put("headId", checkHeadMap.get("headId"));

			this.insertBySqlId(NAMESPACE+"updateUserRegCertM", parameter);
			
			
			String msg = props.getProperty("mobile.send.sms.cert.num");

			//String sRegistNm = parameter.getString("userNm");
			//if (sRegistNm != null && !"".equals(sRegistNm)) {
			//	msg += " [" +sRegistNm+ "]";
			//}
			
			String sMessage = MessageFormat.format(msg, sCertNum);
			
			try {
				CommandUtil.runCommandSMS(strUserCell, sMessage);
				resMsg = props.getProperty("mobile.send.cert.0001");	//인증번호가 SMS로 발송되었습니다.
				rtnSuccess = true;
			} catch (Exception e) {
				isHeadSuccess = false;
				resMsg = props.getProperty("mobile.send.cert.0002");	//인증번호 발송 처리중 오류가 발생했습니다.
			}
								
			
		} else {
			resMsg = props.getProperty("mobile.common.not.found");			//세대주 정보가 존재하지 않습니다.
		}
			
		resMap.put("userCertId", parameter.get("userCertId"));
		resMap.put("resFlag", rtnSuccess);
		resMap.put("msg", resMsg);
		return resMap;
	}

	
	
	@Override
	public Map<String, Object> confirmHeadCertificationNumber(RequestParameter parameter) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		boolean isSuccess = false;
		String sMessage = "";

		//@SuppressWarnings("unchecked")
		//Map<String, Object> certMap = (Map<String, Object>) MobileSessionUtils.getSessionObject(this.CERTIFICATION_NUMBER_SESSION_KEY);

		String strOrgHeadCell = parameter.getString("headCell");
	
		String strHeadCell = FormattingUtil.cellPhoneNumHyphen(strOrgHeadCell);

		//parameter.put("cell", strCell);
		parameter.put("headCell", strHeadCell);
		
		Map<String, Object> certMap =(Map<String, Object>)this.getBySqlId(NAMESPACE+"getHeadCertM", parameter);
		
		if (certMap != null) {
			isSuccess = true;
			
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> isSuccess" + isSuccess);
		}

		if (isSuccess) {
			//String sOrgCertNum = (String) certMap.get("certNum");
			//Date expireDate = (Date) certMap.get("expireDate");
			
			String sOrgCertNum = (String) certMap.get("headCertNo");
			Date expireDate = (Date) certMap.get("headCertDt");

			String sCertNum = parameter.getString("headCertNum");
			Date date = new Date();
			int nCompare = date.compareTo(expireDate);

			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> nCompare" + nCompare);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sOrgCertNum" + sOrgCertNum);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sCertNum" + sCertNum);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> expireDate" + expireDate);
			
			
			if ( sCertNum.equals(sOrgCertNum) ) {
				//MobileSessionUtils.removeSessionObject(this.CERTIFICATION_NUMBER_SESSION_KEY);
				//this.deleteBySqlId(NAMESPACE+"deleteUserCertM", parameter);
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
	public Map<String, Object> confirmUserCertificationNumber(RequestParameter parameter) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		boolean isSuccess = false;
		String sMessage = "";

		//@SuppressWarnings("unchecked")
		//Map<String, Object> certMap = (Map<String, Object>) MobileSessionUtils.getSessionObject(this.CERTIFICATION_NUMBER_SESSION_KEY);

		String strOrgUserCell = parameter.getString("userCell");
	
		String strUserCell = FormattingUtil.cellPhoneNumHyphen(strOrgUserCell);

		//parameter.put("cell", strCell);
		parameter.put("userCell", strUserCell);
		
		Map<String, Object> certMap =(Map<String, Object>)this.getBySqlId(NAMESPACE+"getUserCertM", parameter);
		
		if (certMap != null) {
			isSuccess = true;
			
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> isSuccess" + isSuccess);
		}

		if (isSuccess) {
			//String sOrgCertNum = (String) certMap.get("certNum");
			//Date expireDate = (Date) certMap.get("expireDate");
			
			String sOrgCertNum = (String) certMap.get("userCertNo");
			Date expireDate = (Date) certMap.get("userCertDt");

			String sCertNum = parameter.getString("userCertNum");
			Date date = new Date();
			int nCompare = date.compareTo(expireDate);

			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> nCompare" + nCompare);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sOrgCertNum" + sOrgCertNum);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sCertNum" + sCertNum);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> expireDate" + expireDate);
			
			
			if ( sCertNum.equals(sOrgCertNum) ) {
				//MobileSessionUtils.removeSessionObject(this.CERTIFICATION_NUMBER_SESSION_KEY);
				//this.deleteBySqlId(NAMESPACE+"deleteUserCertM", parameter);
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

}