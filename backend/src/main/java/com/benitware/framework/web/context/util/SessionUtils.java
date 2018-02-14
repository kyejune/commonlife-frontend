//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.benitware.framework.web.context.util;

import org.springframework.web.context.request.RequestContextHolder;

public class SessionUtils {
    public SessionUtils() {
    }

    public static Object getAttribute(String key) {
        return RequestContextHolder.getRequestAttributes().getAttribute(key, 1);
    }

    public static void setAttribute(String key, Object obj) {
        RequestContextHolder.getRequestAttributes().setAttribute(key, obj, 1);
    }
}
