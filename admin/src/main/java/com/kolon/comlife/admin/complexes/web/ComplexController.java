package com.kolon.comlife.admin.complexes.web;


import com.kolon.comlife.admin.complexes.model.ComplexConst;
import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        ComplexInfo complexDetail = complexService.getComplexById( complexInfo.getCmplxId() );

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

    @RequestMapping(value = "updateCategory.do", method= {RequestMethod.PUT})
    public ModelAndView complexUpdateCategory(  HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute Map<String, Integer> parameters
    ) {
        logger.debug("====================> 현장 그룹 변경하기!!!!!!!!!!!!!!!!!!!!!!!!! ");
        logger.debug("====================> complexInfo.getCmplxId : {} ", parameters.get("cmplxId"));
        logger.debug("====================> complexInfo.getCmplxGrpId : {} ", parameters.get("cmplxGrpId"));

        ModelMap ret = new ModelMap();
        int count = complexService.updateComplexGroupTypeById(parameters.get("cmplxId"), parameters.get("cmplxGrpId"));

        if( count > 0 ) {
            ret.put("result", true);
            ret.put("msg", "성공");
        } else {
            ret.put("result", false);
            ret.put("msg", "실패");
        }

        return new ModelAndView( "jsonView", ret);


    }

}
