package com.kolon.comlife.properties.service.impl;

import com.kolon.comlife.properties.model.PropertiesInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Properties Data Access Object
 * @author nacsde
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  ----------   --------    ---------------------------
 *   2017-07-31     조신득          최초 생성
 * </pre>
 */
@Repository("propertiesDAO")
public class PropertiesDAO {
    @Resource
    private SqlSession sqlSession;

    /**
     * 이메일 발송 프로퍼티 리스트 조회
     * @param propertiesInfo
     * @return
     */
    public List<PropertiesInfo> selectPropertiesSendEmailList(PropertiesInfo propertiesInfo) {
        return sqlSession.selectList("Properties.selectPropertiesSendEmailList", propertiesInfo);
    }

    /**
     * 프로퍼티 리스트 조회
     * @param propertiesInfo
     * @return
     */
    public List<PropertiesInfo> selectPropertiesList(PropertiesInfo propertiesInfo) {
        return sqlSession.selectList("Properties.selectPropertiesList", propertiesInfo);
    }

    /**
     * 프로퍼티 상세 조회
     * @param propertiesInfo
     * @return
     */
    public PropertiesInfo selectPropertiesDetail(PropertiesInfo propertiesInfo) {
        return sqlSession.selectOne("Properties.selectPropertiesDetail", propertiesInfo);
    }



    /**
     * 프로퍼티 등록
     * @param propertiesInfo
     * @return
     */
    public int insertProperties(PropertiesInfo propertiesInfo) {
        return sqlSession.insert("Properties.insertProperties", propertiesInfo);
    }

    /**
     * 프로퍼티 수정
     * @param propertiesInfo
     * @return
     */
    public int updateProperties(PropertiesInfo propertiesInfo) {
        return sqlSession.update("Properties.updateProperties", propertiesInfo);
    }


}
