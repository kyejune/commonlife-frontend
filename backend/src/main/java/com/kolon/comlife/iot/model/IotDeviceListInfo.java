package com.kolon.comlife.iot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IotDeviceListInfo extends IotBaseInfo {

    private List<Map<String, Object>> data;

    public IotDeviceListInfo() {
        super();
        this.data = new ArrayList();
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

}
