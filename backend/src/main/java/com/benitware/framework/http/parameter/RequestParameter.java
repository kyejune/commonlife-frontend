package com.benitware.framework.http.parameter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class RequestParameter extends HashMap<String, Object> {

    private final Logger logger = LoggerFactory.getLogger(RequestParameter.class);

    private static final long serialVersionUID = 9200134378586673189L;
    private HttpServletRequest request;

    public RequestParameter() {
    }

    public RequestParameter(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public MultipartHttpServletRequest getMultipartRequest() {
        return (MultipartHttpServletRequest)this.request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String val(String key) {
        return (String)this.get(key);
    }

    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(this.getString(key));
    }

    public String getString(String key) {
        Object value = this.get(key);
        logger.debug(">>>> key> " + key);
        logger.debug(">>>> value> " + value);
        return (String)value ;
    }

    public String getString(String key, String defaultValue) {
        String value = this.getString(key);
        return value != null && !"".equals(value) ? value : defaultValue;
    }

    public String[] getStrings(String key) {
        Object value = this.get(key);
        if (value instanceof String[]) {
            return (String[])value;
        } else {
            String[] values = new String[]{(String)value};
            return values;
        }
    }

    public Object add(String key, Object value) {
        return this.put(key, value);
    }

    public Iterator<String> keys() {
        return this.keySet().iterator();
    }

    public boolean hasText(String key) {
        return StringUtils.hasText(this.getString(key));
    }

    public List getList(String key) {
        return (List)this.get(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(this.getString(key));
    }

    public int getInt(String key, int defaultValue) {
        String returnValue = this.getString(key, "-1");
        if ("-1".equals(returnValue)) {
            this.add(key, defaultValue);
            return defaultValue;
        } else {
            return Integer.parseInt(this.getString(key));
        }
    }

    public Integer getInteger(String key) {
        return Integer.parseInt(this.getString(key));
    }

    public Integer getInteger(String key, Integer defaultValue) {
        Integer value = this.getInteger(key);
        return value == null ? defaultValue : value;
    }

    public Long getLong(String key) {
        String s = this.getString(key);
        return s == null ? null : Long.parseLong(s);
    }

    public Long getLong(String key, Long defaultValue) {
        String s = this.getString(key);
        return s == null ? defaultValue : Long.parseLong(s);
    }

    public BigInteger getBigInteger(String key) {
        String s = this.getString(key);
        return s == null ? null : new BigInteger(s);
    }

    public Float getFloat(String key) {
        String s = this.getString(key);
        return s == null ? null : Float.parseFloat(s);
    }

    public Float getFloat(String key, Float defaultValue) {
        String s = this.getString(key);
        return s == null ? defaultValue : Float.parseFloat(s);
    }

    public Double getDouble(String key) {
        String s = this.getString(key);
        return s == null ? null : Double.parseDouble(s);
    }

    public Double getDouble(String key, Double defaultValue) {
        Double value = this.getDouble(key);
        return value == null ? defaultValue : value;
    }

    public <T> T get(String key, Class<T> requiredType) {
        return (T)this.get(key);
    }

    public Map<String, Object> getJsonToMap(String key) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return (Map)mapper.readValue(this.getString(key), Map.class);
    }

    public List<Map<String, Object>> getJsonToList(String key) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return (List)mapper.readValue(this.getString(key), List.class);
    }

    public <T> T getBean(Class<T> clazz) {
        try {
            T bean = clazz.newInstance();
            if (bean == null) {
                throw new ConversionException("Unable to create bean using empty constructor");
            } else {
                BeanUtils.populate(bean, this);
                return bean;
            }
        } catch (Exception var3) {
            System.err.println(var3.getMessage());
            var3.printStackTrace();
            return null;
        }
    }

    public void removeKeyValues(String keyNames) {
        if (keyNames != null) {
            String[] keys = keyNames.split(",");
            if (keys != null && keys.length >= 1) {
                for(int i = 0; i < keys.length; ++i) {
                    this.remove(keys[i]);
                }

            }
        }
    }

    public Boolean equals(String key, Object value) {
        return value == null ? false : value.equals(this.get(key));
    }

    public boolean isSetKeys(String[] keys) {
        String[] var5 = keys;
        int var4 = keys.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String key = var5[var3];
            if (!this.containsKey(key)) {
                return false;
            }
        }

        return true;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();

        String key;
        Object value;
        for(Iterator iterator = this.entrySet().iterator(); iterator.hasNext(); ret.append('\n').append(key).append(": ").append(value)) {
            Entry<String, Object> entry = (Entry)iterator.next();
            key = (String)entry.getKey();
            value = entry.getValue();
            if (value == null) {
                value = "<null>";
            }
        }

        return ret.toString();
    }
}