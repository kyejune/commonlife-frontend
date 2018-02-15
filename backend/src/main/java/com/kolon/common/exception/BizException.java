package com.kolon.common.exception;


import com.kolon.common.util.DateUtil;

public class BizException
        extends Exception
{
    private String time = null;
    private String className = null;
    private String methodName = null;
    private int errCod = 0;
    private String errId = null;
    private String errMsg = null;
    private String pageURL = null;
    private Object paramObj = null;
    private String modelKey = null;
    private Object model = null;
    private Exception ex = null;

    public BizException()
    {
        this.time = DateUtil.getDateTimeByPattern("yyyy-MM-dd-HH:mm");
    }

    public BizException(String errId)
    {
        this.time = DateUtil.getDateTimeByPattern("yyyy-MM-dd-HH:mm");
        this.errId = errId;
    }

    public BizException(String errId, String errMsg)
    {
        this.time = DateUtil.getDateTimeByPattern("yyyy-MM-dd-HH:mm");
        this.errId = errId;
        this.errMsg = errMsg;
    }

    public BizException(String calssName, String methodName, int errCod, String errMsg)
    {
        this.time = DateUtil.getDateTimeByPattern("yyyy-MM-dd-HH:mm");
        this.className = calssName;
        this.methodName = methodName;
        this.errCod = errCod;
        this.errMsg = errMsg;
    }

    public BizException(String errId, Exception e)
    {
        super(e);
        this.time = DateUtil.getDateTimeByPattern("yyyy-MM-dd-HH:mm");
        this.errId = errId;
        this.ex = e;
    }

    public BizException(String className, String methodName, String errId, String pageURL, Object paramObj)
    {
        this.time = DateUtil.getDateTimeByPattern("yyyy-MM-dd-HH:mm");
        this.className = className;
        this.methodName = methodName;
        this.errId = errId;
        this.pageURL = pageURL;
        this.paramObj = paramObj;
    }

    public BizException(String className, String methodName, String errId, String pageURL, String modelKey, Object model)
    {
        this.time = DateUtil.getDateTimeByPattern("yyyy-MM-dd-HH:mm");
        this.className = className;
        this.methodName = methodName;
        this.errId = errId;
        this.pageURL = pageURL;
        this.modelKey = modelKey;
        this.model = model;
    }

    public BizException(String className, String methodName, String errId, String pageURL, Object paramObj, Exception e)
    {
        super(e);
        this.time = DateUtil.getDateTimeByPattern("yyyy-MM-dd-HH:mm");
        this.className = className;
        this.methodName = methodName;
        this.errId = errId;
        this.pageURL = pageURL;
        this.paramObj = paramObj;
        this.ex = e;
    }

    public Exception getException()
    {
        return this.ex;
    }

    public int getErrCod()
    {
        return this.errCod;
    }

    public String getErrId()
    {
        return this.errId;
    }

    public String getErrMsg()
    {
        return this.errMsg;
    }

    public String getTime()
    {
        return this.time;
    }

    public String getClassName()
    {
        return this.className;
    }

    public String getMethodName()
    {
        return this.methodName;
    }

    public String getPageURL()
    {
        return this.pageURL;
    }

    public Object getParmObj()
    {
        return this.paramObj;
    }

    public String getModelKey()
    {
        return this.modelKey;
    }

    public Object getModel()
    {
        return this.model;
    }
}