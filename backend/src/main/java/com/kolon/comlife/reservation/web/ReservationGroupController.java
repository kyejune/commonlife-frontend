package com.kolon.comlife.reservation.web;

import com.kolon.comlife.reservation.model.ReservationGroupInfo;
import com.kolon.comlife.reservation.service.ReservationGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reservation-groups/*")
public class ReservationGroupController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationGroupController.class);

    @Resource(name = "reservationGroupService")
    ReservationGroupService service;

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(HttpServletRequest request) {
        List<ReservationGroupInfo> groups = service.index( new HashMap() );
        return ResponseEntity.status( HttpStatus.OK ).body( groups );
    }
}
