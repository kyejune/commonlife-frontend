
package com.kolon.comlife.admin.complexes.web;


import com.kolon.comlife.admin.complexes.model.ComplexConst;
import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
import com.kolon.comlife.admin.complexes.model.ComplexRegion;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.imageStore.service.ImageStoreService;
import com.kolon.comlife.admin.manager.model.AdminConst;
import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.manager.service.ManagerService;
import com.kolon.comlife.admin.users.exception.UserGeneralException;
import com.kolon.comlife.admin.users.service.UserService;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller("complexController")
@RequestMapping("admin/complexes/*")
public class ComplexController {

    private static final Logger logger = LoggerFactory.getLogger(ComplexController.class);

    @Autowired
    private ComplexService complexService;

    @Autowired
    private ComplexConst complexConst;

    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private AdminConst adminConst;

    @Autowired
    private ImageStoreService imageStoreService;


    @RequestMapping(value = "list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView complexList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute ComplexInfo complexInfo
    ) {
        // 전체현장 목록 가져오기

        AdminInfo adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        logger.debug("====================> 현장 목록 리스트");
        logger.debug("====================> complexInfo.getCmplxId : {} ", complexInfo.getCmplxId());
        logger.debug("====================> complexInfo.getCmplxNm : {} ", complexInfo.getCmplxNm());
        logger.debug("====================> complexInfo.getCmplxGrp : {} ", complexInfo.getCmplxGrp());

        List<ComplexInfo> complexList = complexService.getComplexList();

//        mav.addObject("grpId",      grpId);
//        mav.addObject("adminConst", adminConst);
        mav.addObject("adminInfo", adminInfo);
        mav.addObject("complexList", complexList);

        return mav;
    }


    @RequestMapping(value = "write.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView complexWrite (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute ComplexInfo complexInfo
    ) {
        AdminInfo adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        logger.debug("====================> 현장 상세 정보 가져오기");
        logger.debug("====================> complexInfo.getCmplxId : {} ", complexInfo.getCmplxId());
        logger.debug("====================> complexInfo.getCmplxNm : {} ", complexInfo.getCmplxNm());
        logger.debug("====================> complexInfo.getCmplxGrp : {} ", complexInfo.getCmplxGrp());

        ComplexInfoDetail complexDetail = complexService.getComplexById( complexInfo.getCmplxId() );

        mav.addObject("adminInfo", adminInfo);
        mav.addObject("complexDetail", complexDetail );

        return mav;
    }

    @RequestMapping(value = "category.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView complexCategorize (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute ComplexInfo complexInfo
    ) {
        AdminInfo adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        List<ComplexInfo> complexList;

        if( complexInfo == null ) {
            complexInfo = new ComplexInfo();
        }

        // 현장 분류 하기
        logger.debug("====================> 현장 분류하기 가져오기 ");
        logger.debug("====================> complexInfo.getCmplxId : {} ", complexInfo.getCmplxId());
        logger.debug("====================> complexInfo.getCmplxNm : {} ", complexInfo.getCmplxNm());
        logger.debug("====================> complexInfo.getCmplxGrp : {} ", complexInfo.getCmplxGrp());

        complexList = complexService.getComplexList();

        mav.addObject("adminInfo",    adminInfo);
        mav.addObject("complexList",  complexList);
        mav.addObject("complexConst", complexConst);

        return mav;
    }

    @RequestMapping(
            value = "updateCategory.do",
            method= {RequestMethod.POST} )
    public ResponseEntity complexUpdateCategory(HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute ComplexInfo complexInfo
    ) {
        int      updatedCount = -1;
        ModelMap ret = new ModelMap();

        logger.debug("====================> 현장 그룹 변경하기");
        logger.debug("====================> complexInfo.getCmplxId : {} ", complexInfo.getCmplxId());
        logger.debug("====================> complexInfo.getCmplxGrpId : {} ", complexInfo.getCmplxGrpId());

        try {
            updatedCount = complexService.updateComplexGroupTypeById(
                    complexInfo.getCmplxId(),
                    complexInfo.getCmplxGrpId());
        } catch( Exception e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new SimpleErrorInfo( e.getMessage() ));
        }

        if( updatedCount > 0 ) {
            ret.put("result", true);
            ret.put("msg", "성공");
        } else {
            ret.put("result", false);
            ret.put("msg", "변경할 값이 없습니다.");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ret);
    }


    /**
     * 현장 관리 - 상세보기 페이지
     */
    @RequestMapping(value = "complexDetail.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView complexDetail (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute ComplexInfoDetail complexInfo
    ) {
        ComplexInfoDetail resultComplexInfo;
        int               totalUserCount;
        List<AdminInfo>   managerList;
        AdminInfo         adminInfoParam;
        List<ComplexRegion> regionList;
        AdminInfo            adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        logger.debug("====================> 현장 상세 정보 페이지 ");
        logger.debug("====================> complexInfo.getCmplxId : {} ", complexInfo.getCmplxId());
        logger.debug("====================> complexInfo.getCmplxNm : {} ", complexInfo.getCmplxNm());
        logger.debug("====================> complexInfo.getCmplxGrp : {} ", complexInfo.getCmplxGrp());

        try {
            resultComplexInfo = complexService.getComplexById( complexInfo.getCmplxId() );
            if( resultComplexInfo == null ) {
                mav.addObject("error", "현장에 대한 정보가 없습니다.");
            }

            totalUserCount = userService.getTotalUserCountByComplexId( complexInfo.getCmplxId() );
        } catch( UserGeneralException e ) {
            return mav.addObject("error", e.getMessage() );
        } catch( Exception e ) {
            e.printStackTrace();;
            return mav.addObject("error", "내부 오류가 발생하였습니다." );
        }

        // APP 내 표시 정보 가져오기
        // 1) 지역목록 가져오기
        regionList = complexService.getComplexRegion();
        logger.debug(">> regionList> " + regionList);
        for(ComplexRegion e : regionList) {
            logger.debug(">> regionList> clCmplxRgnId: " + e.getClCmplxRgnId());
            logger.debug(">> regionList> clRgnNm: " + e.getRgnNm());
            logger.debug(">> regionList> clDispOrder: " + e.getDispOrder());
        }

        // 해당 현장의 관리자 정보 가져오기
        adminInfoParam = new AdminInfo();

        adminInfoParam.setSearchType1("CMPLX_ID");
        adminInfoParam.setSearchKeyword1(String.valueOf( complexInfo.getCmplxId() ));
        managerList = managerService.selectManagerList( adminInfoParam );

        mav.addObject("complexDetail",  resultComplexInfo);
        mav.addObject("totalUserCount", totalUserCount);
        mav.addObject("managerList",    managerList);
        mav.addObject("adminConst",     adminConst);
        mav.addObject("managerInfo",    new AdminInfo());  // Manager 화면전환시 이용
        mav.addObject("adminInfo",      adminInfo ); // 로그인한 관리자 정보
        mav.addObject("regionList",     regionList);

        return mav;
    }


    /**
     * 현장 대표 이미지 변경
     */
//    @PutMapping(
//            value = "/{cmplxId}/image",
//            produces = MediaType.APPLICATION_JSON_VALUE )
//    public ResponseEntity updateComplexImage( HttpServletRequest       request,
//                                              @PathVariable("cmplxId") int cmplxId ) {
//        AdminInfo adminInfo;
//
//        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
//        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
//        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());
//
//        int imageIdx = Integer.valueOf( request.getParameter("imageIdx" ));
//
////        imageStoreService.
//
//
//        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("Success!") );
//    }

    @PutMapping(
            value = "/{cmplxId}/name",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity updateComplexName( HttpServletRequest              request,
                                             @PathVariable("cmplxId") int    cmplxId,
                                             @RequestParam("name")    String name ) {
        AdminInfo adminInfo;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        try {
            complexService.updateComplexName( cmplxId, name, adminInfo.getAdminIdx() );
        } catch ( Exception e ) {
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( new SimpleErrorInfo( e.getMessage() ) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("Success!") );
    }


    @PutMapping(
            value = "/{cmplxId}/addr",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity updateComplexAddr( HttpServletRequest              request,
                                             @PathVariable("cmplxId") int    cmplxId,
                                             @RequestParam("addr")    String addr ) {
        AdminInfo adminInfo;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        try {
            complexService.updateComplexAddr( cmplxId, addr, adminInfo.getAdminIdx() );
        } catch ( Exception e ) {
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( new SimpleErrorInfo( e.getMessage() ) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("Success!") );
    }

    @PutMapping(
            value = "/{cmplxId}/mapSrc",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity updateComplexMapSrc( HttpServletRequest              request,
                                             @PathVariable("cmplxId") int    cmplxId,
                                             @RequestParam("mapSrc")    String mapSrc ) {
        AdminInfo adminInfo;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        try {
            complexService.updateComplexMapSrc( cmplxId, mapSrc, adminInfo.getAdminIdx() );
        } catch ( Exception e ) {
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( new SimpleErrorInfo( e.getMessage() ) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("Success!") );
    }


}
