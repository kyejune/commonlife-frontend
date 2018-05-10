package com.kolon.comlife.admin.reservation.web;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.reservation.model.ReservationGroupInfo;
import com.kolon.comlife.admin.reservation.model.ReservationInfo;
import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.admin.reservation.service.ReservationGroupService;
import com.kolon.comlife.admin.reservation.service.ReservationSchemeService;
import com.kolon.comlife.admin.reservation.service.ReservationService;
import com.kolon.comlife.admin.users.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Resource(name = "userService")
    private UserService userService;

    private List<ReservationInfo> getReservationList( HashMap params ) {
        List<ReservationInfo> reservations = service.index( params );
        List<Integer> ids = new ArrayList<Integer>();
        for (ReservationInfo item :
                reservations) {
            ids.add( item.getParentIdx() );
            item.setUser( userService.getUserExtInfoById( item.getUsrID() ) );
        }

        HashMap schemeParams = new HashMap();
        if( ids.size() > 0 ) {
            schemeParams.put( "ids", ids );
        }
        List<ReservationSchemeInfo> schemes = schemeService.index( schemeParams );

        for (ReservationInfo item : reservations) {
            for (ReservationSchemeInfo scheme : schemes) {
                if (scheme.getIdx() == item.getParentIdx()) {
                    item.setScheme(scheme);
                }
                scheme.setComplex( complexService.getComplexById( scheme.getCmplxIdx() ) );
            }
        }

        return reservations;
    }

    private List<ReservationInfo> getReservationList() {
        HashMap params = new HashMap<String, Object>();
        List<ReservationInfo> reservations = this.getReservationList( params );

        return reservations;
    }

    @RequestMapping(value = "calendar.do")
    public ModelAndView calReservation (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        // 관리자 이름 표시
        AdminInfo adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        mav.addObject("adminInfo", adminInfo);

        HashMap params = new HashMap();
        if( adminInfo.getCmplxId() != 0 ) {
            params.put( "cmplxIdx", adminInfo.getCmplxId() );
        }

        List<ReservationInfo> reservations = this.getReservationList( params );
        mav.addObject( "reservations", reservations );

        return mav;
    }

    @RequestMapping(value = "list.do")
    public ModelAndView listReservation (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        // 관리자 이름 표시
        AdminInfo adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        mav.addObject("adminInfo", adminInfo);

        String complexIdx = request.getParameter( "complexIdx" );
        String groupIdx = request.getParameter( "groupIdx" );
        mav.addObject( "groupIdx", groupIdx );

        HashMap params = new HashMap();
        if( complexIdx != null && !complexIdx.equals( "" ) ) {
            params.put( "cmplxIdx", complexIdx );
            mav.addObject( "complexIdx", complexIdx );
        }

        if( adminInfo.getCmplxId() != 0 ) {
            params.put( "cmplxIdx", adminInfo.getCmplxId() );
        }

        // 그룹에 대한 파라미터가 있을 경우
        if( groupIdx != null && !groupIdx.equals( "" ) ) {
            params.put( "groupIdx", groupIdx );
        }

        List<ComplexInfo> complexes = complexService.getComplexList();
        mav.addObject( "complexes", complexes );

        List<ReservationGroupInfo> groups = groupService.index( new HashMap() );
        mav.addObject( "groups", groups );

        List<ReservationInfo> reservations = this.getReservationList( params );
        mav.addObject( "reservations", reservations );

        return mav;
    }

    @RequestMapping(value = "create.do")
    public ModelAndView createReservation (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        // 관리자 이름 표시
        AdminInfo adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        mav.addObject("adminInfo", adminInfo);

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
    public String storeReservation (
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

        service.create( info );

        return "redirect:" + redirectTo;
    }

    @RequestMapping(value = "edit.do")
    public ModelAndView editReservation (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        // 관리자 이름 표시
        AdminInfo adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        mav.addObject("adminInfo", adminInfo);

        return mav;
    }

    @RequestMapping(value = "show.do")
    public ModelAndView showReservation (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        // 관리자 이름 표시
        AdminInfo adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        mav.addObject("adminInfo", adminInfo);

        int idx = Integer.parseInt( request.getParameter( "idx" ) );
        ReservationInfo reservation = service.show( idx );
        mav.addObject( "reservation", reservation );
        mav.addObject( "redirectTo", request.getHeader( "referer" ) );
        return mav;
    }

    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    public String deleteReservation (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        int idx = Integer.parseInt( request.getParameter( "idx" ) );
        ReservationInfo reservation = new ReservationInfo();
        reservation.setIdx( idx );
        service.delete( reservation );

        String redirectTo = request.getParameter( "redirectTo" );
        if( redirectTo == null ) {
            redirectTo = "/admin/reservations/list.do";
        }
        else if( !redirectTo.equals( "" ) ) {
            redirectTo = "/admin/reservations/list.do";
        }

        return "redirect:" + redirectTo;
    }

    @RequestMapping(value = "queue.do")
    public ModelAndView queueList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        List<ReservationInfo> reservations = service.queue();

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

    @RequestMapping(value = "edit-queue.do"
            , method = RequestMethod.POST
    )
    public String editReservationStatus (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @RequestParam( value = "idx" ) int idx
            , @RequestParam( value = "status" ) String status
    ) {

        // 예약 틀
        ReservationInfo info = service.show( idx );
        info.setStatus( status );

        service.updateStatus( info );

        return "redirect:" + request.getHeader( "referer" );
    }

}
