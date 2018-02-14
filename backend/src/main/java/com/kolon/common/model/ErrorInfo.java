package com.kolon.common.model;


import com.kolon.common.util.StringUtils;

/**
 */
public class ErrorInfo {
    private String className = "";
    private String methodName = "";
    private String errorCode = "";
    private String errorMessage = "";
    private String dateTime = "";
    private Object paramObj = null;
    private Exception originException = null;

    public String getClassName()
    {
        return this.className;
    }

    public void setClassName(String str)
    {
        this.className = StringUtils.nvl(str);
    }

    public String getMethodName()
    {
        return this.methodName;
    }

    public void setMethodName(String str)
    {
        this.methodName = StringUtils.nvl(str);
    }

    public String getErrorCode()
    {
        return this.errorCode;
    }

    public void setErrorCode(String str)
    {
        this.errorCode = StringUtils.nvl(str);
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    public void setErrorMessage(String str)
    {
        this.errorMessage = StringUtils.nvl(str);
    }

    public String getDateTime()
    {
        return this.dateTime;
    }

    public void setDateTime(String str)
    {
        this.dateTime = StringUtils.nvl(str);
    }

    public Object getParamObj()
    {
        return this.paramObj;
    }

    public void setParamObj(Object obj)
    {
        this.paramObj = obj;
    }

    public Exception getOriginException()
    {
        return this.originException;
    }

    public void setOriginException(Exception except)
    {
        this.originException = except;
    }
}
