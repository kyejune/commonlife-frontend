package com.kolon.comlife.admin.reservation.web;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.reservation.model.ReservationAmenityInfo;
import com.kolon.comlife.admin.reservation.model.ReservationGroupInfo;
import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.admin.reservation.service.ReservationAmenityService;
import com.kolon.comlife.admin.reservation.service.ReservationGroupService;
import com.kolon.comlife.admin.reservation.service.ReservationSchemeService;
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

@Controller("reservationGroupController")
@RequestMapping("admin/reservation-groups/*")
public class ReservationGroupController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationGroupController.class);

    @Resource(name = "reservationGroupService")
    private ReservationGroupService service;

    @Resource(name = "reservationSchemeService")
    private ReservationSchemeService schemeService;

    @Resource(name = "reservationAmenityService")
    private ReservationAmenityService amenityService;

    @Resource(name = "complexService" )
    private ComplexService complexService;

    @RequestMapping(value = "list.do")
    public ModelAndView listReservationGroup (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestParam( value = "cmplxIdx", required = false, defaultValue = "0" ) int cmplxIdx
    ) {
        HashMap params = new HashMap();

        if( cmplxIdx != 0 ) {
            params.put( "id", cmplxIdx );
        }

        List<ReservationGroupInfo> groups = service.index( params );
        List<ComplexInfo> complexes = complexService.getComplexList();

        mav.addObject( "cmplxIdx", cmplxIdx );
        mav.addObject( "groups", groups );
        mav.addObject( "complexes", complexes );

        if( cmplxIdx != 0 ) {
            HashMap schemeParams = new HashMap();
            schemeParams.put( "cmplxIdx", cmplxIdx );
            schemeParams.put( "groupIdx", 0 );
            List<ReservationSchemeInfo> schemes = schemeService.index( schemeParams );

            mav.addObject( "schemes", schemes );
        }

        return mav;
    }

    @RequestMapping(value = "create.do")
    public ModelAndView createReservationGroup (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        List<ComplexInfo> complexes = complexService.getComplexList();
        mav.addObject( "complexes", complexes );

        return mav;
    }

    @RequestMapping(value = "create.do"
            , method = RequestMethod.POST
    )
    public String storeReservationGroup (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @RequestParam( value = "cmplxIdx" ) int cmplxIdx
            , @RequestParam( value = "title" ) String title
            , @RequestParam( value = "summary" ) String summary
            , @RequestParam( value = "icon", required = false ) String icon
            ) {

        ReservationGroupInfo info = new ReservationGroupInfo();
        info.setCmplxIdx( cmplxIdx );
        info.setTitle( title );
        info.setSummary( summary );
        info.setIcon( icon );

        service.create( info );

        return "redirect:" + "/admin/reservation-groups/list.do";
    }

    @RequestMapping(value = "edit.do")
    public ModelAndView editReservationGroup (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestParam( value = "idx", required = false, defaultValue = "0" ) int idx
    ) {
        List<ComplexInfo> complexes = complexService.getComplexList();
        mav.addObject( "complexes", complexes );

        ReservationGroupInfo group = service.show( idx );
        mav.addObject( "group", group );

        return mav;
    }

    @RequestMapping(value = "edit.do",
                    method = RequestMethod.POST)
    public String updateReservationGroup (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestParam( value = "idx", required = false, defaultValue = "0" ) int idx
            , @RequestParam( value = "cmplxIdx" ) int cmplxIdx
            , @RequestParam( value = "title" ) String title
            , @RequestParam( value = "summary" ) String summary
            , @RequestParam( value = "icon", required = false ) String icon
    ) {
        ReservationGroupInfo group = service.show( idx );
        group.setCmplxIdx( cmplxIdx );
        group.setTitle( title );
        group.setSummary( summary );
        group.setIcon( icon );

        service.update( group );

        return "redirect:" + "/admin/reservation-groups/list.do";
    }

    @RequestMapping(value = "delete.do",
                    method = RequestMethod.POST)
    public String updateReservationGroup (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestParam( value = "idx", required = false, defaultValue = "0" ) int idx
    ) {
        ReservationGroupInfo group = service.show( idx );
        service.delete( group );

        return "redirect:" + "/admin/reservation-groups/list.do";
    }

    @RequestMapping(value = "show.do")
    public ModelAndView showReservationGroup (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestParam( value = "idx", required = false, defaultValue = "0" ) int idx
    ) {
        ReservationGroupInfo group = service.show( idx );
        mav.addObject( "group", group );

        HashMap params = new HashMap();
        params.put( "groupIdx", idx );
        List<ReservationSchemeInfo> schemes = schemeService.index( params );
        mav.addObject( "schemes", schemes );

        return mav;
    }

}
