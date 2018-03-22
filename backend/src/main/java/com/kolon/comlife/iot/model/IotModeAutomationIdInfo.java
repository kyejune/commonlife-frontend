package com.kolon.comlife.iot.model;

public class IotModeAutomationIdInfo extends IotBaseInfo {
    private int automationId;

    public IotModeAutomationIdInfo() {
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
