package com.kolon.comlife.admin.reservation.web;

import com.kolon.comlife.admin.reservation.model.ReservationInfo;
import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.admin.reservation.service.ReservationSchemeService;
import com.kolon.comlife.admin.reservation.service.ReservationService;
import org.apache.ibatis.session.SqlSession;
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
import java.util.List;

@Controller("reservationController")
@RequestMapping("admin/reservations/*")
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Resource(name = "reservationSchemeService")
    private ReservationSchemeService schemeService;

    @Resource(name = "reservationService")
    private ReservationService service;

    @RequestMapping(value = "list.do")
    public ModelAndView listReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        List<ReservationInfo> reservations = service.index();
        mav.addObject( "reservations", reservations );

        return mav;
    }

    @RequestMapping(value = "create.do")
    public ModelAndView createReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        List<ReservationSchemeInfo> schemes = schemeService.index();
        mav.addObject( "schemes", schemes );
        return mav;
    }

    @RequestMapping(value = "create.do"
            , method = RequestMethod.POST
    )
    public String storeReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @RequestParam( value = "parentIdx", required = false ) int parentIdx
            , @RequestParam( value = "status", required = false ) String status
            ) {

        ReservationInfo info = new ReservationInfo();
        info.setParentIdx( parentIdx );
        info.setStatus( status );

        service.create( info );

        return "redirect:" + "/admin/reservations/list.do";
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
