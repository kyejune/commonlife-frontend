package com.kolon.comlife.push.web;

import com.kolon.comlife.push.model.PushHistoryInfo;
import com.kolon.comlife.push.service.PushHistoryService;
import com.kolon.comlife.push.service.PushService;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/push/*")
public class PushController {
    private static final Logger logger = LoggerFactory.getLogger(PushController.class);

    @Resource(name="pushService")
    private PushService service;

    @Resource(name="pushHistoryService")
    private PushHistoryService historyService;

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(HttpServletRequest request) {
        AuthUserInfo authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );

        // TODO: 로컬에서 인증 정보가 없으면 test003을 기본값으로 출력한다. 추후 수정 필요.
        String userId = "test003";
        if( authUserInfo != null ) {
            userId = authUserInfo.getUserId();
        }

        HashMap params = new HashMap();
        params.put( "userId", userId );
        List<PushHistoryInfo> history = historyService.index( params );
        return ResponseEntity.status( HttpStatus.OK ).body( history );
    }

    @CrossOrigin
    @PostMapping(
            value = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(HttpServletRequest request) {
        // token과 deviceId, osType은 앱단에서 넘겨받는다
        String gcmRegId = request.getParameter( "pushToken" );
        String deviceId = request.getParameter( "deviceId" );
        String osType = request.getParameter( "osType" );

        // MB00901 - Android
        // MB00902 - iOS
        if( osType.equals( "IOS" ) ) {
            osType = "MB00902";
        }
        else {
            osType = "MB00901";
        }

        AuthUserInfo authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );

        // TODO: 로컬에서 인증 정보가 없으면 test003을 기본값으로 출력한다. 추후 수정 필요.
        String userId = "test003";
        int cmplxId = 127;
        int homeId = 1;
        int usrId = 736;
        if( authUserInfo != null ) {
            userId = authUserInfo.getUserId();
            cmplxId = authUserInfo.getCmplxId();
            homeId = authUserInfo.getHomeId();
            usrId = authUserInfo.getUsrId();
        }

        HashMap params = new HashMap();
        params.put( "regId", userId );
        params.put( "cmplxId", cmplxId );
        params.put( "homeId", homeId );
        params.put( "usrId", usrId );
        params.put( "gcmRegId", gcmRegId );
        params.put( "deviceId", deviceId );
        params.put( "osType", osType );

        return ResponseEntity.status( HttpStatus.OK ).body( service.register( params ) );
    }
}
