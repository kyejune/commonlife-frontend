package com.kolon.comlife.iot.model;

public class IotAutomationIdInfo extends IotBaseInfo {
    private int automationId;

    public IotAutomationIdInfo() {
        super();
        this.automationId = -1;
    }

    public int getAutomationId() {
        return automationId;
    }

    public void setAutomationId(int automationId) {
        this.automationId = automationId;
    }
}
