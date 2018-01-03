package com.kolon.common.model;


import com.kolon.common.util.StringUtil;

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
        this.className = StringUtil.nvl(str);
    }

    public String getMethodName()
    {
        return this.methodName;
    }

    public void setMethodName(String str)
    {
        this.methodName = StringUtil.nvl(str);
    }

    public String getErrorCode()
    {
        return this.errorCode;
    }

    public void setErrorCode(String str)
    {
        this.errorCode = StringUtil.nvl(str);
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    public void setErrorMessage(String str)
    {
        this.errorMessage = StringUtil.nvl(str);
    }

    public String getDateTime()
    {
        return this.dateTime;
    }

    public void setDateTime(String str)
    {
        this.dateTime = StringUtil.nvl(str);
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
