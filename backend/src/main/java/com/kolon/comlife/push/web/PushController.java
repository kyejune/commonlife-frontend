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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
