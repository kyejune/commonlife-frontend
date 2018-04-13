package com.kolon.comlife.reservation.web;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.reservation.model.ReservationAmenityInfo;
import com.kolon.comlife.reservation.model.ReservationGroupInfo;
import com.kolon.comlife.reservation.model.ReservationInfo;
import com.kolon.comlife.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.reservation.service.ReservationAmenityService;
import com.kolon.comlife.reservation.service.ReservationGroupService;
import com.kolon.comlife.reservation.service.ReservationSchemeService;
import com.kolon.comlife.reservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reservation-schemes/*")
public class ReservationSchemeController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationSchemeController.class);

    @Resource(name = "reservationSchemeService")
    ReservationSchemeService service;

    @Resource(name = "reservationGroupService")
    ReservationGroupService schemeService;

    @Resource(name = "reservationService")
    ReservationService reservationService;

    @Resource(name = "complexService")
    ComplexService complexService;

    @Resource(name = "reservationAmenityService")
    ReservationAmenityService amenityService;

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(HttpServletRequest request) {

        return null;
    }

    @CrossOrigin
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity show( HttpServletRequest request, @PathVariable("id") int id ) {
        ReservationSchemeInfo info = service.show( id );
        ComplexInfo complex = complexService.getComplexById( info.getCmplxIdx() );
        info.setComplex( complex );

        HashMap params = new HashMap();
        params.put( "schemeIdx", info.getIdx() );
        List<ReservationAmenityInfo> amenities = amenityService.index( params );
        info.setAmenities( amenities );

        return ResponseEntity.status( HttpStatus.OK ).body( info );
    }

    @CrossOrigin
    @GetMapping(
            value = "/{id}/reservations",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity reservations(
            HttpServletRequest request
            , @PathVariable("id") int id
    ) {
        HashMap params = new HashMap();
        params.put( "schemeIdx", id );
        params.put( "startDt", request.getParameter( "startDt" ) );
        List<ReservationInfo> reservations = reservationService.index( params );

        return ResponseEntity.status( HttpStatus.OK ).body( reservations );
    }
}
