package com.kolonbenit.benitware.common.util.push;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kolonbenit.benitware.common.util.PropertiesUtil;
import com.kolonbenit.benitware.common.util.httpClient.SSLHttpClientUril;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 * cf. https://firebase.google.com/docs/cloud-messaging/
 * </pre>
 */
public class FcmPushUtil {

	private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";

	private static String API_KEY = "";
	private static final int MAX_SEND_CNT = 999;	// 1회 최대 전송 가능 수 (1000건까지 가능)

	private static List<String> regList = null;
	private static List<Map<String, Object>> rtnList = null;
	private final static Logger logger = LoggerFactory.getLogger(FcmPushUtil.class);
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * <pre>
	 * Android / iOS FCM 푸시 발송
	 * </pre>
	 * @param regIdList
	 * @param jsonStr
	 * @param osType
	 * @return
	 */
	public static List<Map<String, Object>> sendPush(List<String> regIdList, String jsonStr, String osType) {
		if (regIdList.size() > 0) {
			Properties prop = PropertiesUtil.getPropertiesFromClasspath("gcm/gcm.properties");
			API_KEY = prop.getProperty("GCMserverAPIKey");
			String sJson = setJson(regIdList, jsonStr, osType);

			regList = regIdList;
			beforeSend(sJson);
		}
		return rtnList;
	}

	/**
	 * 푸시 내용 구성
	 * @param regIdList
	 * @param jsonStr
	 * @param osType
	 * @return
	 */
	private static String setJson(List<String> regIdList, String jsonStr, String osType) {
		String sJson = "";

		String sRegIds = setRegIds(regIdList);
		String sMessage = setMessage(jsonStr, osType);
		try {
			ObjectNode msgNode = (ObjectNode) mapper.readTree(sMessage);
			JsonNode idNode = (JsonNode) mapper.readTree(sRegIds);

			msgNode.set("registration_ids", idNode);
			sJson = msgNode.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sJson;
	}

	/**
	 * <pre>
	 * 토큰 리스트 구성
	 *   - registration_ids
	 * </pre>
	 * @param regIdList
	 * @return
	 */
	private static String setRegIds(List<String> regIdList) {
		String sRegIds = "";

		try {
			ArrayNode array = mapper.createArrayNode();

			for (String str : regIdList) {
				array.add(str);
			}

			sRegIds = array.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sRegIds;
	}

	/**
	 * 푸시 메시지 구성
	 * @param jsonStr
	 * @param osType
	 * @return
	 */
	private static String setMessage(String jsonStr, String osType) {
		String sMessage = "";

		try {
			ObjectNode root = mapper.createObjectNode();
			ObjectNode node = mapper.createObjectNode();
			node.put("message", jsonStr);
			
			// 공통코드 MB009 참고
			switch (osType) {
			case "iOS":
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap = convertJSONstringToMap(jsonStr);
				ObjectMapper mapper = new ObjectMapper();
				ObjectNode json = mapper.createObjectNode();
				
				json.put("body", dataMap.get("message").toString());
				json.put("title", dataMap.get("title").toString());
				json.put("icon", "");
				root.put("notification", json);
				root.put("data", node);
				break;
			default:
				root.put("data", node);
				break;
			}

			sMessage = root.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sMessage;
	}

	/**
	 * 푸시 발송 전, 대상을 1000건씩 나눔
	 * @param rawPayload
	 */
	private static void beforeSend(String rawPayload) {
		if (regList != null) {
			if (regList.size() <= MAX_SEND_CNT) {
				sendMulticastResult(regList, rawPayload);
			} else {
				List<String> regListTemp = new ArrayList<String>();
				for (int i=0; i < regList.size(); i++) {
					if ( ((i+1) % MAX_SEND_CNT) == 0 ) {
						sendMulticastResult(regListTemp, rawPayload);
						regListTemp.clear();
					}
					regListTemp.add(regList.get(i));
				}

				// 남은 것 보내기
				if (regListTemp.size() != 0) {
					sendMulticastResult(regListTemp, rawPayload);
				}
			}
		}
	}

	/**
	 * 실제 푸시 발송
	 * @param list
	 * @param rawPayload
	 */
	private static void sendMulticastResult(List<String> list, String rawPayload) {
		rtnList = new ArrayList<>();
		Map<String, Object> rtnMap = null;

		String sAuthorization = "key=" + API_KEY;
		Header header = new BasicHeader("Authorization", sAuthorization);
		String sRes = SSLHttpClientUril.postJson(FCM_URL, header, rawPayload);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sendDate = sdf.format(new Date());
		logger.info("[PUSH]-{rawPayload}"+sRes, sRes);
		logger.info("[PUSH]-{sAuthorization}"+sAuthorization, sAuthorization);
		try {
			JsonNode rtnNode = (JsonNode) mapper.readTree(sRes);
			
			ArrayNode results = (ArrayNode) rtnNode.get("results");

			for (int i=0; i < list.size(); i++) {
				JsonNode result = results.get(i);

				String messageId = "";
				String error = "";
				boolean isSuccess = false;

				if ( result.has("message_id") ) {
					messageId = result.get("message_id").asText();
					isSuccess = true;
				}

				if ( result.has("error") ) {
					error = result.get("error").asText();
				}
				logger.info("[PUSH]-{results.get(i)}"+results.get(i), sAuthorization);
				rtnMap = new HashMap<>();
				rtnMap.put("regId", list.get(i));
				rtnMap.put("msgId", messageId);
				rtnMap.put("errMsg", error);
				rtnMap.put("sendDt", sendDate);
				rtnMap.put("resFlag", isSuccess);

				logger.info("[PUSH]-{}", rtnMap);

				rtnList.add(rtnMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static Map<String,Object> convertJSONstringToMap(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        
        map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        
        return map;
    }
}
