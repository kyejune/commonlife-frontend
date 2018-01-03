package com.kolon.comlife.properties.service;

import com.kolon.comlife.properties.model.PropertiesInfo;

import java.util.List;

/**
 * 프로퍼티 서비스 인터페이스
 * @author nacsde
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  ----------   --------    ---------------------------
 *   2017-07-31    조신득          최초 생성
 * </pre>
 */
public interface PropertiesService {
    /**
     * 이메일 발송 프로퍼티 리스트 조회
     * @param propertiesInfo
     * @return
     */
    List<PropertiesInfo> selectPropertiesSendEmailList(PropertiesInfo propertiesInfo);

    /**
     * 프로퍼티 리스트 조회
     * @param propertiesInfo
     * @return
     */
    List<PropertiesInfo> selectPropertiesList(PropertiesInfo propertiesInfo);

    /**
     * 프로퍼티 상세 조회
     * @param propertiesInfo
     * @return
     */
    PropertiesInfo selectPropertiesDetail(PropertiesInfo propertiesInfo);


    /**
     * 프로퍼티 등록
     * @param propertiesInfo
     * @return
     */
    int insertProperties(PropertiesInfo propertiesInfo);


    /**
     * 프로퍼티 수정
     * @param propertiesInfo
     * @return
     */
    int updateProperties(PropertiesInfo propertiesInfo);



}
