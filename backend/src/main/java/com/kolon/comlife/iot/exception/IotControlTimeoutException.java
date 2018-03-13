package com.kolon.comlife.iot.exception;

import java.net.SocketException;

public class IotControlTimeoutException extends Exception {
    public IotControlTimeoutException() {
    }

    public IotControlTimeoutException(String message) {
        super(message);
    }
}
