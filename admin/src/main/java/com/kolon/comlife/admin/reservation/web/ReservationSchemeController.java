package com.kolon.comlife.admin.reservation.web;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.reservation.model.*;
import com.kolon.comlife.admin.reservation.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller("reservationSchemeController")
@RequestMapping("admin/reservation-schemes/*")
public class ReservationSchemeController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationSchemeController.class);

    @Resource(name = "reservationSchemeService")
    private ReservationSchemeService service;

    @Resource(name = "reservationAmenityService")
    private ReservationAmenityService amenityService;

    @Resource(name = "complexService" )
    private ComplexService complexService;

    @Resource(name = "reservationGroupService")
    private ReservationGroupService groupService;

    @Resource(name = "reservationAllowComplexService")
    private ReservationAllowComplexService allowComplexService;

    @Resource(name = "reservationAmenitySchemeService")
    private ReservationAmenitySchemeService amenitySchemeService;

    @RequestMapping(value = "list.do")
    public ModelAndView listReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        List<ReservationSchemeInfo> schemes = service.index( new HashMap() );
        mav.addObject( "schemes", schemes );

        return mav;
    }

    @RequestMapping(value = "create.do")
    public ModelAndView createReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestParam( value = "cmplxIdx", defaultValue = "0") int cmplxIdx
            , @RequestParam( value = "parentIdx", defaultValue = "0") int parentIdx
    ) {
        String redirectTo = request.getHeader( "referer" );
        mav.addObject( "redirectTo", redirectTo );

        List<ComplexInfo> complexes = complexService.getComplexList();
        mav.addObject( "complexes", complexes );

        List<ReservationAmenityInfo> amenities = amenityService.index();
        mav.addObject( "amenities", amenities );

        mav.addObject( "cmplxIdx", cmplxIdx );
        mav.addObject( "parentIdx", parentIdx );

        if( parentIdx != 0 ) {
            ReservationGroupInfo group = groupService.show( parentIdx );
            mav.addObject( "group", group );
        }

        return mav;
    }

    @RequestMapping(value = "create.do"
            , method = RequestMethod.POST
    )
    public String storeReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @RequestParam( value = "redirectTo", defaultValue = "/admin/reservation-schemes/list.do" ) String redirectTo
            , @RequestParam( value = "cmplxIdx", defaultValue = "0" ) int cmplxIdx
            , @RequestParam( value = "allowCmplxIdxes[]" ) int[] allowCmplxIdxes
            , @RequestParam( value = "parentIdx", required = false, defaultValue = "0") int parentIdx
            , @RequestParam( value = "code", required = false ) String code
            , @RequestParam( value = "reservationType", required = false ) String reservationType
            , @RequestParam( value = "images[]", required = false ) String[] images
            , @RequestParam( value = "title", required = false ) String title
            , @RequestParam( value = "summary", required = false ) String summary
            , @RequestParam( value = "description", required = false ) String description
            , @RequestParam( value = "isOpen", required = false ) String isOpen
            , @RequestParam( value = "startDt", required = false ) String startDt
            , @RequestParam( value = "startTime", required = false ) String startTime
            , @RequestParam( value = "endDt", required = false ) String endDt
            , @RequestParam( value = "endTime", required = false ) String endTime
            , @RequestParam( value = "availableInWeekend", required = false ) String availableInWeekend
            , @RequestParam( value = "point", required = false ) int point
            , @RequestParam( value = "amount", required = false ) int amount
            , @RequestParam( value = "inStock", required = false ) int inStock
            , @RequestParam( value = "maxQty", required = false ) int maxQty
            , @RequestParam( value = "activateDuration", required = false ) String activateDuration
            , @RequestParam( value = "maintenanceStartAt", required = false ) String maintenanceStartAt
            , @RequestParam( value = "maintenanceEndAt", required = false ) String maintenanceEndAt
            , @RequestParam( value = "options", required = false ) String options
            , @RequestParam( value = "amenities[]", required = false ) int[] amenities
            , @RequestParam( value = "precautions", required = false ) String precautions
            , @RequestParam( value = "delYn", required = false ) String delYn
            ) {

        ReservationSchemeInfo info = new ReservationSchemeInfo();
        info.setCmplxIdx( cmplxIdx );
        info.setParentIdx( parentIdx );
        info.setCode( code );
        info.setReservationType( reservationType );
        info.setImages( StringUtils.join( images, "," ) );
        info.setTitle( title );
        info.setSummary( summary );
        info.setDescription( description );
        info.setIsOpen( isOpen );
        info.setStartDt( startDt );
        info.setStartTime( startTime );
        info.setEndDt( endDt );
        info.setEndTime( endTime );
        info.setAvailableInWeekend( availableInWeekend );
        info.setPoint( point );
        info.setAmount( amount );
        info.setInStock( inStock );
        info.setMaxQty( maxQty );
        info.setActivateDuration( activateDuration );
        info.setMaintenanceStartAt( maintenanceStartAt );
        info.setMaintenanceEndAt( maintenanceEndAt );
        info.setOptions( options );
        info.setPrecautions( precautions );
        info.setDelYn( delYn );

        ReservationSchemeInfo savedInfo = service.create( info );

        for (int element : allowCmplxIdxes) {
            ReservationAllowComplexInfo i = new ReservationAllowComplexInfo();
            i.setSchemeIdx( savedInfo.getIdx() );
            i.setComplexIdx( element );
            allowComplexService.create( i );
        }

        for (int element : amenities) {
            ReservationAmenitySchemeInfo i = new ReservationAmenitySchemeInfo();
            i.setAmenityIdx( element );
            i.setSchemeIdx( savedInfo.getIdx() );
            amenitySchemeService.create( i );
        }

        return "redirect:" + redirectTo;
    }

    @RequestMapping(value = "edit.do")
    public ModelAndView editReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestParam( value = "idx") int idx
            , @RequestParam( value = "cmplxIdx", defaultValue = "0") int cmplxIdx
            , @RequestParam( value = "parentIdx", required = false, defaultValue = "0") int parentIdx
    ) {
        String redirectTo = request.getHeader( "referer" );
        mav.addObject( "redirectTo", redirectTo );

        List<ComplexInfo> complexes = complexService.getComplexList();
        mav.addObject( "complexes", complexes );

        List<ReservationAmenityInfo> amenities = amenityService.index();
        mav.addObject( "amenities", amenities );

        mav.addObject( "cmplxIdx", cmplxIdx );
        mav.addObject( "parentIdx", parentIdx );

        ReservationSchemeInfo scheme = service.show( idx );
        mav.addObject( "scheme", scheme );

        if( scheme.getParentIdx() != 0 ) {
            ReservationGroupInfo group = groupService.show( scheme.getParentIdx() );
            mav.addObject( "group", group );
        }

        return mav;
    }

    @RequestMapping(value = "edit.do"
            , method = RequestMethod.POST
    )
    public String updateReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @RequestParam( value = "idx") int idx
            , @RequestParam( value = "redirectTo", defaultValue = "/admin/reservation-schemes/list.do" ) String redirectTo
            , @RequestParam( value = "cmplxIdx", defaultValue = "0" ) int cmplxIdx
            , @RequestParam( value = "allowCmplxIdxes[]" ) int[] allowCmplxIdxes
            , @RequestParam( value = "parentIdx", required = false, defaultValue = "0") int parentIdx
            , @RequestParam( value = "code", required = false ) String code
            , @RequestParam( value = "reservationType", required = false ) String reservationType
            , @RequestParam( value = "title", required = false ) String title
            , @RequestParam( value = "summary", required = false ) String summary
            , @RequestParam( value = "description", required = false ) String description
            , @RequestParam( value = "isOpen", required = false ) String isOpen
            , @RequestParam( value = "startDt", required = false ) String startDt
            , @RequestParam( value = "startTime", required = false ) String startTime
            , @RequestParam( value = "endDt", required = false ) String endDt
            , @RequestParam( value = "endTime", required = false ) String endTime
            , @RequestParam( value = "availableInWeekend", required = false ) String availableInWeekend
            , @RequestParam( value = "point", required = false ) int point
            , @RequestParam( value = "amount", required = false ) int amount
            , @RequestParam( value = "inStock", required = false ) int inStock
            , @RequestParam( value = "maxQty", required = false ) int maxQty
            , @RequestParam( value = "activateDuration", required = false ) String activateDuration
            , @RequestParam( value = "maintenanceStartAt", required = false ) String maintenanceStartAt
            , @RequestParam( value = "maintenanceEndAt", required = false ) String maintenanceEndAt
            , @RequestParam( value = "options", required = false ) String options
            , @RequestParam( value = "amenities[]", required = false ) int[] amenities
            , @RequestParam( value = "precautions", required = false ) String precautions
            , @RequestParam( value = "delYn", required = false ) String delYn
    ) {

        ReservationSchemeInfo info = service.show( idx );
        info.setCmplxIdx( cmplxIdx );
        info.setParentIdx( parentIdx );
        info.setCode( code );
        info.setReservationType( reservationType );
        info.setTitle( title );
        info.setSummary( summary );
        info.setDescription( description );
        info.setIsOpen( isOpen );
        info.setStartDt( startDt );
        info.setStartTime( startTime );
        info.setEndDt( endDt );
        info.setEndTime( endTime );
        info.setAvailableInWeekend( availableInWeekend );
        info.setPoint( point );
        info.setAmount( amount );
        info.setInStock( inStock );
        info.setMaxQty( maxQty );
        info.setActivateDuration( activateDuration );
        info.setMaintenanceStartAt( maintenanceStartAt );
        info.setMaintenanceEndAt( maintenanceEndAt );
        info.setOptions( options );
        info.setPrecautions( precautions );
        info.setDelYn( delYn );

        ReservationSchemeInfo savedInfo = service.create( info );

        HashMap params = new HashMap();
        params.put( "schemeIdx", info.getIdx() );

        allowComplexService.delete( params );
        for (int element : allowCmplxIdxes) {
            ReservationAllowComplexInfo i = new ReservationAllowComplexInfo();
            i.setSchemeIdx( savedInfo.getIdx() );
            i.setComplexIdx( element );
            allowComplexService.create( i );
        }

        amenitySchemeService.delete( params );
        for (int element : amenities) {
            ReservationAmenitySchemeInfo i = new ReservationAmenitySchemeInfo();
            i.setAmenityIdx( element );
            i.setSchemeIdx( savedInfo.getIdx() );
            amenitySchemeService.create( i );
        }

        return "redirect:" + redirectTo;
    }

    @RequestMapping(value = "delete.do"
            , method = RequestMethod.POST
    )
    public String deleteReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @RequestParam( value = "idx") int idx
    ) {
        ReservationSchemeInfo info = service.show( idx );
        service.delete( info );

        return "redirect:" + request.getHeader( "referer" );
    }

}
