package com.kolon.comlife.admin.reservation.web;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeOptionInfo;
import com.kolon.comlife.admin.reservation.service.ReservationSchemeOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller("reservationOptionAPIController")
@RequestMapping("admin/api/reservation-scheme-options/*")
public class ReservationSchemeOptionAPIController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationSchemeOptionAPIController.class);

    @Resource(name="reservationSchemeOptionService")
    ReservationSchemeOptionService service;

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
    ) {
        return ResponseEntity.status( HttpStatus.OK ).body( service.index( new HashMap() ) );
    }

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
    ) {
        ReservationSchemeOptionInfo source = new ReservationSchemeOptionInfo();
        source.setName( request.getParameter( "name" ) );
        ReservationSchemeOptionInfo info = service.create( source );
        return ResponseEntity.status( HttpStatus.OK ).body( info );
    }

    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @PathVariable("id") int id
    ) {
        ReservationSchemeOptionInfo info = service.show( id );
        service.delete( info );
        return ResponseEntity.status( HttpStatus.OK ).body( "ok" );
    }
}
