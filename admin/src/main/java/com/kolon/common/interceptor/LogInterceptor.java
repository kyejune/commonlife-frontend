package com.kolon.common.interceptor;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/*
 */
public class LogInterceptor
        extends HandlerInterceptorAdapter
{
    private final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception
    {
        if (this.logger.isInfoEnabled())
        {
            this.logger.info("====================Request Info====================");
            this.logger.info("request.getRequestURI() : " + request.getRequestURI());
            this.logger.info("request.getRequestURL() : " + request.getRequestURL());
            this.logger.info("request.getServletPath() : " + request.getServletPath());
            this.logger.info("request.getContextPath() : " + request.getContextPath());
            this.logger.info("request.getPathInfo() : " + request.getPathInfo());
            this.logger.info("request.getMethod() : " + request.getMethod());
            this.logger.info("this.getClass().getName() : " + handler.getClass().getName());
            this.logger.info("request.getRemoteAddr() : " + request.getRemoteAddr());

            Enumeration enums = request.getParameterNames();
            while (enums.hasMoreElements())
            {
                String paramName = (String)enums.nextElement();
                String[] parameters = request.getParameterValues(paramName);
                if (ArrayUtils.isNotEmpty(parameters)) {
                    for (String parameter : parameters) {
                        this.logger.info("parameter ::: [" + paramName + "] " + parameter);
                    }
                }
            }
            this.logger.info("====================Request Info====================");
        }
        return true;
    }
}
