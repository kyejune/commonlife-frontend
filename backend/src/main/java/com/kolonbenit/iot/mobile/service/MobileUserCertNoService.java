package com.kolonbenit.iot.mobile.service;

import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;

import java.util.Map;

public interface MobileUserCertNoService {


	/**
	 * @description  회원등록을 처리한다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName 회원등록
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> registerMember(RequestParameter parameter) throws Exception;

	/**
	 * @description  회원가입  세대주 인증번호 요청을 처리한다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호요청
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> sendHeadCertificationNumber(RequestParameter parameter) throws Exception;
	
	
	/**
	 * @description  회원가입  사용자 인증번호 요청을 처리한다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호요청
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> sendUserCertificationNumber(RequestParameter parameter) throws Exception;

	/**
	 * @description  세대주 인증번호 확인을 처리힌다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호확인
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> confirmHeadCertificationNumber(RequestParameter parameter) throws Exception;
	
	
	
	/**
	 * @description  사용자 인증번호 확인을 처리힌다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호확인
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> confirmUserCertificationNumber(RequestParameter parameter) throws Exception;

	


}