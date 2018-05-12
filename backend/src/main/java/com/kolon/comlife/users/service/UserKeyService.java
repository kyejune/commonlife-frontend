package com.kolon.comlife.users.service;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public interface UserKeyService {

    Map<String, String> createKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException;

    PrivateKey getPrivateKey( String pk_key ) throws NoSuchAlgorithmException, InvalidKeySpecException;

}
