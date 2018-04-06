package com.kolon.comlife.admin.reservation.web;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.reservation.model.ReservationInfo;
import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.admin.reservation.service.ReservationGroupService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller("reservationController")
@RequestMapping("admin/reservations/*")
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Resource(name = "reservationSchemeService")
    private ReservationSchemeService schemeService;

    @Resource(name = "reservationGroupService")
    private ReservationGroupService groupService;

    @Resource(name = "complexService" )
    private ComplexService complexService;

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

        List<Integer> ids = new ArrayList<Integer>();
        for (ReservationInfo item :
                reservations) {
            ids.add( item.getParentIdx() );
        }

        HashMap params = new HashMap<String, Object>();
        if( ids.size() > 0 ) {
            params.put( "ids", ids );
        }

        List<ReservationSchemeInfo> schemes = schemeService.index( params );

        for (ReservationInfo item : reservations) {
            for (ReservationSchemeInfo scheme : schemes) {
                if (scheme.getIdx() == item.getParentIdx()) {
                    item.setScheme(scheme);
                }
            }
        }

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
        String redirectTo = request.getHeader( "referer" );
        mav.addObject( "redirectTo", redirectTo );

        List<ComplexInfo> complexes = complexService.getComplexList();
        mav.addObject( "complexes", complexes );

        List<ReservationSchemeInfo> schemes = schemeService.index( new HashMap() );
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
            , @RequestParam( value = "redirectTo", defaultValue = "/admin/reservation-groups/list.do" ) String redirectTo
            , @RequestParam( value = "parentIdx", required = false ) int parentIdx
            , @RequestParam( value = "status", required = false ) String status
            , @RequestParam( value = "startDt", required = false ) String startDt
            , @RequestParam( value = "startTime", required = false ) String startTime
            , @RequestParam( value = "endDt", required = false ) String endDt
            , @RequestParam( value = "endTime", required = false ) String endTime
            , @RequestParam( value = "qty", required = false ) int qty
            ) {

        // 예약 틀
        ReservationSchemeInfo scheme = schemeService.show( parentIdx );

        ReservationInfo info = new ReservationInfo();
        info.setUsrID( 14 ); // TODO: 사용자 아이디 입력
        info.setStatus( status );
        info.setStartDt( startDt );
        info.setStartTime( startTime );
        info.setEndDt( endDt );
        info.setEndTime( endTime );
        info.setQty( qty );

        // 틀에서 내려받을 자료들
        info.setParentIdx( scheme.getIdx() );
        info.setPoint( scheme.getPoint() );
        info.setAmount( scheme.getAmount() );

        logger.debug( "---------- qty: " + qty );

//        service.create( info );

        return "redirect:" + redirectTo;
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
