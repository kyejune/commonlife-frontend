package com.kolon.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {

	private final static Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	private final static String SECRET = "com.smartiok.kolon";
	private final static String ISSUER = "smartiok";
	
	public static String encodeJwt(Map<String, String> claims) {
		return encodeJwt(SECRET, ISSUER, claims);
	}
	
	public static String encodeJwt(String pSecret, Map<String, String> claims) {
//		return encodeJwt(SECRET, ISSUER, claims);
		return encodeJwt(pSecret, ISSUER, claims);
	}
	
	public static DecodedJWT decodeJwt(String sEncJwt) {
		return decodeJwt(SECRET, ISSUER, sEncJwt);
	}
	
	public static DecodedJWT decodeJwt(String pSecret, String sEncJwt) {
//		return decodeJwt(SECRET, ISSUER, sEncJwt);
		logger.info("decodeJwt(String pSecret, String sEncJwt) : ");
		return decodeJwt(pSecret, ISSUER, sEncJwt);
	}
	
	
	public static String encodeJwt(String secret, String issuer, Map<String, String> claims) {
		
		String sEncJwt = null;
		
		try {
			
			Builder builder = JWT.create().withIssuer(issuer);
			//Builder builder = JWT.create();
//			JsonNode aa = null;
//			
//			aa.asText();
			
			
			logger.info("secret key : {}",secret);
			for (Map.Entry<String, String> entry : claims.entrySet()) {
				logger.info("getKey : {},  getValue : {}",entry.getKey(), entry.getValue());	
				builder.withClaim(entry.getKey(), entry.getValue());
			}
			
			Date expiresAt = new Date(System.currentTimeMillis() + (1000*60*60*12));
			builder.withExpiresAt(expiresAt);			
			sEncJwt = builder.sign(Algorithm.HMAC256(secret));
			
		} catch (IllegalArgumentException e) {
			logger.error("JwtUtils IllegalArgumentException Occured.");
			e.printStackTrace();
		} catch (JWTCreationException e) {
			logger.error("JwtUtils JWTCreationException Occured.");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error("JwtUtils UnsupportedEncodingException Occured.");
			e.printStackTrace();
		}

		return sEncJwt;
	}
	
	public static DecodedJWT decodeJwt(String secret, String issuer, String sEncJwt) {
		
		DecodedJWT decJwt = null;
		
		try {
			//com.fasterxml.jackson.databind.JsonNode.asText("");
			logger.info("--------> Decode Start");
			JWT.decode(sEncJwt);
			logger.info("<-------- Decode End");
		} catch (JWTDecodeException de) {			
			logger.error("JwtUtils JWTDecodeException Occured.");
			de.printStackTrace();
			return decJwt;
		} catch (Exception e) {
			logger.error("JwtUtils Exception Occured. [{}]", e.toString());
			e.printStackTrace();
			return decJwt;
		}
		
//		logger.info("계속됩니다. =========================");
		
		try {			
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withIssuer(issuer).build();			
			decJwt = verifier.verify(sEncJwt);			
		} catch (IllegalArgumentException e) {
			logger.error("JwtUtils IllegalArgumentException Occured.");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error("JwtUtils UnsupportedEncodingException Occured.");
			e.printStackTrace();
		} catch (JWTVerificationException e) {
			logger.error("JwtUtils JWTVerificationException Occured.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("JwtUtils Exception Occured. [{}]", e.toString());
			e.printStackTrace();
		}
		
		return decJwt;
	}
	

	public static void main(String[] args) {
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("id", "cv10201");
		m.put("cmplxId", "119");
		m.put("homeId", "1");
		
		String sEncJwt = ""; //"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDM5ODE1MjksIlVTRVJfSUQiOiJ5czMzIiwiaXNzIjoic21hcnRpb2sifQ.7Ht5vxgt18zJWDcwxigmiM3b9kkPZWWm5NPXCRwEPng";
		String secret  = ""; //"-3cae7fc827f99733";
		String issuer  = "smartiok"; 
		
//		sEncJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDQ1MjA1MzEsIlVTRVJfSUQiOiJ5czMzIiwiaXNzIjoic21hcnRpb2sifQ.kIIeE_T6cN5wt647yim_PjMYFWGuvIzUoNv6eY55_ac";
//		secret  = "-4a5d719131dadbe3";
		
		sEncJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDQ1MjU4MjcsIlVTRVJfSUQiOiJ5czMzIiwiaXNzIjoic21hcnRpb2sifQ.hnw--1Z-JoDTO80MIHtAqqDbMpTQ580QVjWjxBXsI98";
		secret  = "-31c0a2aba8dd8129";
		
		//encodeJwt(m);
		logger.info("sEncJwt [{}]", sEncJwt);
		
		//DecodedJWT decJwt = decodeJwt(sEncJwt);
		DecodedJWT decJwt = decodeJwt(secret, sEncJwt);		
		
		if (decJwt != null) {
			logger.info("=================== test_verifyJwtToken ===================");
			logger.info("jwt token : " + decJwt.getToken());
			logger.info("jwt algorithm : " + decJwt.getAlgorithm());
			logger.info("jwt claims : " + decJwt.getClaims());
			logger.info("jwt issuer : " + decJwt.getIssuer());
			logger.info("jwt issuer date : " + decJwt.getIssuedAt());
			logger.info("jwt expires date : " + decJwt.getExpiresAt());
			logger.info("jwt signature : " + decJwt.getSignature());
			logger.info("jwt type : " + decJwt.getType());
			logger.info("jwt key id : " + decJwt.getKeyId());
			logger.info("jwt id : " + decJwt.getId());
			logger.info("jwt subject : " + decJwt.getSubject());
			logger.info("jwt payload : " + decJwt.getPayload());
			logger.info("jwt content type : " + decJwt.getContentType());
			logger.info("jwt audience list : " + decJwt.getAudience());
			
			try {			
				JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withIssuer(issuer).build();			
				decJwt = verifier.verify(sEncJwt);
				
			} catch (IllegalArgumentException e) {
				logger.error("JwtUtils IllegalArgumentException Occured.");
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				logger.error("JwtUtils UnsupportedEncod ingException Occured.");
				e.printStackTrace();
			} catch (JWTVerificationException e) {
				logger.error("JwtUtils JWTVerificationException Occured.");
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("JwtUtils Exception Occured. [{}]", e.toString());
				e.printStackTrace();
			}
			
			Map<String, Claim> claims = decJwt.getClaims();
			
			logger.info("");
			logger.info("=================== claims ===================");
			for (Map.Entry<String, Claim> entry : claims.entrySet()) {
				logger.info("claims key : {}, value : {}", entry.getKey(), entry.getValue().asString());
			}
		}
		
		logger.info("=========   Verification The End   ==========");
	}
	
}
