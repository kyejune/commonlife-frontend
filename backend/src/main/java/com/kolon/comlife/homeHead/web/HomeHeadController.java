package com.kolon.comlife.homeHead.web;

import com.kolon.comlife.homeHead.model.HomeHeadInfo;
import com.kolon.comlife.homeHead.service.HomeHeadService;
import com.kolon.comlife.reservation.service.ReservationSchemeService;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/home-head/*")
public class HomeHeadController {

    private static final Logger logger = LoggerFactory.getLogger(HomeHeadController.class);

    @Resource(name = "homeHeadService")
    HomeHeadService service;

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(HttpServletRequest request) {
        AuthUserInfo authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );

        // TODO: 로컬에서 인증 정보가 없으면 yunamkim(userId: 632)을 기본값으로 출력한다. 추후 수정 필요.
        int idx = 632;
        if( authUserInfo != null ) {
            idx = authUserInfo.getHomeId();
        }
        HomeHeadInfo info = service.show( idx );
        return ResponseEntity.status( HttpStatus.OK ).body( info );
    }

}
