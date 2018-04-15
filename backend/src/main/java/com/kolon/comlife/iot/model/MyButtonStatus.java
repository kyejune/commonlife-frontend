package com.kolon.comlife.iot.model;

public class MyButtonStatus {

    String  key;
    boolean status;
    String  text;

    public MyButtonStatus(String key, boolean status, String text) {
        this.key = key;
        this.status = status;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
