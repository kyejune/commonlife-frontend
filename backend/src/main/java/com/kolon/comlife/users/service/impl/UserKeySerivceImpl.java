package com.kolon.comlife.users.service.impl;


import com.kolon.comlife.users.service.UserKeyService;
import com.kolon.common.helper.JedisHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("userKeyService")
public class UserKeySerivceImpl implements UserKeyService {
    final static Logger logger = LoggerFactory.getLogger(  UserKeySerivceImpl.class );

    private static final JedisHelper    helper = JedisHelper.getInstance();

    private static final String         KEY_ALGORITHM  = "RSA";
    private static final int            KEY_SIZE       = 1024; // must be longer than 512
    private static final int            KEY_EXPIRE_SEC = 300; // 15 seconds
    private static final String         UUID_PREFIX    = "PK_";

    @Override
    public Map<String, String> createKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {

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

        generator = KeyPairGenerator.getInstance( KEY_ALGORITHM );
        generator.initialize( KEY_SIZE );

        keyPair    = generator.genKeyPair();
        keyFactory = KeyFactory.getInstance( KEY_ALGORITHM );

        publicKey  = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        publicSpec = keyFactory.getKeySpec( publicKey, RSAPublicKeySpec.class );

        publicKeyModulus  = publicSpec.getModulus().toString(16);
        publicKeyExponent = publicSpec.getPublicExponent().toString(16);

        result.put("pk_key",            pk_key);
        result.put("publicKeyModulus",  publicKeyModulus);
        result.put("publicKeyExponent", publicKeyExponent);

        // Redis에 Private Key 저장
        jedis = helper.getConnection();
        try {
            jedis.set( pk_key.getBytes(), privateKey.getEncoded() );
            jedis.expire( pk_key.getBytes(), KEY_EXPIRE_SEC );
        } finally {
            helper.returnResource( jedis );
        }

        return result;
    }

    @Override
    public PrivateKey getPrivateKey( String pk_key ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Jedis jedis;

        KeyFactory kf;
        byte[]     privateKeyBytes;
        PrivateKey privateKey;

        jedis = helper.getConnection();
        try {
            privateKeyBytes = jedis.get(pk_key.getBytes());
            logger.debug("PRIVATE_KEY_BYTES>>>> " + privateKeyBytes);
        } finally {
            helper.returnResource( jedis );
        }

        kf = KeyFactory.getInstance( KEY_ALGORITHM );
        kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        privateKey = kf.generatePrivate( new PKCS8EncodedKeySpec (privateKeyBytes ) );

        return privateKey;
    }
}
