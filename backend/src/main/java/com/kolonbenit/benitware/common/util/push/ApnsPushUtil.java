package com.kolonbenit.benitware.common.util.push;

import com.kolon.comlife.board.web.BoardController;
import com.kolonbenit.benitware.common.util.PropertiesUtil;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

// TODO: 검증되지 않았으므로 테스트 하면서 수정 필요할 수 있음

public class ApnsPushUtil {

	private static final Logger logger = LoggerFactory.getLogger(ApnsPushUtil.class);
	private static final String PROP_CLASS_PATH = "/properties/apns/";

	public static PushNotificationManager pushManager;
	public static PushNotificationPayload payload;

	private static List<String> regList = null;
	private static List<Map<String, Object>> rtnList = null;

	/**
	 * iOS APNS 푸시 발송
	 * @param regIdList
	 * @param jsonStr
	 * @return
	 */
	public static List<Map<String, Object>> sendPush(List<String> regIdList, String jsonStr) {

		try {
			Properties prop = PropertiesUtil.getPropertiesFromClasspath("apns/apns.properties");

			String sPath = PROP_CLASS_PATH + prop.getProperty("APNSsslCertificateName");
			String sPwd = prop.getProperty("APNSsslCertificatePwd");

			InputStream is = ApnsPushUtil.class.getResourceAsStream(sPath);

			AppleNotificationServer server = new AppleNotificationServerBasicImpl(is, sPwd, true);
			pushManager = new PushNotificationManager();
			pushManager.initializeConnection(server);

			setMessage(jsonStr);
			regList = regIdList;
			sendNotifications();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rtnList;
	}

	/**
	 * 푸시 메시지 구성
	 * @param jsonStr
	 */
	private static void setMessage(String jsonStr) {
		payload = PushNotificationPayload.complex();

		try {
			payload.addAlert(jsonStr);
			payload.addBadge(1);
			payload.addSound("default");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 실제 푸시 발송
	 */
	private static void sendNotifications() {
		rtnList = new ArrayList<>();
		Map<String, Object> rtnMap = null;

		List<Device> deviceList = new ArrayList<Device>(regList.size());

		for (String receiverId : regList) {
			Device device = new BasicDevice();
            device.setToken(receiverId);
            deviceList.add(device);
		}

		PushedNotifications notifications;
		try {
			notifications = pushManager.sendNotifications(payload, deviceList);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sendDate = sdf.format(new Date());

			if (notifications != null) {
				for (int i=0; i < notifications.size(); i++) {
					PushedNotification notification = notifications.get(i);

					int identifier = notification.getIdentifier();
					boolean isSuccess = false;

					rtnMap = new HashMap<>();
					rtnMap.put("regId", notification.getDevice().getToken());
					rtnMap.put("msgId", identifier);
					rtnMap.put("errMsg", notification.getException().getMessage());
					rtnMap.put("sendDt", sendDate);

					if (notification.isSuccessful()) {
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
