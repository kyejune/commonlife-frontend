package com.kolonbenit.iot.mobile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;

public interface MobileApplianceService {

	/**
	 * 삼성 로그인 URL 조회
	 * @param pramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getLoginUrl(RequestParameter pramMap) throws Exception;
	
	/***
	 * access token 갱신
	 * @param pramMap
	 * @return access token information
	 * @throws Exception
	 */
	public Map<String, Object> remakeAccessToken(Map<String, Object> pramMap) throws Exception;
	
	/**
	 * access token, refresh token 생성
	 * @param param
	 * @throws Exception
	 */
	public void insertToken(Map<String, Object> pramMap) throws Exception;
	
	/***
	 * access token, refresh token 갱신
	 * @param pramMap
	 * @return
	 * @throws Exception
	 */
	public void updateToken(Map<String, Object> pramMap) throws Exception;
	
	/***
	 * access token, refresh token 조회
	 * @param pramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTokenInfo(RequestParameter pramMap) throws Exception;
	
	/**
	 * 만료될 access token 조회
	 * @param pramMap
	 * @return
	 * @throws Exception
	 */
	public int updateAccessToken(RequestParameter pramMap) throws Exception;

	/**
	 * 가전 저장
	 * @param pramMap 
	 * 					deviceId : 삼성 가전 조회 응답의 type
	 * 					cmplxId
	 * 					homeId
	 * @return 저장한 THINGS_ID
	 * @throws Exception
	 */
	public List<String> insertAppliance(RequestParameter pramMap) throws Exception;
	
	/***
	 * 가전 변경 알림 처리
	 * @param pramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> syncDeviceToSamsung(List<String> userIds) throws Exception;

	/**
	 * 가전 목록 조회
	 * @param pramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDevice(RequestParameter pramMap) throws Exception;
	
	/**
	 * 가전 USE_YN을 N으로 변경
	 * @param pramMap
	 * @throws Exception
	 */
	public void deleteDevice(RequestParameter pramMap) throws Exception;

	/**
	 * 삼성가전 노티를 받기 위한 subscription 
	 * @param deviceIds
	 */
	public void deviceSubscription(ArrayList<String> deviceIds, Map<String, Object> tokenInfo);
	
	/**
	 * 삼성가전 노티를 받지 않기 위한 unsubscription 
	 * @param deviceIds
	 */
	public void deviceUnsubscription(ArrayList<String> deviceIds, Map<String, Object> tokenInfo);

	/**
	 * AWSIoT에 기기 등록
	 * @param alThingsIds
	 */
	public void deviceRegistForIoT(ArrayList<String> alThingsIds, String sCmplxId, String sVendorId);
	
	/**
	 * AWSIoT에 기기 삭제
	 * @param alThingsIds
	 */
	public void deviceUnregistForIoT(ArrayList<String> alThingsIds, String sCmplxId, String sVendorId);

	/**
	 * 벤더 조회
	 * @param pramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getVendor(RequestParameter pramMap) throws Exception;
	
	/**
	 * 삼성계정 중복 여부
	 * @param pramMap
	 * @return
	 * @throws Exception
	 */
	public boolean isDupSamsungAccount(RequestParameter pramMap) throws Exception;

	
	/**
	 * 삼성계정 정보 삭제
	 * @param param
	 * @throws Exception
	 */
	public Map<String, Object> deleteToken(RequestParameter param) throws Exception;
	
	/**
	 * 삼성가전 Listener 관리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean manageDeviceListener(RequestParameter param) throws Exception;
}
