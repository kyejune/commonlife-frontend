package com.kolon.comlife.status;

import com.kolon.comlife.common.model.SimpleMsgInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/status")
public class StatusController {
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getStatus(HttpServletRequest request) {

        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("OK!"));
    }

}
