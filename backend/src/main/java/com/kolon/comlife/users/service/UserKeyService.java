package com.kolon.comlife.users.service;

import com.kolon.comlife.users.exception.KeyNotFoundException;
import com.kolon.comlife.users.exception.NotFoundException;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public interface UserKeyService {

    Map<String, String> createKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException;

    PrivateKey getPrivateKey( String pk_key ) throws NoSuchAlgorithmException, InvalidKeySpecException, KeyNotFoundException;

}
