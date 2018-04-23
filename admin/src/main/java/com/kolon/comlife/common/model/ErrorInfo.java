package com.kolon.comlife.common.model;

public class ErrorInfo extends SimpleErrorInfo {

    private String reason;

    public ErrorInfo() {
        super();
    }

    public ErrorInfo(String msg) {
        super(msg);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

