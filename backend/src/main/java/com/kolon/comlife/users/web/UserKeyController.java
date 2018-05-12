package com.kolon.comlife.users.web;


import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.common.helper.JedisHelper;
import com.kolonbenit.benitware.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/users")
public class UserKeyController {
    private static final Logger logger = LoggerFactory.getLogger(UserKeyController.class);
    private static final int KEY_SIZE = 1024; // must be longer than 512
    private static final int KEY_EXPIRE_SEC = 60; // 60 seconds
    private static final String UUID_PREFIX = "PK_";

    private static final JedisHelper helper = JedisHelper.getInstance();

    @GetMapping(
            value="/keypair",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getKeyPair(HttpServletRequest request) {
        Map<String, String> result;
        KeyPairGenerator    generator;
        KeyPair             keyPair;
        KeyFactory          keyFactory;
        PublicKey           publicKey;
        PrivateKey          privateKey;
        RSAPublicKeySpec    publicSpec;
        String              publicKeyModulus;
        String              publicKeyExponent;
        UUID                uuid;
        String              pk_key;

        Jedis jedis;

        uuid = UUID.randomUUID();
        pk_key = UUID_PREFIX + uuid.toString();

        result = new HashMap<>();

        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize( KEY_SIZE );

            keyPair = generator.genKeyPair();
            keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();

            // todo: private key는 Redis에 저장해야 함
            publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec( publicKey, RSAPublicKeySpec.class );
        } catch ( Exception e ) {
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( new SimpleErrorInfo("내부 오류가 발생하였습니다. 관리자에게 문의하세요. \nerror msg: " + e.getMessage()) );
        }

        publicKeyModulus  = publicSpec.getModulus().toString(16);
        publicKeyExponent = publicSpec.getPublicExponent().toString(16);

        jedis = helper.getConnection();

        result.put("pk_key", pk_key);
        result.put("publicKeyModulus" , publicKeyModulus);
        result.put("publicKeyExponent" , publicKeyExponent);

        // jedis 추가
        jedis.set( pk_key.getBytes(), privateKey.getEncoded() );
        jedis.expire( pk_key.getBytes(), KEY_EXPIRE_SEC );

        helper.returnResource( jedis );

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
        KeyFactory kf;

        String pk_key;
        String userId;
        String userPw;

        Jedis jedis;

        byte[] privateKeyBytes;
        PrivateKey privateKey;

        result = new HashMap<>();

        pk_key = request.getParameter("pk_key");
        userId = request.getParameter("userId");
        userPw = request.getParameter("userPw");

        logger.debug("SEC_PK_KEY >>>> " + pk_key);
        logger.debug("SEC_USER_ID>>>> " + userId);
        logger.debug("SEC_USER_PW>>>> " + userPw);

        jedis = helper.getConnection();

        privateKeyBytes = jedis.get(pk_key.getBytes());
        logger.debug("PRIVATE_KEY_BYTES>>>> " + privateKeyBytes);

        helper.returnResource( jedis );

        try {
            kf = KeyFactory.getInstance("RSA");
            kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
            privateKey = kf.generatePrivate( new PKCS8EncodedKeySpec (privateKeyBytes ) );

            userId = StringUtil.decryptRsa( privateKey, userId );
            userPw = StringUtil.decryptRsa( privateKey, userPw );
        } catch( Exception e ) {
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( new SimpleErrorInfo("내부 오류가 발생하였습니다. 관리자에게 문의하세요. \nerror msg: " + e.getMessage()) );
        }

        result.put( "userId", userId );
        result.put( "userPw", userPw );

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }



}
