package com.kolon.comlife.admin.reservation.web;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.reservation.model.*;
import com.kolon.comlife.admin.reservation.service.*;
import com.kolon.common.prop.ServicePropertiesMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Resource(name = "reservationSchemeOptionService")
    private ReservationSchemeOptionService optionService;

    @Autowired
    ServicePropertiesMap serviceProp;

    @RequestMapping(value = "list.do")
    public ModelAndView listReservationScheme (
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
        List<ReservationSchemeInfo> schemes = service.index( params );
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
        // 관리자 이름 표시
        AdminInfo adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        mav.addObject("adminInfo", adminInfo);

        String redirectTo = request.getHeader( "referer" );
        mav.addObject( "redirectTo", redirectTo );

        List<ComplexInfo> complexes = complexService.getComplexList();
        mav.addObject( "complexes", complexes );

        List<ReservationGroupInfo> groups = groupService.index( new HashMap() );
        mav.addObject( "groups", groups );

        List<ReservationAmenityInfo> amenities = amenityService.index( new HashMap() );
        mav.addObject( "amenities", amenities );

        mav.addObject( "cmplxIdx", cmplxIdx );
        mav.addObject( "parentIdx", parentIdx );

        // 이미지 스토어 호스트
        mav.addObject( "IMAGE_STORE_HOST", serviceProp.getByKey( "IMAGE_STORE", "SERVER_HOST" ) );

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
            , @RequestParam( value = "activate", required = false, defaultValue = "no") String activate
            , @RequestParam( value = "useTime", required = false, defaultValue = "no") String useTime
            , @RequestParam( value = "useQueue", required = false, defaultValue = "no") String useQueue
            , @RequestParam( value = "useQty", required = false, defaultValue = "no") String useQty
            , @RequestParam( value = "useOptions", required = false, defaultValue = "no") String useOptions
            , @RequestParam( value = "useField", required = false, defaultValue = "no") String useField
            , @RequestParam( value = "isExpress", required = false, defaultValue = "N") String isExpress
            , @RequestParam( value = "fieldLabel", required = false) String fieldLabel
            , @RequestParam( value = "code", required = false ) String code
            , @RequestParam( value = "icon", required = false ) String icon
            , @RequestParam( value = "reservationType", required = false ) String reservationType
            , @RequestParam( value = "images[]", required = false ) String[] images
            , @RequestParam( value = "title", required = false ) String title
            , @RequestParam( value = "summary", required = false ) String summary
            , @RequestParam( value = "description", required = false ) String description
            , @RequestParam( value = "isOpen", required = false ) String isOpen
            , @RequestParam( value = "startDt", required = false ) String startDt
            , @RequestParam( value = "endDt", required = false ) String endDt
            , @RequestParam( value = "openTime", required = false, defaultValue = "00") String openTime
            , @RequestParam( value = "closeTime", required = false, defaultValue = "24") String closeTime
            , @RequestParam( value = "maxDuration", required = false, defaultValue = "1") int maxDuration
            , @RequestParam( value = "maxDays", required = false, defaultValue = "1") int maxDays
            , @RequestParam( value = "availableInWeekend", required = false ) String availableInWeekend
            , @RequestParam( value = "point", required = false ) int point
            , @RequestParam( value = "amount", required = false, defaultValue = "0") int amount
            , @RequestParam( value = "inStock", required = false, defaultValue = "0") int inStock
            , @RequestParam( value = "maxQty", required = false, defaultValue = "0" ) int maxQty
            , @RequestParam( value = "activateDuration", required = false, defaultValue = "0") int activateDuration
            , @RequestParam( value = "maintenanceStartAt", required = false ) String maintenanceStartAt
            , @RequestParam( value = "maintenanceEndAt", required = false ) String maintenanceEndAt
            , @RequestParam( value = "options", required = false ) String options
            , @RequestParam( value = "amenities[]", required = false ) int[] amenities
            , @RequestParam( value = "precautions", required = false ) String precautions
            , @RequestParam( value = "delYn", required = false, defaultValue = "N") String delYn
            , @RequestParam( value = "optionIdx[]", required = false) int[] optionIdx
            ) {

        ReservationSchemeInfo info = new ReservationSchemeInfo();
        info.setCmplxIdx( cmplxIdx );
        info.setParentIdx( parentIdx );
        info.setActivate( activate );
        info.setUseTime( useTime );
        info.setUseQueue( useQueue );
        info.setUseQty( useQty );
        info.setUseOptions( useOptions );
        info.setUseField( useField );
        info.setIsExpress( isExpress );
        info.setFieldLabel( fieldLabel );
        info.setCode( code );
        info.setIcon( icon );
        info.setReservationType( reservationType );
        info.setImages( StringUtils.join( images, "," ) );
        info.setTitle( title );
        info.setSummary( summary );
        info.setDescription( description );
        info.setIsOpen( isOpen );
        info.setStartDt( startDt );
        info.setEndDt( endDt );
        // 시간 값만 받기 때문에 HH:mm:ss 형식으로 설정
        info.setOpenTime( openTime + ":00:00" );
        info.setCloseTime( closeTime + ":00:00" );
        info.setMaxDuration( maxDuration );
        info.setMaxDays( maxDays );
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

        if( amenities != null ) {
            for (int element : amenities) {
                ReservationAmenitySchemeInfo i = new ReservationAmenitySchemeInfo();
                i.setAmenityIdx( element );
                i.setSchemeIdx( savedInfo.getIdx() );
                amenitySchemeService.create( i );
            }
        }

        // 옵션 항목 parentIdx 업데이트
        if( optionIdx != null && optionIdx.length > 0 ) {
            for ( int element: optionIdx ) {
                ReservationSchemeOptionInfo option = optionService.show( element );
                option.setParentIdx( savedInfo.getIdx() );
                optionService.update( option );
            }
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
        // 관리자 이름 표시
        AdminInfo adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        mav.addObject("adminInfo", adminInfo);

        String redirectTo = request.getHeader( "referer" );
        mav.addObject( "redirectTo", redirectTo );

        List<ComplexInfo> complexes = complexService.getComplexList();
        mav.addObject( "complexes", complexes );

        List<ReservationGroupInfo> groups = groupService.index( new HashMap() );
        mav.addObject( "groups", groups );

        List<ReservationAmenityInfo> amenities = amenityService.index( new HashMap() );
        mav.addObject( "amenities", amenities );

        // 옵션 목록 불러옴
        HashMap optionParams = new HashMap();
        optionParams.put( "parentIdx", idx );
        List<ReservationSchemeOptionInfo> optionList = optionService.index( optionParams );
        mav.addObject( "optionList", optionList );

        mav.addObject( "cmplxIdx", cmplxIdx );
        mav.addObject( "parentIdx", parentIdx );

        ReservationSchemeInfo scheme = service.show( idx );
        mav.addObject( "scheme", scheme );

        ComplexInfo selectedComplex = complexService.getComplexById( scheme.getCmplxIdx() );
        mav.addObject( "selectedComplex", selectedComplex );

        if( scheme.getParentIdx() != 0 ) {
            ReservationGroupInfo selectedGroup = groupService.show( scheme.getParentIdx() );
            mav.addObject( "selectedGroup", selectedGroup );
        }

        HashMap params = new HashMap();
        params.put( "schemeIdx", scheme.getIdx() );
        List<ReservationAmenityInfo> schemeAmenities = amenityService.indexOfSchemes( params );
        scheme.setAmenities( schemeAmenities );

        if( scheme.getParentIdx() != 0 ) {
            ReservationGroupInfo group = groupService.show( scheme.getParentIdx() );
            mav.addObject( "group", group );
        }

        // 이미지 스토어 호스트
        mav.addObject( "IMAGE_STORE_HOST", serviceProp.getByKey( "IMAGE_STORE", "SERVER_HOST" ) );

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
            , @RequestParam( value = "activate", required = false, defaultValue = "no") String activate
            , @RequestParam( value = "useTime", required = false, defaultValue = "no") String useTime
            , @RequestParam( value = "useQueue", required = false, defaultValue = "no") String useQueue
            , @RequestParam( value = "useQty", required = false, defaultValue = "no") String useQty
            , @RequestParam( value = "useOptions", required = false, defaultValue = "no") String useOptions
            , @RequestParam( value = "useField", required = false, defaultValue = "no") String useField
            , @RequestParam( value = "isExpress", required = false, defaultValue = "N") String isExpress
            , @RequestParam( value = "fieldLabel", required = false) String fieldLabel
            , @RequestParam( value = "code", required = false ) String code
            , @RequestParam( value = "icon", required = false ) String icon
            , @RequestParam( value = "reservationType", required = false ) String reservationType
            , @RequestParam( value = "images[]", required = false ) String[] images
            , @RequestParam( value = "title", required = false ) String title
            , @RequestParam( value = "summary", required = false ) String summary
            , @RequestParam( value = "description", required = false ) String description
            , @RequestParam( value = "isOpen", required = false ) String isOpen
            , @RequestParam( value = "startDt", required = false ) String startDt
            , @RequestParam( value = "endDt", required = false ) String endDt
            , @RequestParam( value = "openTime", required = false, defaultValue = "00" ) String openTime
            , @RequestParam( value = "closeTime", required = false, defaultValue = "24" ) String closeTime
            , @RequestParam( value = "maxDuration", required = false, defaultValue = "1" ) int maxDuration
            , @RequestParam( value = "maxDays", required = false, defaultValue = "1" ) int maxDays
            , @RequestParam( value = "availableInWeekend", required = false ) String availableInWeekend
            , @RequestParam( value = "point", required = false ) int point
            , @RequestParam( value = "amount", required = false, defaultValue = "0") int amount
            , @RequestParam( value = "inStock", required = false ) int inStock
            , @RequestParam( value = "maxQty", required = false ) int maxQty
            , @RequestParam( value = "activateDuration", required = false, defaultValue = "0") int activateDuration
            , @RequestParam( value = "maintenanceStartAt", required = false ) String maintenanceStartAt
            , @RequestParam( value = "maintenanceEndAt", required = false ) String maintenanceEndAt
            , @RequestParam( value = "options", required = false ) String options
            , @RequestParam( value = "amenities[]", required = false ) int[] amenities
            , @RequestParam( value = "precautions", required = false ) String precautions
            , @RequestParam( value = "delYn", required = false ) String delYn
            , @RequestParam( value = "optionIdx[]", required = false) int[] optionIdx
    ) {

        ReservationSchemeInfo info = service.show( idx );
        info.setCmplxIdx( cmplxIdx );
        info.setParentIdx( parentIdx );
        info.setActivate( activate );
        info.setUseTime( useTime );
        info.setUseQueue( useQueue );
        info.setUseQty( useQty );
        info.setUseOptions( useOptions );
        info.setUseField( useField );
        info.setIsExpress( isExpress );
        info.setFieldLabel( fieldLabel );
        info.setCode( code );
        info.setIcon( icon );
        info.setReservationType( reservationType );
        info.setImages( StringUtils.join( images, "," ) );
        info.setTitle( title );
        info.setSummary( summary );
        info.setDescription( description );
        info.setIsOpen( isOpen );
        info.setStartDt( startDt );
        info.setEndDt( endDt );
        // 시간 값만 받기 때문에 HH:mm:ss 형식으로 설정
        info.setOpenTime( openTime + ":00:00" );
        info.setCloseTime( closeTime + ":00:00" );
        info.setMaxDuration( maxDuration );
        info.setMaxDays( maxDays );
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

        ReservationSchemeInfo savedInfo = service.update( info );

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
        if( amenities != null ) {
            for (int element : amenities) {
                ReservationAmenitySchemeInfo i = new ReservationAmenitySchemeInfo();
                i.setAmenityIdx( element );
                i.setSchemeIdx( savedInfo.getIdx() );
                amenitySchemeService.create( i );
            }
        }

        // 기존 옵션 항목 중 삭제된 항목을 찾아 제거
        HashMap optionParams = new HashMap();
        optionParams.put( "parentIdx", idx );
        List<ReservationSchemeOptionInfo> oldOptions = optionService.index( optionParams );
        oldFor : for ( ReservationSchemeOptionInfo old: oldOptions ) {
            if( optionIdx != null && optionIdx.length > 0 ) {
                for (int element : optionIdx) {
                    if (old.getIdx() == element) {
                        continue oldFor;
                    }
                }
            }
            optionService.delete( old );
        }

        // 옵션 항목 parentIdx 업데이트
        if( optionIdx != null && optionIdx.length > 0 ) {
            for (int element : optionIdx) {
                ReservationSchemeOptionInfo option = optionService.show(element);
                option.setParentIdx(savedInfo.getIdx());
                optionService.update(option);
            }
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
