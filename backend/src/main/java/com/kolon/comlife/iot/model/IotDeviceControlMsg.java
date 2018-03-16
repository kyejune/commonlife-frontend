package com.kolon.comlife.iot.model;

// Iot 기기 제어에 사용하는 프로토콜 키 및 값
public class IotDeviceControlMsg {
    private String protcKey;  // Protocol Key
    private Object value;     // Value

    public IotDeviceControlMsg() {
        super();
    }

    public String getProtcKey() {
        return protcKey;
    }

    public void setProtcKey(String protcKey) {
        this.protcKey = protcKey;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
