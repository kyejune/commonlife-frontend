package com.kolon.comlife.admin.manager.web;

import com.kolon.comlife.admin.complexes.model.ComplexSimpleInfo;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.manager.model.AdminConst;
import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.manager.model.ManagerInfo;
import com.kolon.comlife.admin.manager.service.ManagerService;
import com.kolon.common.admin.model.BaseUserInfo;
import com.kolon.common.admin.pagination.PaginationInfoExtension;
import com.kolon.common.admin.pagination.PaginationSupport;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.method.P;
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
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 관리자 컨트롤러
 */
@Controller("managerController")
@RequestMapping("admin/managers/*")
public class ManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Resource(name = "managerService")
    private ManagerService managerService;

    @Resource(name ="adminConst")
    private AdminConst adminConst;

    @Autowired
    private ComplexService complexService;



    /**
     * 관리자 리스트
     * @param request
     * @param response
     * @param mav
     * @return
     */
    @RequestMapping(value = "list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView managerList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute AdminInfo adminInfo
    ) {
        String grpIdStr;
        int    grpId = -1;

        if(adminInfo.getSearchKeyword1() == null){
            adminInfo.setSearchKeyword1("");
        }

        grpIdStr = request.getParameter("grpId");
        if( grpIdStr == null ) {
//            response.setStatus(HttpStatus.SC_FORBIDDEN);
//            mav.addObject("msg", "잘못된 접근입니다.");
//            mav.setViewName("/error/400");
//            return mav;
        } else {
            try {
                grpId = Integer.parseInt(request.getParameter("grpId"));
                switch(grpId) {
                    case AdminConst.ADMIN_GRP_SUPER:
                    case AdminConst.ADMIN_GRP_COMPLEX:
                        adminInfo.setSearchType1("GRP_ID");
                        adminInfo.setSearchKeyword1(grpIdStr);
                        break;
                    default :
                        throw new NumberFormatException();
                }
            } catch( NumberFormatException e ){
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                mav.addObject("msg", "잘못된 입력입니다.");
                mav.setViewName("/error/400");
                return mav;
            }
        }

        logger.debug("====================> 관리자 리스트!!!!!!!!!!!!!!!!!!!!!!!!! ");
        logger.debug("====================> request.getParameter('pageIndex')  : " + request.getParameter("pageIndex"));
        logger.debug("====================> adminInfo.getPageIndex : {} ",adminInfo.getPageIndex());
        logger.debug("====================> adminInfo.getSearchType1: {} ",adminInfo.getSearchType1());
        logger.debug("====================> adminInfo.getSearchKeyword1 : {} ",adminInfo.getSearchKeyword1());

        List<AdminInfo> managerList = managerService.selectManagerList(adminInfo);

        mav.addObject("grpId",      grpId);
        mav.addObject("adminConst", adminConst);
        mav.addObject("managerList", managerList);

        return mav;
    }

    /**
     * 관리자 상세화면
     * @param request
     * @param response
     * @param mav
     * @return
     */
    @RequestMapping(value = "write.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView managerDetail (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute AdminInfo adminInfo
    ) {
        logger.debug("====================> adminInfo.getAdminId: {} ",adminInfo.getAdminId());

        int grpId = -1;
        AdminInfo managerDetail = null;
        String    paramCreate = request.getParameter("create");
        String    mode; // INS:신규등록 or UPD:업데이트
        List<ComplexSimpleInfo> cmplxList = complexService.getComplexSimpleList();


        try {
            grpId = Integer.parseInt(request.getParameter("grpId"));

        } catch( NumberFormatException e ){
            // 잘못 된 입력
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            mav.setViewName("/error/400");
            return mav;
        }

        // 신규 관리자 생성
        if( paramCreate != null && paramCreate.equals("true") )
        {
            managerDetail = new AdminInfo();
            // list.jsp에서 설정한 grpId  검증

            switch(grpId) {
                case AdminConst.ADMIN_GRP_SUPER:
                    managerDetail.setGrpId(AdminConst.ADMIN_GRP_SUPER);
                    break;
                case AdminConst.ADMIN_GRP_COMPLEX:
                    managerDetail.setGrpId(AdminConst.ADMIN_GRP_COMPLEX);
                    break;
                default :
                    response.setStatus(HttpStatus.SC_BAD_REQUEST);
                    mav.setViewName("/error/400");
                    return mav;
            }
            mode = "INS";
        } else {
            // 기존 사용자 정보 업데이트
            //   - 이전 사용자 정보 가져오기
            managerDetail = managerService.selectManagerDetail(adminInfo);

            if( managerDetail == null ) {
                response.setStatus(HttpStatus.SC_NOT_FOUND);
                mav.addObject("msg", "해당사용자가 없습니다.");
                mav.setViewName("/error/404");
                return mav;
            }
            mode = "UPD";
        }

        mav.addObject("grpId", grpId);
        mav.addObject("cmplxList", cmplxList);
        mav.addObject("mode", mode);
        mav.addObject("adminConst", adminConst);
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
                    @RequestMapping(value = "proc.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView managerProc (
            Principal principal
            , HttpServletRequest request
            , HttpServletResponse response
            , ModelMap model
            , ModelAndView mav
            , @ModelAttribute AdminInfo adminInfo)
    {
        int rs = -1;
//        BaseUserInfo baseUserInfo = (BaseUserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        managerInfo.setRegUserId(baseUserInfo.getMngId());
//        managerInfo.setUpdUserId(baseUserInfo.getMngId());
        // todo: 임시로 0으로 설정, 로그인 기능 도입 이후에 로그인 사용자 값에서 가져오도록 변경할 것
        adminInfo.setRegAdminIdx(1);
        adminInfo.setUpdAdminIdx(1);

        // 새로운 관리자 생성
        if( "INS".equals(adminInfo.getMode()) ){
            try {
                rs = managerService.insertManager( adminInfo );
            }catch( Exception e ) {
                if(e instanceof DuplicateKeyException) {
                    response.setStatus(HttpStatus.SC_CONFLICT);
                    return managerProcReturn( false, "아이디 또는 이메일이 이미 사용 중입니다.");
                } else {
                    response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                    return managerProcReturn( false, "내부 오류입니다. 담당자에게 문의하세요.");
                }
            }

            if( rs > 0) {
                response.setStatus(HttpStatus.SC_OK);
                return managerProcReturn( true, "성공하였습니다.");
            } else {
                response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return managerProcReturn( false, "내부 오류입니다. 담당자에게 문의하세요.");
            }

        }

        // 기존 관리자 데이터 업데이트
        if( "UPD".equals(adminInfo.getMode()) ) {
            try {
                rs = managerService.updateManager( adminInfo );
            }catch( Exception e ) {
                if(e instanceof DuplicateKeyException) {
                    response.setStatus(HttpStatus.SC_CONFLICT);
                    return managerProcReturn( false, "이메일이 이미 사용 중입니다.");
                } else {
                    response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                    return managerProcReturn( false, "내부 오류입니다. 담당자에게 문의하세요.");
                }
            }

            if( rs > 0) {
                response.setStatus(HttpStatus.SC_OK);
                return managerProcReturn( true, "업데이트 되었습니다.");
            } else {
                response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                return managerProcReturn( false, "내부 오류입니다. 담당자에게 문의하세요.");
            }
        } else {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            return managerProcReturn( false, "입력값이 잘못 되었습니다.");
        }
    }

    private ModelAndView managerProcReturn(boolean result,
                                           String  msg)
    {
        ModelMap ret = new ModelMap();

        ret.put("result", result);
        ret.put("msg", msg);

        return new ModelAndView( "jsonView", ret);
    }
}
