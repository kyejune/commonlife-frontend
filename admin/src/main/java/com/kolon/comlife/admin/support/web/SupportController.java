package com.kolon.comlife.admin.support.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.support.exception.SupportGeneralException;
import com.kolon.comlife.admin.support.model.SupportCategoryInfo;
import com.kolon.comlife.admin.support.service.SupportService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller("supportController")
@RequestMapping("admin/support/*")
public class SupportController {

    private static final Logger logger = LoggerFactory.getLogger(SupportController.class);

    @Autowired
    private ComplexService complexService;

    @Autowired
    private SupportService supportService;

    @GetMapping( value = "categoryList.do" )
    public ModelAndView categoryList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute("categoryInfo") SupportCategoryInfo categoryInfo
    ) {

        AdminInfo                 adminInfo;
        ComplexInfoDetail         complexInfo;
        List<SupportCategoryInfo> categoryList;
        String                    categoryListJson;
        ObjectMapper              mapper = new ObjectMapper();

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        try {
            complexInfo = complexService.getComplexById(categoryInfo.getCmplxId());
            if( complexInfo == null ) {
                return mav.addObject( "error",
                                      "해당 현장에 대한 정보가 없습니다. 문제가 지속되면 담당자에게 문의하세요.");
            }

            categoryList = supportService.getCategoryInfoByComplexId(categoryInfo);
            if( categoryList == null ) {
                return mav.addObject( "error",
                                      "해당 현장에 대한 지원 분류 정보가 없습니다. 문제가 지속되면 담당자에게 문의하세요.");
            }
        } catch( SupportGeneralException e ) {
            return mav.addObject(
                    "error",
                    e.getMessage() );
        }

        try {
            categoryListJson = mapper.writeValueAsString(categoryList);
        } catch( Exception e) {
            return mav.addObject(
                    "error",
                    "작업 처리과정에 에러가 발생했습니다. 문제가 지속되면 담당자에게 문의하세요.");
        }

        mav.addObject("adminInfo", adminInfo);

        mav.addObject("complexInfo", complexInfo );
        mav.addObject("categoryInfo", categoryInfo); // Parameters
        // JSON value
        mav.addObject("categoryList", categoryListJson);
        mav.addObject("cmplxId", categoryInfo.getCmplxId());

        return mav;
    }


    @PostMapping(
            value = "procIns.do",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity procIns (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestBody List<Map<String, Object>> dispOrderParam
            , @RequestParam("cmplxId") int cmplxId
    ) {
        List<SupportCategoryInfo> dispOrderList;

        if (cmplxId < 1) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo("잘못된 현장아이디(cmplxId)를 입력했습니다. 다시 확인하세요."));
        }

        if (dispOrderParam == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo("업데이트 할 내용이 없습니다. 전송 내용을 다시 확인하세요. "));
        }


        dispOrderList = new ArrayList<>();
        for (Map e : dispOrderParam) {
            SupportCategoryInfo cateInfo = new SupportCategoryInfo();

            cateInfo.setCateIdx(Integer.parseInt((String) e.get("cateIdx")));
            cateInfo.setCmplxId(cmplxId);
            cateInfo.setDispOrder(((Integer) e.get("dispOrder")).intValue());
            cateInfo.setDelYn((String) e.get("delYn"));

            dispOrderList.add(cateInfo);


            logger.debug(">>> " + cateInfo.getCateIdx());
            logger.debug(">>> " + cateInfo.getDispOrder());
            logger.debug(">>> " + cateInfo.getDelYn());
            logger.debug("----");
        }

        try {
            supportService.updateCategoryInfoDisplayByComplexId(dispOrderList);
        } catch (SupportGeneralException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SimpleMsgInfo("업데이트를 완료했습니다."));
    }
}
