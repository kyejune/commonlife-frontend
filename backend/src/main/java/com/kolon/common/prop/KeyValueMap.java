package com.kolon.common.prop;

import java.util.HashMap;

public class KeyValueMap {
    private HashMap< String, String > map;
    public KeyValueMap() {
        this.map = new HashMap<>();
    }

    public String getValue(String key) { return this.map.get(key); }
    public void setValue(String key, String value) { this.map.put(key, value); }
}