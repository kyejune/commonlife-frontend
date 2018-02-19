package com.kolon.common.util;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class WebUtil extends org.springframework.web.util.WebUtils
{
    public static final String ACTION_MESSAGE = "actionMessage";
    public static final String ACTION_STATUS_PARAM = "actionStatus";
    public static final String ACTION_STATUS_FAILED = "failed";
    public static final String ACTION_STATUS_SUCCESS = "success";
    public static final String ACTION_STATUS_VALIDATOR_ERROR = "validatorError";

    public static String getUrlExtension(HttpServletRequest request)
    {
        return FilenameUtils.getExtension(request.getServletPath());
    }

    public static String getUrlFullPath(HttpServletRequest request)
    {
        return FilenameUtils.getFullPath(request.getServletPath());
    }

    public static void setActionStatus(HttpServletRequest request, String actionStatus)
    {
        request.setAttribute("actionStatus", actionStatus);
    }

    public static void setActionMessage(HttpServletRequest request, String message)
    {
        request.setAttribute("actionMessage", message);
    }

    public static void setAjaxAction(HttpServletResponse response, int actionStatus, String actionMessage)
    {
        setAjaxActionStatus(response, actionStatus);
        setAjaxActionMessage(response, actionMessage);
    }

    public static void setAjaxAction(HttpServletResponse response, int actionStatus, Map<String, Object> paramMap)
    {
        setAjaxActionStatus(response, actionStatus);
        setAjaxActionParam(response, paramMap);
    }

    public static void setAjaxActionStatus(HttpServletResponse response, int actionStatus)
    {
        response.setStatus(actionStatus);
    }

    public static void setAjaxActionMessage(HttpServletResponse response, String actionMessage)
    {
        Map<String, Object> actionParam = new HashMap();
        actionParam.put("actionMessage", actionMessage);
        setAjaxActionParam(response, actionParam);
    }

    public static void setAjaxActionParam(HttpServletResponse response, Map<String, Object> actionParamMap)
    {
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter writer = null;
        try
        {
            writer = response.getWriter();
            writer.print(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(actionParamMap));
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> setParam(HttpServletRequest request)
    {
        Map<String, Object> map = new HashMap();
        if ((request instanceof DefaultMultipartHttpServletRequest))
        {
            DefaultMultipartHttpServletRequest multi = (DefaultMultipartHttpServletRequest)request;

            Enumeration<String> en = multi.getParameterNames();
            while (en.hasMoreElements())
            {
                String name = (String)en.nextElement();
                if (multi.getParameterValues(name).length > 1) {
                    map.put(name, multi.getParameterValues(name));
                } else {
                    map.put(name, multi.getParameter(name));
                }
            }
        }
        else
        {
            Enumeration<String> en = request.getParameterNames();
            while (en.hasMoreElements())
            {
                String name = (String)en.nextElement();
                if (request.getParameterValues(name).length > 1) {
                    map.put(name, request.getParameterValues(name));
                } else {
                    map.put(name, request.getParameter(name));
                }
            }
        }
        return map;
    }

    public static String clearXSSMinimum(String value)
    {
        if ((value == null) || (value.trim().equals(""))) {
            return "";
        }
        String returnValue = value;

        returnValue = returnValue.replaceAll("&", "&amp;");
        returnValue = returnValue.replaceAll("<", "&lt;");
        returnValue = returnValue.replaceAll(">", "&gt;");
        returnValue = returnValue.replaceAll("\"", "&#34;");
        returnValue = returnValue.replaceAll("'", "&#39;");
        returnValue = returnValue.replaceAll(".", "&#46;");
        returnValue = returnValue.replaceAll("%2E", "&#46;");
        returnValue = returnValue.replaceAll("%2F", "&#47;");
        return returnValue;
    }

    public static String clearXSSMaximum(String value)
    {
        String returnValue = value;
        returnValue = clearXSSMinimum(returnValue);

        returnValue = returnValue.replaceAll("%00", null);

        returnValue = returnValue.replaceAll("%", "&#37;");

        returnValue = returnValue.replaceAll("\\.\\./", "");
        returnValue = returnValue.replaceAll("\\.\\.\\\\", "");
        returnValue = returnValue.replaceAll("\\./", "");
        returnValue = returnValue.replaceAll("%2F", "");

        return returnValue;
    }

    public static String filePathBlackList(String value)
    {
        String returnValue = value;
        if ((returnValue == null) || (returnValue.trim().equals(""))) {
            return "";
        }
        returnValue = returnValue.replaceAll("\\.\\./", "");
        returnValue = returnValue.replaceAll("\\.\\.\\\\", "");

        return returnValue;
    }

    public static String filePathReplaceAll(String value)
    {
        String returnValue = value;
        if ((returnValue == null) || (returnValue.trim().equals(""))) {
            return "";
        }
        returnValue = returnValue.replaceAll("/", "");
        returnValue = returnValue.replaceAll("\\", "");
        returnValue = returnValue.replaceAll("\\.\\.", "");
        returnValue = returnValue.replaceAll("&", "");

        return returnValue;
    }

    public static String filePathWhiteList(String value)
    {
        return value;
    }

    public static boolean isIPAddress(String str)
    {
        Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

        return ipPattern.matcher(str).matches();
    }

    public static String removeCRLF(String parameter)
    {
        return parameter.replaceAll("\r", "").replaceAll("\n", "");
    }

    public static String removeSQLInjectionRisk(String parameter)
    {
        return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("%", "").replaceAll(";", "").replaceAll("-", "").replaceAll("\\+", "").replaceAll(",", "");
    }

    public static String removeOSCmdRisk(String parameter)
    {
        return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("|", "").replaceAll(";", "");
    }
}