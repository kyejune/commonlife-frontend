package com.kolon.common.exception;


import com.kolon.common.model.ErrorInfo;
import com.kolon.common.util.DateUtil;

public class UserSysException
        extends Exception
{
    private ErrorInfo errorVO = new ErrorInfo();

    public UserSysException(String className, String methodName)
    {
        this.errorVO.setClassName(className);
        this.errorVO.setMethodName(methodName);
        this.errorVO.setDateTime(DateUtil.getDateTime());
    }

    public UserSysException(String className, String methodName, Exception originException)
    {
        super(originException);
        this.errorVO.setClassName(className);
        this.errorVO.setMethodName(methodName);
        this.errorVO.setDateTime(DateUtil.getDateTime());
        this.errorVO.setOriginException(originException);
    }

    public ErrorInfo getErrorVO()
    {
        return this.errorVO;
    }

    public void setErrorVO(ErrorInfo errorVO)
    {
        this.errorVO = errorVO;
    }
}