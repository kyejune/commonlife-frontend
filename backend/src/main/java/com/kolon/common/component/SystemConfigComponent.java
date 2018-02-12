package com.kolon.common.component;

import com.kolon.common.helper.ResourceCloseHelper;
import com.kolon.common.prop.SystemPropertiesMap;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.Closeable;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * 시스템 설정 컴포넌트 확장
 * @author Cho Sin Deuck
 * @version 1.0
 * @see <pre> * </pre>
 */
@Component
public class SystemConfigComponent {
    public static final Logger logger = LoggerFactory.getLogger(SystemConfigComponent.class);


    private HashMap<Object, Object> tempMap = null;

    private static List<String> properties;

    public void setProperties(List<String> properties)
    {
        this.properties = properties;
    }

    public void init() throws ServletException
    {
        initProperties();
    }

    private void initProperties()
    {
        logger.info("########################################");
        logger.info("## 시스템 프로퍼티 설정값을 초기화 합니다.");
        try
        {
            if (CollectionUtils.isNotEmpty(this.properties)) {
                for (String property : this.properties) {
                    propertiesLoad(property);
                }
            } else {
                logger.info("## 시스템 프로퍼티 파일이 존재하지 않습니다.");
            }
        }
        catch (Exception ex)
        {
            logger.error("ERROR",ex);
        }
        logger.info("## 시스템 설정값을 정상적으로 초기화 하였습니다.");
        logger.info("########################################");
    }

    private void propertiesLoad(String propertiesFile)
            throws Exception
    {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(propertiesFile);
        Properties props = new Properties();
        props.load(stream);
        SystemPropertiesMap propMap = SystemPropertiesMap.getInstance();
        this.tempMap = propMap.getProperties();
        if (this.tempMap == null) {
            this.tempMap = new HashMap();
        }
        Enumeration<Object> element = props.keys();
        while (element.hasMoreElements())
        {
            Object object = element.nextElement();

            this.tempMap.put(object.toString(), props.get(object.toString()));

            logger.info("## " + object.toString() + " :::: " + props.get(object.toString()));
        }
        ResourceCloseHelper.close(new Closeable[] { stream });
        propMap.setProperties(this.tempMap);
    }

}
