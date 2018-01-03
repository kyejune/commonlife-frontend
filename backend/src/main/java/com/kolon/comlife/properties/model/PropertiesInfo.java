package com.kolon.comlife.properties.model;

import com.kolon.common.model.BaseUserInfo;
import org.apache.ibatis.type.Alias;

/**
 * 프로퍼티 Value Object
 */
@Alias("propertiesInfo")
public class PropertiesInfo extends BaseUserInfo {
    private String propGroup;
    private String propKey;
    private String propValue;
    private String propDesc;

    private String useYn;
    private String regDttm;
    private String regUserId;
    private String updDttm;
    private String updUserId;


    public String getPropGroup() {
        return propGroup;
    }

    public void setPropGroup(String propGroup) {
        this.propGroup = propGroup;
    }

    public String getPropKey() {
        return propKey;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    public String getPropDesc() {
        return propDesc;
    }

    public void setPropDesc(String propDesc) {
        this.propDesc = propDesc;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(String regDttm) {
        this.regDttm = regDttm;
    }

    public String getRegUserId() {
        return regUserId;
    }

    public void setRegUserId(String regUserId) {
        this.regUserId = regUserId;
    }

    public String getUpdDttm() {
        return updDttm;
    }

    public void setUpdDttm(String updDttm) {
        this.updDttm = updDttm;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }
}
