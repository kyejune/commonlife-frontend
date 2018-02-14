package com.kolon.comlife.users.service.impl;

import com.benitware.framework.http.parameter.RequestParameter;
import com.benitware.framework.orm.mybatis.BaseIbatisDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kolon.comlife.users.service.MobileApplianceService;
import com.kolonbenit.benitware.common.util.httpClient.HttpClientUtil;
import com.kolonbenit.benitware.common.util.httpClient.SSLHttpClientUril;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.*;

@Service
public class MobileApplianceServiceImpl extends BaseIbatisDao<Object, Object> implements MobileApplianceService {

	/**
	 * Logger for this class and subclasses
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    @Qualifier("messagesProps")
	private Properties props;
	
	private static String NAMESPACE = "mobile.ApplianceMapper.";
	//private static String SAMSUNG_COMMUNICATION_URL = "https://simulator.cspserver.net/api/";
	//private static String SAMSUNG_COMMUNICATION_URL = "https://simulator.cspserver.net/api/";
	//private static String SAMSUNG_COMMUNICATION_URL = "https://api2.cspserver.net/bridge/api/";
	

	@Override
	public Map<String, Object> getTokenInfo(RequestParameter pramMap) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) this.getBySqlId(NAMESPACE+"getTokenInfo", pramMap);
		return dataMap;
	}
	
	@Override
	public Map<String, Object> remakeAccessToken(Map<String, Object> pramMap) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("grant_type", "refresh_token");
		postMap.put("refresh_token", pramMap.get("RE_TOKEN_KEY"));
		postMap.put("client_id", pramMap.get("CLIENT_ID"));
		postMap.put("client_secret", pramMap.get("CERT_KEY"));
		
		// success : {access_token=cu0kbQfnSpvMhBEE85AEO2ThQ, token_type=bearer, access_token_expires_in=86400, expires_in=86400, refresh_token=cUeSvzEaBn, refresh_token_expires_in=7776000}
		// fail  : {error=invalid_grant, error_code=AUT_1803, error_description=The value of refresh_token parameter is incorrect.}			
		String sResult = SSLHttpClientUril.post(String.valueOf(pramMap.get("CERT_ADDR")), postMap);
		
		logger.info("remakeAccessToken result : {}", sResult);
		
		// convert JSON string to Map
		ObjectMapper mapper = new ObjectMapper();
		resultMap = mapper.readValue(sResult, new TypeReference<Map<String, String>>(){});
		
		return resultMap;
	}
	
	@Override
	public void insertToken(Map<String, Object> pramMap) throws Exception {
		this.insertBySqlId(NAMESPACE+"insertToken", pramMap);
	}
	
	@Override
	public void updateToken(Map<String, Object> pramMap) throws Exception {
		this.updateBySqlId(NAMESPACE+"updateToken", pramMap);
	}

	@Override
	public Map<String, Object> getDevice(RequestParameter pramMap) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info("syncDeviceToSamsung.getDevice.getTokenInfo param : {}", pramMap);
		Map<String, Object> tokenInfo = getTokenInfo(pramMap);
		
		if (tokenInfo != null) logger.info("tokenInfo : {}", tokenInfo.toString()); 
		
		BasicHeader channelKey = new BasicHeader("x-csp-channel-key", String.valueOf(tokenInfo.get("CHANNEL_KEY")));
		BasicHeader appId = new BasicHeader("x-csp-appId", String.valueOf(tokenInfo.get("CLIENT_ID")));
		BasicHeader userId = new BasicHeader("x-csp-userId", String.valueOf(tokenInfo.get("USER_ID_KEY")));
		BasicHeader authorization = new BasicHeader("Authorization", String.valueOf(tokenInfo.get("CERT_METHOD")) + " " + String.valueOf(tokenInfo.get("TOKEN_KEY")));
		
		Header[] headers = {channelKey, appId, userId, authorization};
		
		Map<String, Object> mVendor = this.getVendor(pramMap);
		String sResult = SSLHttpClientUril.get(String.valueOf(mVendor.get("CTRL_ADDR")) + "devices", null, headers);
		
		logger.info("getDevice sResult : {}", sResult);
		
		// convert JSON string to Map
		ObjectMapper mapper = new ObjectMapper();
		resultMap = mapper.readValue(sResult, new TypeReference<Map<String, Object>>(){});
		
		resultMap.put("DEVICE_INFO_JSON_STRING", sResult);
		resultMap.put("VENDOR_CD", String.valueOf(mVendor.get("VENDOR_CD")));
		
		return resultMap;
	}
	
	@Override
	@Transactional
	public List<String> insertAppliance(RequestParameter pramMap) throws Exception {

		logger.info("@@@ insertAppliance Params : {}", pramMap.toString());
		ArrayList<String> rtnThingsIds = new ArrayList<String>();
		
		//#{deviceId}, #{vendorId}
		List<Object> thingsList = this.listBySqlId(NAMESPACE+"getThingsModel", pramMap);
		
		if (thingsList != null && thingsList.size() > 0) {
			
			String sUpThingsId = "";
			String sClientId = "";
			String sThingsConnCd = "";
			boolean isChangeUpThingsId = true;
			ArrayList<Integer> alThingsIds = new ArrayList<Integer>();
			
			for (Object thing : thingsList) {
				
				HashMap<String, String> item = (HashMap<String, String>) thing;
				
				logger.info("@@@ thingInfo : {}", item.toString());
				
				sThingsConnCd = "";
				
				//모듈이 아닐 때
				if ("N".equals(item.get("MOD_YN"))) {
					//UP_THINGS_ID를 Query로 조회한다. #{cmplxId}, #{homeId}
					Map<String, Object> dataMap = (Map<String, Object>) this.getBySqlId(NAMESPACE+"getGatewayThingsId", pramMap);
					
					if (dataMap != null && !"".equals(dataMap.get("THINGS_ID"))) {
						sUpThingsId = dataMap.get("THINGS_ID").toString();
					} else {
						sUpThingsId = "-1";
					}
					
					sThingsConnCd = item.get("CLNT_ID");
					sClientId = makeClientId(pramMap, item.get("THINGS_MODEL_CD"));
				} else {
					sClientId = item.get("CLNT_ID");
				}
				
				RequestParameter insertParam = new RequestParameter();
				
				insertParam.put("cmplxId", pramMap.get("cmplxId"));
				insertParam.put("upThingsId", sUpThingsId);
				insertParam.put("homeId", pramMap.get("homeId"));
				insertParam.put("serialNo", pramMap.get("serialNo"));
				
				insertParam.put("clntId", sClientId);
				insertParam.put("thingsNm", item.get("THINGS_NM"));
				insertParam.put("spThingsNm", item.get("THINGS_NM"));
				insertParam.put("stndCd", item.get("STND_CD"));
				insertParam.put("modYn", item.get("MOD_YN"));
				insertParam.put("errYn", item.get("ERR_YN"));
				insertParam.put("vendorId", item.get("VENDOR_ID"));
				insertParam.put("metaId", item.get("META_ID"));
				insertParam.put("modelId", item.get("MODEL_ID"));
				insertParam.put("ver", item.get("VER"));
				insertParam.put("thingsConnCd", sThingsConnCd);
				insertParam.put("kindCd", item.get("KIND_CD"));
				insertParam.put("typeCd", item.get("TYPE_CD"));
				insertParam.put("deviceYn", item.get("DEVICE_YN"));
				insertParam.put("sensBinaryYn", item.get("SENS_BINARY_YN"));
				insertParam.put("sensMetorYn", item.get("SENS_METOR_YN"));
				insertParam.put("elctApclYn", item.get("ELCT_APLC_YN"));
				insertParam.put("useYn", item.get("USE_YN"));
				insertParam.put("rmk", item.get("RMK"));
				insertParam.put("vendorCd", pramMap.get("vendorCd"));
				
				logger.info("insert device : {}", insertParam.toString());

				this.insertBySqlId(NAMESPACE+"insertDevice", insertParam);
				
				int iThingsId = Integer.parseInt(insertParam.get("thingsIdx").toString());
				logger.info("iThingsId : {}", iThingsId);
				
				if ("Y".equals(item.get("MOD_YN"))) {
					this.insertBySqlId(NAMESPACE+"insertRoomThings", insertParam);
				} else {
					insertParam.put("manageType", "ADD");
					boolean isManageDeviceListener = manageDeviceListener(insertParam);
					logger.info("process manageDeviceListener result : {}", isManageDeviceListener);
				}
				
				alThingsIds.add(iThingsId);
				rtnThingsIds.add(String.valueOf(iThingsId));
				
				if (isChangeUpThingsId) {
					sUpThingsId = String.valueOf(iThingsId);
					isChangeUpThingsId = false;
				}
			}
			
			RequestParameter stsParam = new RequestParameter();
			stsParam.put("cmplxId", pramMap.get("cmplxId"));
			stsParam.put("thingsIds", alThingsIds);
			logger.info("stsParam ThingsIds : {}", stsParam.toString());
			
			//장비 속성 저장
			this.insertBySqlId(NAMESPACE+"insertDeviceSts", stsParam);
		}

		return rtnThingsIds;
	}
	
	@Transactional
	
	@Override
	public boolean manageDeviceListener(RequestParameter param) {
		
		String sManageType = param.getString("manageType");
		StringBuffer sbJson = new StringBuffer();
		
		sbJson.append("{");
		  sbJson.append("\"DATA\":{");
		  	sbJson.append("\"mode\":\"").append(sManageType).append("\", ");
		  	sbJson.append("\"cmplxId\":\"").append(param.get("cmplxId")).append("\", ");
		  	sbJson.append("\"homeId\":\"").append(param.get("homeId")).append("\", ");
		  	sbJson.append("\"clientId\":\"").append(param.get("clntId")).append("\", ");
		  	sbJson.append("\"deviceType\":\"").append(param.get("thingsConnCd")).append("\", ");
		  	sbJson.append("\"vendorType\":\"").append(param.get("vendorCd")).append("\"");
		  sbJson.append("}");
		sbJson.append("}");

		logger.info("subscription : {}", sbJson.toString());
		
		String sResult = HttpClientUtil.postJson("http://52.78.71.172:58080", sbJson.toString());
		
		logger.info("subscription sResult : {}", sResult);
		
		return false;
	}
	
	@Override
	public void deleteDevice(RequestParameter pramMap) throws Exception {
		this.updateBySqlId(NAMESPACE+"deleteDevice", pramMap);
	}
	

	@Override
	public Map<String, Object> getLoginUrl(RequestParameter pramMap) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) this.getBySqlId(NAMESPACE+"getLoginUrl", pramMap);
		return dataMap;
	}
	
	@Override
	public void deviceSubscription(ArrayList<String> deviceIds, Map<String, Object> tokenInfo) {

		BasicHeader channelKey = new BasicHeader("x-csp-channel-key", String.valueOf(tokenInfo.get("CHANNEL_KEY")));
		BasicHeader appId = new BasicHeader("x-csp-appId", String.valueOf(tokenInfo.get("CLIENT_ID")));
		BasicHeader userId = new BasicHeader("x-csp-userId", String.valueOf(tokenInfo.get("USER_ID_KEY")));
		BasicHeader authorization = new BasicHeader("Authorization", String.valueOf(tokenInfo.get("CERT_METHOD")) + " " + String.valueOf(tokenInfo.get("TOKEN_KEY")));
		BasicHeader contentType = new BasicHeader("Content-Type", "application/json");
		
		Header[] headers = {channelKey, appId, userId, authorization, contentType};
		
		StringBuffer sbJsonId = new StringBuffer();
		
		if (deviceIds != null && deviceIds.size() > 0) {
			sbJsonId.append("{\"Subscriptions\":[");
			for (int i = 0; i < deviceIds.size(); i++) {
				sbJsonId.append("\"");
				sbJsonId.append(deviceIds.get(i));
				sbJsonId.append("\"");
				if (i != deviceIds.size() -1) {
					sbJsonId.append(", ");
				}
			}
			sbJsonId.append("]}");
		}
		
		logger.info("subscription : {}", sbJsonId.toString());
		
		String sResult = SSLHttpClientUril.postJson(String.valueOf(tokenInfo.get("CTRL_ADDR")) + "subscriptions", headers, sbJsonId.toString());
		
		logger.info("subscription sResult : {}", sResult);
		
	}
	
	private void deviceSubscription(Collection<String> deviceIds, Map<String, Object> tokenInfo) {
		deviceSubscription(new ArrayList<String>(deviceIds), tokenInfo);
	}

	
	@Override
	public void deviceUnsubscription(ArrayList<String> deviceIds, Map<String, Object> tokenInfo) {

		BasicHeader channelKey = new BasicHeader("x-csp-channel-key", String.valueOf(tokenInfo.get("CHANNEL_KEY")));
		BasicHeader appId = new BasicHeader("x-csp-appId", String.valueOf(tokenInfo.get("CLIENT_ID")));
		BasicHeader userId = new BasicHeader("x-csp-userId", String.valueOf(tokenInfo.get("USER_ID_KEY")));
		BasicHeader authorization = new BasicHeader("Authorization", String.valueOf(tokenInfo.get("CERT_METHOD")) + " " + String.valueOf(tokenInfo.get("TOKEN_KEY")));
		BasicHeader contentType = new BasicHeader("Content-Type", "application/json");
		
		Header[] headers = {channelKey, appId, userId, authorization, contentType};
		
		StringBuffer sbJsonId = new StringBuffer();
		
		if (deviceIds != null && deviceIds.size() > 0) {
			sbJsonId.append("{\"Subscriptions\":[");
			for (int i = 0; i < deviceIds.size(); i++) {
				sbJsonId.append("\"");
				sbJsonId.append(deviceIds.get(i));
				sbJsonId.append("\"");
				if (i != deviceIds.size() -1) {
					sbJsonId.append(", ");
				}
			}
			sbJsonId.append("]}");
		}
		
		logger.info("subscription : {}", sbJsonId.toString());
		
		String sResult = SSLHttpClientUril.deleteJson(String.valueOf(tokenInfo.get("CTRL_ADDR")) + "subscriptions", headers, sbJsonId.toString());
		
		logger.info("subscription sResult : {}", sResult);
	}
	
	private void deviceUnsubscription(Collection<String> deviceIds, Map<String, Object> tokenInfo) {
		deviceUnsubscription(new ArrayList<String>(deviceIds), tokenInfo);
	}
	
	@Override
	public void deviceRegistForIoT(ArrayList<String> alThingsIds, String sCmplxId, String sVendorId) {
		
		RequestParameter param = new RequestParameter();
		param.put("cmplxId", sCmplxId);
		param.put("vendorId", sVendorId);
		param.put("thingsIds", alThingsIds);
		
		List<Object> clientList = this.listBySqlId(NAMESPACE+"getClientInfo", param);
		
		final String IOT_CERT_ID = "arn:aws:iot:ap-northeast-2:803694487581:cert/61a8d1b5ca4cc7bafae1614af2d481a5b1b97fc6e55db514b0f5a52671e4bca6";
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode root = mapper.createObjectNode();
		
		root.put("cert_id", IOT_CERT_ID);
		
		ArrayNode devices = mapper.createArrayNode();
		
		for (Object item : clientList) {
			HashMap<String, String> res = (HashMap<String, String>) item;
			
			ObjectNode deviceInfo = mapper.createObjectNode();
			
			deviceInfo.put("client_id", res.get("CLNT_ID"));
			deviceInfo.put("manufacturer", res.get("VENDOR_CD"));
			deviceInfo.put("act_sens", res.get("ACT_SENS"));
			deviceInfo.put("dev_type", res.get("DEV_TYPE"));
			
			devices.add(deviceInfo);
		}
		
		ObjectNode deviceNode = mapper.createObjectNode();
		deviceNode.put("devices", devices);
		
		root.putAll(deviceNode);

		logger.info("deviceRegistForIoT Json Data : {}", root.toString());
		
		String sIoTHost = "https://5z1zpi6fnb.execute-api.ap-northeast-2.amazonaws.com";
		String sIoTPath = "/homeiot/registration/devices";
		String sResult = SSLHttpClientUril.postJson(sIoTHost + sIoTPath, root.toString());
		
		logger.info("deviceRegistForIoT result : {}", sResult);
	}
	
	@Override
	public void deviceUnregistForIoT(ArrayList<String> alThingsIds, String sCmplxId, String sVendorId) {
		
		String sClientId = "";
		
		RequestParameter param = new RequestParameter();
		param.put("cmplxId", sCmplxId);
		param.put("vendorId", sVendorId);
		param.put("thingsIds", alThingsIds);
		
		List<Object> clientList = this.listBySqlId(NAMESPACE+"getClientInfo", param);
		
		Map<String, Object> connectInfoMap = (Map<String, Object>) this.getBySqlId(NAMESPACE+"getConnectInfo", param);
		
		//"61a8d1b5ca4cc7bafae1614af2d481a5b1b97fc6e55db514b0f5a52671e4bca6";
		String sCertId = "";
		
		//https://5z1zpi6fnb.execute-api.ap-northeast-2.amazonaws.com
		String sIoTHost = "";
		
		if (connectInfoMap != null) {
			//arn:aws:iot:ap-northeast-2:803694487581:cert/61a8d1b5ca4cc7bafae1614af2d481a5b1b97fc6e55db514b0f5a52671e4bca6
			String sCertKey = String.valueOf(connectInfoMap.get("CERT_KEY"));
			String sApiGwAddr = String.valueOf(connectInfoMap.get("API_GW_ADDR"));
			
			if (sCertKey.indexOf("/") > 0) {
				int iCertIdIdx = sCertKey.split("/").length -1;
				sCertId = sCertKey.split("/")[iCertIdIdx];
			}
			
			if (StringUtils.isNotEmpty(sApiGwAddr)) {
				sIoTHost = sApiGwAddr;
			}
		} else {
			logger.error("Connection Info is not exist. Please Check COMPLEX_M WHERE CMPLX_ID = {}", sCmplxId);
			return;
		}
		
		if ("".equals(sCertId)) {
			logger.error("CERT_KEY is not exist. Please Check COMPLEX_M.CERT_KEY WHERE CMPLX_ID = {}", sCmplxId);
			return;
		}
		if ("".equals(sIoTHost)) {
			logger.error("API_GW_ADDR is not exist. Please Check COMPLEX_M.API_GW_ADDR WHERE CMPLX_ID = {}", sCmplxId);
			return;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode root = mapper.createObjectNode();
		
		root.put("cert_id", sCertId);
		
		ArrayNode devices = mapper.createArrayNode();
		
		for (Object item : clientList) {
			HashMap<String, String> res = (HashMap<String, String>) item;
			devices.add(res.get("CLNT_ID"));
			sClientId = String.valueOf(res.get("CLNT_ID"));
		}
		
		ObjectNode deviceNode = mapper.createObjectNode();
		deviceNode.put("devices", devices);
		
		root.putAll(deviceNode);

		logger.info("deviceUnregistForIoT Json Data : {}", root.toString());
		
		String sIoTPath = "/homeiot/registration/devices";
		
		String sResult = "";
		
		logger.info("clientList.size():{}", clientList.size());
		if (clientList.size() > 1) {
			logger.info("URL1 :::::: {}", sIoTHost + sIoTPath);
			sResult = SSLHttpClientUril.deleteJson(sIoTHost + sIoTPath, root);
		} else {
			sIoTPath = "/homeiot/registration/device/" + sClientId;
			logger.info("URL0 :::::: {}", sIoTHost + sIoTPath);
			sResult = SSLHttpClientUril.deleteJson(sIoTHost + sIoTPath);
		}
		
		logger.info("deviceUnregistForIoT result : {}", sResult);
	}
	
	/**
	 * CLNT_ID 생성
	 * @param pramMap
	 * @param thingModelCd
	 * @return
	 */
	private String makeClientId(RequestParameter pramMap, String thingModelCd) {
		
		Map<String, Object> dataMap = (Map<String, Object>) this.getBySqlId(NAMESPACE+"getClientIdPreFix", pramMap);
		
		String sClientIdPrefix = "";
		String sClientIdSuffix = "";
		
		if (dataMap != null) {
			sClientIdPrefix = String.valueOf(dataMap.get("CLNT_ID_PREFIX"));
		}
		
		int iTotalLen = 10;
		int iModelLen = thingModelCd.length();

		String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int n = alphabet.length();
		
	    Random r = new Random();
	    StringBuffer sbRan = new StringBuffer();

	    for (int i = 0; i < (iTotalLen - iModelLen); i++) {
	    	sbRan.append(alphabet.charAt(r.nextInt(n)));
	    }
	    
	    sClientIdSuffix = thingModelCd + sbRan.toString();
		
		return sClientIdPrefix + "-" + sClientIdSuffix;
	}

	/**
	 * serial no를 things id로 변환한다.
	 * @param serialNo
	 * @return
	 */
	private ArrayList<String> convertSerialNoToThingsId(Collection<String> serialNo) {
		
		ArrayList<String> thingsIds = new ArrayList<String>();
		ArrayList<String> alSerialNo = new ArrayList<String>(serialNo);
		
		for (String item : serialNo) {
			logger.info("Collection serialNo : {}", item);
		}
		for (String item : alSerialNo) {
			logger.info("ArrayList alSerialNo : {}", item);
		}
		
		RequestParameter param = new RequestParameter();
		param.put("serialNos", alSerialNo);
		logger.info("convertSerialNoToThingsId serialNos : {}", serialNo.size());
		
		List<Object> thingsList = this.listBySqlId(NAMESPACE+"getSerialNoToThingsId", param);
		logger.info("convertSerialNoToThingsId thingsList.size : {}", thingsList.size());
		
		for (Object item : thingsList) {
			HashMap<String, String> thing = (HashMap<String, String>) item;
			logger.info("convertSerialNoToThingsId HashMap : {}", thing.toString());
			logger.info("convertSerialNoToThingsId THINGS_ID : {}", thing.get("THINGS_ID"));
			thingsIds.add(String.valueOf(thing.get("THINGS_ID")));
		}
		logger.info("thingsIds.size : {}", thingsIds.size());
		
		return thingsIds;
	}

	@Override
	public Map<String, Object> getVendor(RequestParameter pramMap) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) this.getBySqlId(NAMESPACE+"getVendorInfo", pramMap);
		return dataMap;
	}

	@Override
	public boolean isDupSamsungAccount(RequestParameter pramMap) throws Exception {
		boolean isDup = false;
		int iAccCnt = this.countBySqlId(NAMESPACE + "getAccountCnt", pramMap);
		if (iAccCnt > 1) {
			isDup = true;
		}
		return isDup;
	}

	@Override
	public Map<String, Object> deleteToken(RequestParameter param) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		boolean isSuccess = true;
		String res = props.getProperty("mobile.appliance.account.release.success");
		
		//FIXME susia  (삼성Real : 1, 삼성Simulator : 13)
//		param.put("vendorId", "13");
		
		int iDeleteRowCnt = this.deleteBySqlId(NAMESPACE + "deleteToken", param);
		
		if (iDeleteRowCnt == 0) {
			isSuccess = false;
			res = props.getProperty("mobile.appliance.account.release.fail");
		} else {
			this.deleteDevice(param);
		}
		
		Map<String, Object> loginInfoMap = this.getLoginUrl(param);
		String sLoginUrl = String.valueOf(loginInfoMap.get("LOGIN_URL"));
		String sCallBackUrl = String.valueOf(loginInfoMap.get("CBAK_ADDR")) + "?";
		
		resultMap.put("LOGIN_URL", sLoginUrl + URLEncoder.encode(sCallBackUrl, "UTF-8"));
		
		resultMap.put("resFlag", isSuccess);
		resultMap.put("MSG", res);
		
		return resultMap;
	}

	@Override
	public int updateAccessToken(RequestParameter pramMap) throws Exception {
		
		List<Object> expireTokenList = this.listBySqlId(NAMESPACE+"getFutureExpireTokenList", pramMap);
		
		if (expireTokenList != null) {
			logger.info("Target Update Access Token Count : {}", expireTokenList.size());
		}
		
		int iUpdatedRowCnt = 0;
		
		for (Object tokenInfo : expireTokenList) {
			HashMap<String, Object> item = (HashMap<String, Object>) tokenInfo;
			Map<String, Object> accessTokenInfo = this.remakeAccessToken(item);
			
			//token update
			if (accessTokenInfo != null && accessTokenInfo.get("access_token") != null && !"".equals(accessTokenInfo.get("access_token"))) {
				
				logger.info("remakeMap : {}", accessTokenInfo.toString());
				
				accessTokenInfo.put("cmplxId", item.get("CMPLX_ID"));
				accessTokenInfo.put("homeId", item.get("HOME_ID"));
				accessTokenInfo.put("vendorId", item.get("VENDOR_ID"));
				accessTokenInfo.put("userIdKey", item.get("USER_ID_KEY"));
				
				this.updateToken(accessTokenInfo);
			}
			
			iUpdatedRowCnt++;
		}
		
		return iUpdatedRowCnt;
	}

	@Override
	public Map<String, Object> syncDeviceToSamsung(List<String> userIds) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
