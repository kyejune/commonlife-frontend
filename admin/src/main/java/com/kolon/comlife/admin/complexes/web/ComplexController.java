
package com.kolon.comlife.admin.complexes.web;


import com.kolon.comlife.admin.complexes.model.ComplexConst;
import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.manager.model.AdminConst;
import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.manager.service.ManagerService;
import com.kolon.comlife.admin.users.exception.UserGeneralException;
import com.kolon.comlife.admin.users.service.UserService;
import com.kolon.comlife.admin.users.service.impl.UserSerivceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



    @RequestMapping(value = "list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView complexList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute ComplexInfo complexInfo
    ) {
        // 전체현장 목록 가져오기

        logger.debug("====================> 현장 목록 리스트!!!!!!!!!!!!!!!!!!!!!!!!! ");
        logger.debug("====================> complexInfo.getCmplxId : {} ", complexInfo.getCmplxId());
        logger.debug("====================> complexInfo.getCmplxNm : {} ", complexInfo.getCmplxNm());
        logger.debug("====================> complexInfo.getCmplxGrp : {} ", complexInfo.getCmplxGrp());

        List<ComplexInfo> complexList = complexService.getComplexList();

//        mav.addObject("grpId",      grpId);
//        mav.addObject("adminConst", adminConst);
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
        // 현장 상세 정보 가져오기

        logger.debug("====================> 현장 상세 정보 가져오기!!!!!!!!!!!!!!!!!!!!!!!!! ");
        logger.debug("====================> complexInfo.getCmplxId : {} ", complexInfo.getCmplxId());
        logger.debug("====================> complexInfo.getCmplxNm : {} ", complexInfo.getCmplxNm());
        logger.debug("====================> complexInfo.getCmplxGrp : {} ", complexInfo.getCmplxGrp());

        ComplexInfoDetail complexDetail = complexService.getComplexById( complexInfo.getCmplxId() );

//        mav.addObject("grpId",      grpId);
//        mav.addObject("adminConst", adminConst);
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

        if( complexInfo == null ) {
            complexInfo = new ComplexInfo();
        }

        // 현장 분류 하기
        logger.debug("====================> 현장 분류하기 가져오기!!!!!!!!!!!!!!!!!!!!!!!!! ");
        logger.debug("====================> complexInfo.getCmplxId : {} ", complexInfo.getCmplxId());
        logger.debug("====================> complexInfo.getCmplxNm : {} ", complexInfo.getCmplxNm());
        logger.debug("====================> complexInfo.getCmplxGrp : {} ", complexInfo.getCmplxGrp());

        List<ComplexInfo> complexList = complexService.getComplexList();

//        mav.addObject("grpId",      grpId);
        mav.addObject("complexList", complexList);
        mav.addObject("complexConst", complexConst);

        return mav;
    }

    @RequestMapping(value = "updateCategory.do", method= {RequestMethod.POST})
    public ResponseEntity complexUpdateCategory(HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute ComplexInfo complexInfo
    ) {
        logger.debug("====================> 현장 그룹 변경하기!!!!!!!!!!!!!!!!!!!!!!!!! ");
        logger.debug("====================> complexInfo.getCmplxId : {} ", complexInfo.getCmplxId());
        logger.debug("====================> complexInfo.getCmplxGrpId : {} ", complexInfo.getCmplxGrpId());

        ModelMap ret = new ModelMap();

        int count = complexService.updateComplexGroupTypeById(
                                        complexInfo.getCmplxId(),
                                        complexInfo.getCmplxGrpId() );

        if( count > 0 ) {
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
        List<AdminInfo> managerList;

        logger.debug("====================> 현장 상세 정보 페이지 !!!!!!!!!!!!!!!!!!!!!!!!! ");
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

        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setSearchType1("CMPLX_ID");
        adminInfo.setSearchKeyword1(String.valueOf( complexInfo.getCmplxId() ));
        managerList = managerService.selectManagerList(adminInfo);

        mav.addObject("complexDetail",  resultComplexInfo);
        mav.addObject("totalUserCount", totalUserCount);
        mav.addObject("managerList",    managerList);
        mav.addObject("adminConst",     adminConst);
        mav.addObject("adminInfo",     new AdminInfo());  // Manager 화면전환시 이용

        return mav;
    }
}
