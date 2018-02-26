package com.kolon.comlife.admin.manager.web;

import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.manager.model.ManagerInfo;
import com.kolon.comlife.admin.manager.service.ManagerService;
import com.kolon.common.admin.model.BaseUserInfo;
import com.kolon.common.admin.pagination.PaginationInfoExtension;
import com.kolon.common.admin.pagination.PaginationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;


/**
 * 관리자 컨트롤러
 */
@Controller("managerController")
@RequestMapping("admin/managers/*")
public class ManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Resource(name = "managerService")
    private ManagerService managerService;


    /**
     * 관리자 리스트
     * @param request
     * @param response
     * @param mav
     * @return
     */
    @RequestMapping(value = "list.*", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView managerList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute AdminInfo adminInfo
    ) {
        if(adminInfo.getSearchKeyword1() == null){
            adminInfo.setSearchKeyword1("");
        }

        logger.debug("====================> 관리자 리스트!!!!!!!!!!!!!!!!!!!!!!!!! ");
        logger.debug("====================> request.getParameter('pageIndex')  : " + request.getParameter("pageIndex"));
        logger.debug("====================> adminInfo.getPageIndex : {} ",adminInfo.getPageIndex());
        logger.debug("====================> adminInfo.getSearchKeyword1 : {} ",adminInfo.getSearchKeyword1());

        List<AdminInfo> managerList = managerService.selectManagerList(adminInfo);

        PaginationInfoExtension pagination = PaginationSupport.setPaginationVO(request, adminInfo, "1", adminInfo.getRecordCountPerPage(), 10);
        pagination.setTotalRecordCountVO(managerList);

        mav.addObject("managerList", managerList);
        mav.addObject("pagination", pagination);

        return mav;
    }

    /**
     * 관리자 상세화면
     * @param request
     * @param response
     * @param mav
     * @return
     */
    @RequestMapping(value = "write.*", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView managerDetail (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute AdminInfo adminInfo
    ) {
        logger.debug("====================> adminInfo.getAdminId: {} ",adminInfo.getAdminId());

        AdminInfo managerDetail = managerService.selectManagerDetail(adminInfo);
        PaginationInfoExtension pagination = PaginationSupport.setPaginationVO(
                request,
                adminInfo,
                "1",
                adminInfo.getRecordCountPerPage(),
                10);

        mav.addObject("managerDetail", managerDetail);

        return mav;
    }

    /**
     * 관리자 처리 (등록/수정/삭제)
     * @param request
                    * @param response
                    * @param mav
                    * @return
                    */
                    @RequestMapping(value = "proc.*", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView managerProc (
            Principal principal
            , HttpServletRequest request
            , HttpServletResponse response
            , ModelMap model
            , ModelAndView mav
            , @ModelAttribute ManagerInfo managerInfo
    ) {
        BaseUserInfo baseUserInfo = (BaseUserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        int rs = -1;

        managerInfo.setRegUserId(baseUserInfo.getMngId());
        managerInfo.setUpdUserId(baseUserInfo.getMngId());

        if("INS".equals(managerInfo.getMode())){
            rs = managerService.insertManager(managerInfo);
        }else{
            rs = managerService.updateManager(managerInfo);
        }

        model.addAttribute("result", rs > 0);

        return new ModelAndView("jsonView", model);
    }
}
