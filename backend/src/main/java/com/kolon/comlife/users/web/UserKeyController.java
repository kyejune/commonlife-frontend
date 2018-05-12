package com.kolon.comlife.users.web;


import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.users.service.UserKeyService;
import com.kolonbenit.benitware.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/users")
public class UserKeyController {
    private static final Logger logger = LoggerFactory.getLogger(UserKeyController.class);

    @Autowired
    UserKeyService userKeyService;

    @GetMapping(
            value="/keypair",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getKeyPair(HttpServletRequest request) {

        Map<String, String> result;

        try {

            result = userKeyService.createKeyPair();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( new SimpleErrorInfo(
                            "내부 오류가 발생하였습니다. 관리자에게 문의하세요. \nerror msg: " + e.getMessage()) );
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( new SimpleErrorInfo(
                            "내부 오류가 발생하였습니다. 관리자에게 문의하세요. \nerror msg: " + e.getMessage()) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    /**
     * TODO: Production에서는 제거 할 것
     */
    @GetMapping(
            value="/keypair/test",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getKeyPairTest(HttpServletRequest request) {

        Map<String, String> result;

        String pk_key;
        String userId;
        String userPw;
        PrivateKey privateKey;

        result = new HashMap<>();

        pk_key = request.getParameter("pk_key");
        userId = request.getParameter("userId");
        userPw = request.getParameter("userPw");

        logger.debug("SEC_PK_KEY >>>> " + pk_key);
        logger.debug("SEC_USER_ID>>>> " + userId);
        logger.debug("SEC_USER_PW>>>> " + userPw);

        try {
            privateKey = userKeyService.getPrivateKey( pk_key );

            userId = StringUtil.decryptRsa( privateKey, userId );
            userPw = StringUtil.decryptRsa( privateKey, userPw );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( new SimpleErrorInfo(
                            "내부 오류가 발생하였습니다. 관리자에게 문의하세요. \nerror msg: " + e.getMessage()) );
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( new SimpleErrorInfo(
                            "내부 오류가 발생하였습니다. 관리자에게 문의하세요. \nerror msg: " + e.getMessage()) );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( new SimpleErrorInfo(
                            "내부 오류가 발생하였습니다. 관리자에게 문의하세요. \nerror msg: " + e.getMessage()) );
        }

        result.put( "userId", userId );
        result.put( "userPw", userPw );

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }



}
