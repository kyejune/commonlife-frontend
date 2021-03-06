package com.kolon.comlife.iot.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class IotModeAutomationInfo extends IotBaseInfo {
    @SerializedName("SCNA_IF_THINGS")
    private List<Map<String, Object>> scnaIfThings; // 기기의 상태 속성
    @SerializedName("SCNA_IF_SPC")
    private List<Map<String, Object>> scnaIfSpc;    // ??
    @SerializedName("SCNA_IF_APLY")
    private List<Map<String, Object>> scnaIfAply;  // 시간
    @SerializedName("SCAN_IF_OPTION")
    private List<Map<String, Object>> scnaIfOption; // 조건에서 사용하는 옵션 종류 e.g. < > =  ...
    @SerializedName("SCNA_THINGS")
    private List<Map<String, Object>> scnaThings;   // 동작 기기
    @SerializedName("SCNA")
    private List<Map<String, Object>> scna; // 조건에서 사용하는 옵션 종류 e.g. < > =  ...

    public IotModeAutomationInfo() {
        super();
    }

    public List<Map<String, Object>> getScnaIfThings() {
        return scnaIfThings;
    }

    public void setScnaIfThings(List<Map<String, Object>> scnaIfThings) {
        this.scnaIfThings = scnaIfThings;
    }

    public List<Map<String, Object>> getScnaIfSpc() {
        return scnaIfSpc;
    }

    public void setScnaIfSpc(List<Map<String, Object>> scnaIfSpc) {
        this.scnaIfSpc = scnaIfSpc;
    }

    public List<Map<String, Object>> getScnaIfAply() {
        return scnaIfAply;
    }

    public void setScnaIfAply(List<Map<String, Object>> scnaIfAply) {
        this.scnaIfAply = scnaIfAply;
    }

    public List<Map<String, Object>> getScnaIfOption() {
        return scnaIfOption;
    }

    public void setScnaIfOption(List<Map<String, Object>> scnaIfOption) {
        this.scnaIfOption = scnaIfOption;
    }

    public List<Map<String, Object>> getScnaThings() {
        return scnaThings;
    }

    public void setScnaThings(List<Map<String, Object>> scnaThings) {
        this.scnaThings = scnaThings;
    }

    public List<Map<String, Object>> getScna() {
        return scna;
    }

    public void setScna(List<Map<String, Object>> scna) {
        this.scna = scna;
    }
}
