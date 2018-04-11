package com.kolon.common.messaging.service;

import com.kolon.common.exception.SmsException;

public interface SmsSenderService {
    void send( String cellNumber, String message ) throws SmsException ;
}
