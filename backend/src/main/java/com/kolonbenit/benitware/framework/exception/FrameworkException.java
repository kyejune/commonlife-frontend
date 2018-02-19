package com.kolonbenit.benitware.framework.exception;


public class FrameworkException extends RuntimeException {
    private static final long serialVersionUID = 6842469462694212596L;
    private String msgKey;
    private String nextView;
    private Object msgArg;

    public String getMsgKey() {
        return this.msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public String getNextView() {
        return this.nextView;
    }

    public void setNextView(String nextView) {
        this.nextView = nextView;
    }

    public Object getMsgArg() {
        return this.msgArg;
    }

    public void setMsgArg(Object msgArg) {
        this.msgArg = msgArg;
    }

    public FrameworkException(Throwable cause, String msgKey) {
        this.initCause(cause);
        this.msgKey = msgKey;
    }

    public FrameworkException(Throwable cause, String msgKey, Object msgArg) {
        this.initCause(cause);
        this.msgKey = msgKey;
        this.msgArg = msgArg;
    }

    public FrameworkException(Throwable cause, String msgKey, Object msgArg, String nextView) {
        this.initCause(cause);
        this.msgKey = msgKey;
        this.msgArg = msgArg;
        this.nextView = nextView;
    }
}
