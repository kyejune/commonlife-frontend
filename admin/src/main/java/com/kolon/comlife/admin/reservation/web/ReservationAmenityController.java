package com.kolon.comlife.admin.reservation.web;

import com.kolon.comlife.admin.reservation.model.ReservationAmenityInfo;
import com.kolon.comlife.admin.reservation.service.ReservationAmenityService;
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

@Controller("reservationAmenityController")
@RequestMapping("admin/reservation-amenities/*")
public class ReservationAmenityController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationAmenityController.class);

    @Resource(name = "reservationAmenityService")
    private ReservationAmenityService service;

    @RequestMapping(value = "list.do")
    public ModelAndView listReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        List<ReservationAmenityInfo> amenities = service.index();

        mav.addObject( "amenities", amenities );

        return mav;
    }

    @RequestMapping(value = "create.do")
    public ModelAndView createReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
//        List<ReservationSchemeInfo> schemes = schemeService.index( new HashMap() );
//        mav.addObject( "schemes", schemes );
        return mav;
    }

    @RequestMapping(value = "create.do"
            , method = RequestMethod.POST
    )
    public String storeReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @RequestParam( value = "useIcon", required = false ) String useIcon
            , @RequestParam( value = "iconClass", required = false ) String iconClass
            , @RequestParam( value = "imagePath", required = false ) String imagePath
            , @RequestParam( value = "name", required = false ) String name
            ) {

        // 예약 틀
        ReservationAmenityInfo info = new ReservationAmenityInfo();
        info.setUseIcon( useIcon );
        info.setIconClass( iconClass );
        info.setImagePath( imagePath );
        info.setName( name );

        service.create( info );

        return "redirect:" + "/admin/reservation-amenities/list.do";
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
