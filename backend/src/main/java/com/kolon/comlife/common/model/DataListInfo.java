package com.kolon.comlife.common.model;

import java.util.List;

public class DataListInfo extends SimpleMsgInfo {
    private List data;


    public DataListInfo() {
        this.data = null;
    }

    public DataListInfo(List data) {
        this.data = data;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
