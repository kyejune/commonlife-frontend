package com.kolon.comlife.users.web;

import com.kolon.comlife.users.service.UserDebugService;
import com.kolon.comlife.users.util.IokUtil;
import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import com.kolonbenit.iot.mobile.controller.MobileUserCertNoController;
import com.kolonbenit.iot.mobile.service.MobileUserCertNoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * Test 및 debug 용 Controller
 *
 * todo: WARNING!!! 접근 권한 설정해야 하거나, Production에는 제거해야 됨
 */

@Controller
@RequestMapping("/users/debug/")
public class UserDebugController {

    private final static Logger logger = LoggerFactory.getLogger( UserDebugController.class );

    @Autowired
    private MobileUserCertNoService mobileUserCertNoService;

    @Autowired
    private UserDebugService userDebugService;

    /**
     *
     * params : userCertId, headNm, headCell
     */
    @GetMapping(
            value = "headCertNum",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getHeadCertNumByUserId( HttpServletRequest request ) {
        Map result;
        RequestParameter parameter = IokUtil.buildRequestParameter( request );

        result = userDebugService.getHeadCert( parameter );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     *
     * params : userCertId, userCell
     */
    @GetMapping(
            value = "userCertNum",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getUserCertNumByUserId( HttpServletRequest request ) {
        Map result;
        RequestParameter parameter = IokUtil.buildRequestParameter( request );

        result = userDebugService.getUserCert( parameter );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }



}
