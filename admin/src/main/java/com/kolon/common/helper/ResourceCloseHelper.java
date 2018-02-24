package com.kolon.common.helper;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

public class ResourceCloseHelper
{
    private static Logger logger = LoggerFactory.getLogger(ResourceCloseHelper.class);

    public static void close(Closeable... resources)
    {
        if (!ArrayUtils.isEmpty(resources)) {
            for (Closeable resource : resources) {
                if (resource != null) {
                    try
                    {
                        resource.close();
                    }
                    catch (Exception ignore)
                    {
                        logger.error("해당 객체 close error! >> " + ignore.getMessage());
                    }
                }
            }
        }
    }
}
