package com.kolonbenit.iot.mobile.service;

import java.util.List;
import java.util.Map;

import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;

public interface MobileUserService {

	/**
	 * @description  모바일에서 푸시 토큰이 갱신될 경우, DB 업데이트 처리한다.
	 * @param RequestParameter parameter
	 * @return int 처리결과
	 * @throws Exception
	 * @methodKorName GCM등록정보수정
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int modifyGcmRegInfoIntro(RequestParameter parameter) throws Exception;

	/**
	 * @description  모바일 로그인을 처리한다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName 모바일사용자로그인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> mobileUserLogin(RequestParameter parameter) throws Exception;

	/**
	 * @description  모바일 로그인을 처리 전, 중복 로그인을 체크한다. (팝업 띄우기 위함)
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName 중복로그인체크처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> mobileUserLoginConfirm(RequestParameter parameter) throws Exception;

	/**
	 * @description  좌측 슬라이드 메뉴의 로그아웃을 처리한다.
	 * @param RequestParameter parameter
	 * @return boolean 처리결과
	 * @throws Exception
	 * @methodKorName 로그아웃처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean mobileUserLogout(RequestParameter parameter) throws Exception;
	
	
	/**
	 * @description  모바일 로그인 상태를 체크한다. (팝업 띄우기 위함)
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 상태결과
	 * @throws Exception
	 * @methodKorName 로그인상태처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> mobileUserLoginStatus(RequestParameter parameter) throws Exception;
	

	/**
	 * @description  회원가입 시, 단지 리스트를 조회한다. (콤보 박스)
	 * @param RequestParameter parameter
	 * @return List<Object> 처리결과
	 * @throws Exception
	 * @methodKorName 단지리스트조회
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<Object> listComplexInfo(RequestParameter parameter) throws Exception;

	/**
	 * @description  회원가입 시, 동 리스트를 조회한다. (콤보 박스)
	 * @param RequestParameter parameter
	 * @return List<Object> 처리결과
	 * @throws Exception
	 * @methodKorName 동리스트조회
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<Object> listDongInfo(RequestParameter parameter) throws Exception;

	/**
	 * @description  회원가입 시, 호 리스트를 조회한다. (콤보 박스)
	 * @param RequestParameter parameter
	 * @return List<Object> 처리결과
	 * @throws Exception
	 * @methodKorName 호리스트조회
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<Object> listHoInfo(RequestParameter parameter) throws Exception;

	/**
	 * @description  회원가입 시, ID 중복확인을 처리한다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName ID중복확인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> checkUserId(RequestParameter parameter) throws Exception;

	/**
	 * @description  회원가입 시, ID 찾기를 처리한다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName ID찾기
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> searchUserId(RequestParameter parameter) throws Exception;

	/**
	 * @description  로그인 화면 및 설정-계정 설정 화면에서 비밀번호 찾기를 처리한다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName 비밀번호찾기
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> searchUserPw(RequestParameter parameter) throws Exception;

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
	 * @description  회원가입 / 로그인-비밀번호 찾기 / 설정-계정 설정-비밀번호 찾기 시, 인증번호 요청을 처리한다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호요청
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> sendCertificationNumber(RequestParameter parameter) throws Exception;

	/**
	 * @description  인증번호 확인을 처리힌다.
	 * @param RequestParameter parameter
	 * @return Map<String, Object> 처리결과
	 * @throws Exception
	 * @methodKorName 인증번호확인
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> confirmCertificationNumber(RequestParameter parameter) throws Exception;

		
	
	
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
	public Map<String, Object> tokenUpdate(RequestParameter parameter) throws Exception;
	

	
	
	
	
	/**
	 * @description  Token을 검증한다.                       
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 상태결과
	 * @throws Exception
	 * @methodKorName 모바일사용자로그인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> tokenVerify(RequestParameter parameter) throws Exception;
	
	
	
	
	
	
	
	/**
	 * @description  Token을 발행한다.                       
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 상태결과
	 * @throws Exception
	 * @methodKorName 모바일사용자로그인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void tokenIssue(Map<String, Object> phomeIdMap) throws Exception;
	
	
	
	
	
	
	/**
	 * @description  Token을 파괴한다.                       
	 * @param RequestParameter parameter, ModelMap model
	 * @return Map<String, Object> resMap 상태결과
	 * @throws Exception
	 * @methodKorName 모바일사용자로그인처리
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	//public Map<String, Object> tokenDestory(RequestParameter.java parameter) throws Exception
	public String tokenDestory(String pToken) throws Exception ;
	
	/**
	 * @description  회원가입 시 개인정보 처리 Url 리턴
	 * @param RequestParameter parameter
	 * @return List<Object> 처리결과
	 * @throws Exception
	 * @methodKorName 인정보 처리 Url 리턴
	 * <!-- begin-user-doc -->
     *
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<Object> getUserInfoProcess(RequestParameter parameter) throws Exception;


}