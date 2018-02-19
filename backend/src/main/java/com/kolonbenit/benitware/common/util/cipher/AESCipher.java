package com.kolonbenit.benitware.common.util.cipher;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {

	/**
	 * Key Size is 128bit(16자), 192bit(24자), 256bit(32자)
	 * 	- ASP와 통신 고려할 경우, 128bit 사용
	 */
	private final static byte[] IV_BYTES = new byte[16];

	private final static String CHARSET = "UTF-8";

	/**
	 * AES 암호화
	 * @param plainStr
	 * @param keyData
	 * @return
	 */
	public static String encodeAES(String plainStr, String keyData) {
		String enStr = "";
		try {
			SecretKey secretKey = new SecretKeySpec(keyData.getBytes(CHARSET), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// 3번째 인자인 IvParameterSpec 생략 가능
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(IV_BYTES));

			byte[] encrypted = cipher.doFinal(plainStr.getBytes(CHARSET));
			enStr = Base64.encodeBase64String(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enStr;
	}

	/**
	 * AES 복호화
	 * @param encryptStr
	 * @param keyData
	 * @return
	 */
	public static String decodeAES(String encryptStr, String keyData) {
		String deStr = "";
		try {
			SecretKey secretKey = new SecretKeySpec(keyData.getBytes(CHARSET), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// 3번째 인자인 IvParameterSpec 생략 가능
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(IV_BYTES));

			byte[] textBytes = Base64.decodeBase64(encryptStr);
			deStr = new String(cipher.doFinal(textBytes), CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deStr;
	}

}