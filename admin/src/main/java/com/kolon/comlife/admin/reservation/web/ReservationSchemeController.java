package com.kolon.comlife.admin.reservation.web;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.admin.reservation.service.ReservationSchemeService;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    ) {
        return mav;
    }

    @RequestMapping(value = "create.do"
            , method = RequestMethod.POST
    )
    public String storeReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
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
            , @RequestParam( value = "delYn", required = false ) String delYn
            ) {

        ReservationSchemeInfo info = new ReservationSchemeInfo();
        info.setParentIdx( 0 );
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
        info.setDelYn( delYn );

        service.create( info );

        return "redirect:" + "/admin/reservation-schemes/list.do";
    }

    @RequestMapping(value = "edit.do")
    public ModelAndView editReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        return mav;
    }

}
