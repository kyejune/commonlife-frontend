package com.kolon.comlife.properties.web;


import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.common.prop.KeyValueMap;
import com.kolon.common.prop.ServicePropertiesMap;
import com.kolon.common.prop.SystemPropertiesMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * todo: *중요* 인증된 관리자-서버에서 호출할 수 있도록 접근을 제안해야 합니다!!
 *
 * Service 및 System Properties Map 정보를 가져오거나 Service Properties Map을 갱신함
 *  - 관리 용도의 API 입니다.
 *  - 해당 API 호출은 서비스의 성능저하를 야기할 있을 수 있습니다. 기능 용도로 사용하지 않도록 합니다.
 */

@Controller
@RequestMapping("/management/properties/*")
public class PropertiesController {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesController.class);

    @Autowired
    private ServicePropertiesMap serviceProp;

    private SystemPropertiesMap  systemProp = SystemPropertiesMap.getInstance();

    @GetMapping(
            value="/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPropertiesMapList() {
        String ret = "";

        StringBuilder builder = new StringBuilder();
        builder
                .append("{\"data\":[\"")
                .append( systemProp.getClass().getSimpleName() )
                .append("\",\"")
                .append( serviceProp.getClass().getSimpleName() )
                .append("\"]}");

        return ResponseEntity.status(HttpStatus.OK).body(builder.toString());
    }

    @GetMapping(
            value="/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPropertiesDataList(@PathVariable("name") String name ) {
        Map    result = new TreeMap();
        String groupKey;
        String key;
        String value;

        if ( name.equals(systemProp.getClass().getSimpleName()) ) {
            result.putAll( systemProp.getProperties() );
            logger.debug( ">>>>> System Properties:" + result);
        } else if( name.equals(serviceProp.getClass().getSimpleName()) ) {
            KeyValueMap groupProp;

            Iterator<String> groupsItr = serviceProp.keySet().iterator();
            while( groupsItr.hasNext() ) {
                groupKey = groupsItr.next();
                groupProp = serviceProp.getByGroup( groupKey );
                Iterator<String> keyItr = groupProp.keySet().iterator();
                while( keyItr.hasNext() ) {
                    key = keyItr.next();
                    value = groupProp.getValue( key );
                    result.put( (groupKey + "." + key), value );
                }
            }

            logger.debug( ">>>>> Service Properties:" + result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /**
     * Service Properties를 테이블에서 읽어와 Service Properties Map을 재구성(리로딩)합니다.
     *
     * @param name
     * @return
     */
    @PutMapping(
            value="/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity reloadPropertiesDataList(@PathVariable("name") String name ) {

        if ( name.equals(systemProp.getClass().getSimpleName()) ) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new SimpleErrorInfo("해당 기능은 지원하지 않습니다."));
        } else if( name.equals(serviceProp.getClass().getSimpleName()) ) {
            serviceProp.reload();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SimpleMsgInfo("프로퍼티 리로딩이 완료되었습니다"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
