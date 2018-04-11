package com.kolon.common.messaging.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolon.common.messaging.service.SmsSenderService;
import com.kolon.common.exception.SmsException;
import com.kolon.common.http.HttpPostRequester;
import com.kolon.common.prop.ServicePropertiesMap;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("smsSenderService")
public class SmsSenderServiceImpl implements SmsSenderService {

    private static final Logger logger = LoggerFactory.getLogger(SmsSenderServiceImpl.class);

    private final static String PROP_GROUP     = "MESSAGING";
    private final static String PROP_KEY       = "SMS_SENDER_HOST";
    private final static String SMS_SENDER_PATH  = "/push/controller/SendSmsController/sendSms.do";

    @Autowired
    ServicePropertiesMap serviceProp;

    @Autowired
    CloseableHttpClient httpClient;

    @Override
    public void send( String cellNumber, String message ) throws SmsException {
        List    sendList = new ArrayList();
        Map     msgSet = new HashMap();

        String            jsonBody;
        HttpPostRequester requester;

        msgSet.put("cellNumber", cellNumber);
        msgSet.put("message", message);
        sendList.add( msgSet );

        try {
            logger.debug( ">>>>>>>>> trying to send a message! " );
            logger.info( httpClient.toString() );
            logger.info( SMS_SENDER_PATH );
            requester = new HttpPostRequester( httpClient,
                    serviceProp.getByKey(PROP_GROUP, PROP_KEY),
                    SMS_SENDER_PATH );
        } catch( URISyntaxException e ) {
            logger.error( e.getMessage() );
            throw new SmsException( "SMS를 발송이 실패하였습니다. 만약 문제가 지속된다면 지원센터로 문의하시기 바랍니다." );
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonBody = mapper.writeValueAsString( sendList );
        } catch ( JsonProcessingException e ) {
            logger.error( e.getMessage() + " >> cellNumber: " + cellNumber + " // message: " + message );
            throw new SmsException( "SMS를 발송이 실패하였습니다. 발송 내용을 다시 한 번 확인하시기 바랍니다." );
        }

        try {
            requester.setBody( jsonBody );
            requester.execute();
        } catch ( Exception e ) {
            logger.error(e.getMessage());
            throw new SmsException( "SMS를 발송이 실패하였습니다. 수신자 또는 발송 내용을 다시 한번 확인하시기 바랍니다. " +
                                    "만약 문제가 지속된다면 지원센터로 문의하시기 바랍니다." );
        }
    }
}
