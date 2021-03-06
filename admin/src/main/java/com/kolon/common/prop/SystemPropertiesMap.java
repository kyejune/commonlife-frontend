package com.kolon.common.prop;

/**
 * Created by agcdDev on 2017-07-07.
 */

import com.google.common.base.Splitter;
import org.apache.commons.collections.IteratorUtils;

import java.util.HashMap;

public class SystemPropertiesMap
{
    private static SystemPropertiesMap systemPropertiesMap;
    private static HashMap<Object, Object> propertiesValues;

    public static SystemPropertiesMap getInstance()
    {
        synchronized (SystemPropertiesMap.class)
        {
            if (systemPropertiesMap == null) {
                systemPropertiesMap = new SystemPropertiesMap();
            }
        }
        return systemPropertiesMap;
    }

    public void setProperties(HashMap<Object, Object> propertiesValues)
    {
        this.propertiesValues = propertiesValues;
    }

    public HashMap<Object, Object> getProperties()
    {
        return propertiesValues;
    }

    public String getValue(String key)
    {
        String resultStr = "";
        if (propertiesValues.get(key) != null) {
            resultStr = propertiesValues.get(key).toString();
        }
        return resultStr;
    }

    public boolean hasValue(String key, String value)
    {
        return hasValue(key, value, ",");
    }

    public boolean hasValue(String key, String value, String separator)
    {
        String resultStr = "";
        if (propertiesValues.get(key) != null)
        {
            resultStr = propertiesValues.get(key).toString();

            Iterable<String> result = Splitter.on(separator).trimResults().omitEmptyStrings().split(resultStr);
            return IteratorUtils.toList(result.iterator()).contains(new String(value));
        }
        return false;
    }
}