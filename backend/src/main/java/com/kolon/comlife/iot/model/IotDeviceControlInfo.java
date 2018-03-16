package com.kolon.comlife.iot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IotDeviceControlInfo {
    private List<Map<String, Object>> informations;

    public IotDeviceControlInfo() {
        super();
        informations = new ArrayList<>();
    }

    public List<Map<String, Object>> getInformations() {
        return informations;
    }

    public void setInformations(List<Map<String, Object>> informations) {
        this.informations = informations;
    }
}
