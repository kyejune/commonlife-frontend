package com.kolon.common.prop;

import com.kolon.comlife.properties.model.PropertiesInfo;
import com.kolon.comlife.properties.service.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component("servicePropertiesMap")
public class ServicePropertiesMap {
    private static final Logger logger = LoggerFactory.getLogger(ServicePropertiesMap.class);

    @Resource
    PropertiesService propertiesService;

    private AtomicReference< HashMap< String, KeyValueMap > > atomicRefPropGroup;

    public ServicePropertiesMap( ) {
        this.atomicRefPropGroup = new AtomicReference<>();
        this.atomicRefPropGroup.set( new HashMap<String, KeyValueMap>() );
    }

    @PostConstruct
    private void reloadInternal() {
        PropertiesInfo               propInfo     = new PropertiesInfo();
        List<PropertiesInfo>         propInfoList = this.propertiesService.selectPropertiesList(propInfo);
        HashMap<String, KeyValueMap> newMap       = new HashMap<>();

        for(PropertiesInfo p : propInfoList) {
            KeyValueMap kv = newMap.get(p.getPropGroup());
            if( kv == null ) {
                kv = new KeyValueMap();
            }
            kv.setValue( p.getPropKey(), p.getPropValue() );
            newMap.put(p.getPropGroup(), kv);

//            System.out.println( ">>>> " + kv.getValue( p.getPropKey() ));
//            System.out.println(
//                    p.getPropGroup() + "." + p.getPropKey() + ":" +
//                    p.getPropValue() + " { " + p.getPropDesc() + "}" );
        }

        atomicRefPropGroup.set(newMap);
    }

    public void reload() {
        this.reloadInternal();
    }

    public KeyValueMap getByGroup(String keyGroup) {
        return atomicRefPropGroup.get().get(keyGroup);
    }

    public String getByKey(String keyGroup, String key) {
        KeyValueMap kvm = atomicRefPropGroup.get().get(keyGroup);
        if (kvm == null) {
            return null;
        }

        return kvm.getValue(key);
    }
}
