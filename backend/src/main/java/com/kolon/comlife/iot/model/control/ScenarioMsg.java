package com.kolon.comlife.iot.model.control;

public class ScenarioMsg {

    // "scna_type" : "normal | service_normal | service_confirm | service_cancel | service_return"

    final public static String NORMAL = "normal";
    final public static String SERVICE_NORMAL = "service_normal";
    final public static String SERVICE_CONFIRM = "service_confirm";
    final public static String SERVICE_CANCEL = "service_cancel";
    final public static String SERVICE_RETURN = "service_return";

    private String scna_type;

    public ScenarioMsg() {
        scna_type = null;
    }

    public ScenarioMsg(String scna_type) {
        this.scna_type = scna_type;
    }

    public String getScna_type() {
        return scna_type;
    }

    public void setScna_type(String scna_type) {
        this.scna_type = scna_type;
    }
}
