package com.kolon.common.messaging.web;

import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.common.messaging.service.SmsSenderService;
import com.kolon.common.exception.SmsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/common")
public class SmsController {
    private static Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Resource(name = "smsSenderService")
    SmsSenderService smsSender;

    @PostMapping(
        value = "/smsSender",
        produces = MediaType.APPLICATION_JSON_VALUE )
    public  ResponseEntity smsSender( HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam("cellNumber") String cellNumber,
                                      @RequestParam("message") String message ) {

        try {
            smsSender.send(cellNumber, message);
        } catch( SmsException e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new SimpleErrorInfo( e.getMessage() ));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SimpleMsgInfo(cellNumber +  "번호로 SMS 메시지가 전송되었습니다."));
    }
}
