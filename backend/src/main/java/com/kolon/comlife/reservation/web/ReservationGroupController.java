package com.kolon.comlife.reservation.web;

import com.kolon.comlife.reservation.model.ReservationGroupInfo;
import com.kolon.comlife.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.reservation.service.ReservationGroupService;
import com.kolon.comlife.reservation.service.ReservationSchemeService;
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
@RequestMapping("/reservation-groups/*")
public class ReservationGroupController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationGroupController.class);

    @Resource(name = "reservationGroupService")
    ReservationGroupService service;

    @Resource(name = "reservationSchemeService")
    ReservationSchemeService schemeService;

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(
            HttpServletRequest request
            , @RequestParam( value = "cmplxIdx", required = false, defaultValue = "0" ) int cmplxIdx
    ) {
        HashMap groupParams = new HashMap();
        groupParams.put( "cmplxIdx", cmplxIdx );
        List<ReservationGroupInfo> groups = service.index( groupParams );

        // 현재 그룹에 속해있는 스키마들을 가져온다
        /*
        List<Integer> ids = new ArrayList<Integer>();
        for ( ReservationGroupInfo group: groups ) {
            ids.add( group.getIdx() );
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put( "ids", ids );
        List<ReservationSchemeInfo> schems = schemeService.index( params );
        //*/

        HashMap schemeParams = new HashMap();
        schemeParams.put( "cmplxIdx", cmplxIdx );
        schemeParams.put( "groupIdx", 0 );
        List<ReservationSchemeInfo> schemes = schemeService.index( schemeParams );

        HashMap result = new HashMap();
        result.put( "groups", groups );
        result.put( "schemes", schemes );

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @CrossOrigin
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity show( HttpServletRequest request, @PathVariable("id") int id ) {
        ReservationGroupInfo info = service.show( id );

        // 현재 그룹에 속해있는 스키마들을 가져온다
        HashMap params = new HashMap();
        params.put( "groupIdx", id );
        List<ReservationSchemeInfo> schemes = schemeService.index( params );
        info.setSchemes( schemes );

        return ResponseEntity.status( HttpStatus.OK ).body( info );
    }
}
