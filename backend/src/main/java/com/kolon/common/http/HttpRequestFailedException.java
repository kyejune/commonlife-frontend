package com.kolon.common.http;

public class HttpRequestFailedException extends Exception {
    private int statusCode;

    public HttpRequestFailedException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
