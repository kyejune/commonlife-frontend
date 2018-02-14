package com.kolonbenit.benitware.common.util.push;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.kolonbenit.benitware.common.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class GcmPushUtil {

	private static final Logger logger = LoggerFactory.getLogger(GcmPushUtil.class);

	private static String API_KEY = "";
	private static final int MAX_SEND_CNT = 999;	// 1회 최대 전송 가능 수 (1000건까지 가능)

	private static Sender sender;
	private static Message message;

	private static List<String> regList = null;
	private static List<Map<String, Object>> rtnList = null;

	/**
	 * <pre>
	 * Android GCM 푸시 발송
	 *   - gcm-server 라이브러리 (GCM 3.0 이전 URL 사용)
	 * </pre>
	 * @param regIdList
	 * @param jsonStr
	 * @return
	 */
	public static List<Map<String, Object>> sendPush(List<String> regIdList, String jsonStr) {
		Properties prop = PropertiesUtil.getPropertiesFromClasspath("gcm/gcm.properties");
		API_KEY = prop.getProperty("GCMserverAPIKey");

		sender = new Sender(API_KEY);
		setMessage(jsonStr);
		regList = regIdList;
		beforeSend();

		return rtnList;
	}

	/**
	 * 푸시 메시지 구성
	 * @param jsonStr
	 */
	private static void setMessage(String jsonStr) {
		Builder builder = new Message.Builder();
		builder.addData("message", jsonStr);
		message = builder.build();
	}

	/**
	 * 푸시 발송 전, 대상을 1000건씩 나눔
	 */
	private static void beforeSend() {
		if (regList != null) {
			if (regList.size() <= MAX_SEND_CNT) {
				sendMulticastResult(regList);
			} else {
				List<String> regListTemp = new ArrayList<String>();
				for (int i=0; i < regList.size(); i++) {
					if ( ((i+1) % MAX_SEND_CNT) == 0 ) {
						sendMulticastResult(regListTemp);
						regListTemp.clear();
					}
					regListTemp.add(regList.get(i));
				}

				// 남은 것 보내기
				if (regListTemp.size() != 0) {
					sendMulticastResult(regListTemp);
				}
			}
		}
	}

	/**
	 * 실제 푸시 발송
	 * @param list
	 */
	private static void sendMulticastResult(List<String> list) {
		rtnList = new ArrayList<>();
		Map<String, Object> rtnMap = null;

		MulticastResult multiResult;
		try {
			multiResult = sender.send(message, list, 5);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sendDate = sdf.format(new Date());

			if (multiResult != null) {
				List<Result> resultList = multiResult.getResults();

				for (int i=0; i < resultList.size(); i++) {
					Result result = resultList.get(i);

					String messageId = result.getMessageId();
					boolean isSuccess = false;

					rtnMap = new HashMap<>();
					rtnMap.put("regId", list.get(i));
					rtnMap.put("msgId", messageId);
					rtnMap.put("errMsg", result.getErrorCodeName());
					rtnMap.put("sendDt", sendDate);

					if (messageId != null) {
						isSuccess = true;
					}
					rtnMap.put("resFlag", isSuccess);

					logger.info("[PUSH]-{}", rtnMap);

					rtnList.add(rtnMap);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
