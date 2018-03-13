package com.kolon.comlife.iot.model.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeviceMsg {
    private List<Map> informations;

    public DeviceMsg() {
        this.informations = new ArrayList();
    }

    public DeviceMsg(List<Map> informations) {
        this.informations = informations;
    }

    public List<Map> getInformations() {
        return informations;
    }

    public void setInformations(List<Map> informations) {
        this.informations = informations;
    }
}
