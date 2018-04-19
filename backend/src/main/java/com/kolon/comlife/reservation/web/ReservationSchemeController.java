package com.kolon.comlife.reservation.web;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.reservation.model.*;
import com.kolon.comlife.reservation.service.*;
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

    @Resource(name = "reservationSchemeOptionService")
    ReservationSchemeOptionService reservationSchemeOptionService;

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

        HashMap optionParams = new HashMap();
        optionParams.put( "schemeIdx", info.getIdx() );
        List<ReservationSchemeOptionInfo> schemeOptions = reservationSchemeOptionService.index( optionParams );
        info.setSchemeOptions( schemeOptions );

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
