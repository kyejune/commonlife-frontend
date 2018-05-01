package com.kolon.comlife.admin.users.web;

import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.users.model.UserExtInfo;
import com.kolon.comlife.admin.users.model.UserInfo;
import com.kolon.comlife.admin.users.service.UserService;
import com.kolon.comlife.common.paginate.PaginateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/users/*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final int LIMIT_COUNT = 20;  // 한 번에 로딩할 게시글 갯수

    private static final String USER_TYPE_USER = "user";
    private static final String USER_TYPE_HEAD = "head";

    @Autowired
    UserService userService;


    private ModelAndView getListInternal( ModelAndView mav, String userType, int pageNum ) {
        AdminInfo            adminInfo;
        PaginateInfo         paginateInfo;
        Map<String, Object>  params = new HashMap<>();

        List<UserExtInfo>    userList;
        int                  cmplxId;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        cmplxId = adminInfo.getCmplxId();

        params.put( "userType", userType );
        params.put( "limit", LIMIT_COUNT);
        params.put( "pageNum", Integer.valueOf(pageNum) );
        params.put( "cmplxId", Integer.valueOf( cmplxId ) );
        params.put( "adminIdx", adminInfo.getAdminIdx() );

        // 포스트 목록 추출
        try {
            if( USER_TYPE_USER.equals( userType ) ) {
                paginateInfo = userService.getUserListByComplexId( params );
            } else {
                paginateInfo = userService.getHeadListByComplexId( params );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return mav.addObject("error",
                    "목록 가져오기를 실패했습니다. 문제가 지속되면 담당자에게 문의하세요." );
        }

        userList = paginateInfo.getData();

        logger.debug(">>>> paginateInfo.getTotalRecordCount:" + paginateInfo.getTotalRecordCount() );
        logger.debug(">>>> paginateInfo.getPageSize:" + paginateInfo.getPageSize());
        logger.debug(">>>> paginateInfo.getTotalPageCount:" + paginateInfo.getTotalPageCount() );

        mav.addObject("userType", userType);
        mav.addObject("paginateInfo", paginateInfo);
        mav.addObject("userList", userList);
        mav.addObject("pageNum", pageNum);

        mav.setViewName("admin/users/userList");

        return mav;
    }

    @GetMapping( value = "headList.*" )
    public ModelAndView getHeadList( ModelAndView        mav,
                                     @RequestParam(required=false, defaultValue = "1") int pageNum ) {

        return this.getListInternal( mav, USER_TYPE_HEAD, pageNum );
    }

    @GetMapping( value = "userList.*" )
    public ModelAndView getUserList( ModelAndView        mav,
                                      @RequestParam(required=false, defaultValue = "1") int pageNum ) {

        return this.getListInternal( mav, USER_TYPE_USER, pageNum );
    }

}
