package com.kolon.comlife.properties.service.impl;

import com.kolon.comlife.properties.model.PropertiesInfo;
import com.kolon.comlife.properties.service.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 프로퍼티 서비스 구현체
 * @author nacsde
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  ----------   --------    ---------------------------
 *   2017-07-31   조신득          최초 생성
 * </pre>
 */
@Service("propertiesService")
public class PropertiesServiceImpl implements PropertiesService {
    public static final Logger logger = LoggerFactory.getLogger(PropertiesServiceImpl.class);

    @Resource(name = "propertiesDAO")
    private PropertiesDAO propertiesDAO;

    @Override
    public List<PropertiesInfo> selectPropertiesSendEmailList(PropertiesInfo propertiesInfo) {
        return propertiesDAO.selectPropertiesSendEmailList(propertiesInfo);
    }

    @Override
    public List<PropertiesInfo> selectPropertiesList(PropertiesInfo propertiesInfo) {
        return propertiesDAO.selectPropertiesList(propertiesInfo);
    }

    @Override
    public PropertiesInfo selectPropertiesDetail(PropertiesInfo propertiesInfo) {
        return propertiesDAO.selectPropertiesDetail(propertiesInfo);
    }

    @Override
    public int insertProperties(PropertiesInfo propertiesInfo) {
        return propertiesDAO.insertProperties(propertiesInfo);
    }

    @Override
    public int updateProperties(PropertiesInfo propertiesInfo) {
        return propertiesDAO.updateProperties(propertiesInfo);
    }
}
