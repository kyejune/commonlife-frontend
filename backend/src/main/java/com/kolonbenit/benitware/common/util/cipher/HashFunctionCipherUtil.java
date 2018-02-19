package com.kolonbenit.benitware.common.util.cipher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunctionCipherUtil {

	/**
	 * MD5 알고리즘으로 해싱처리
	 * @param text
	 * @return
	 */
	public static String hashingMD5(String text) {
		return hashing(text, "MD5");
	}

	/**
	 * <pre>
	 * 해당 알고리즘으로 해싱처리
	 *   - MD5, SHA-128, SHA-256
	 * </pre>
	 * @param text
	 * @return
	 */
	private static String hashing(String text, String algorithm) {
		MessageDigest md;
		String out = "";

		try {
			md = MessageDigest.getInstance(algorithm);
			md.update(text.getBytes());
			byte[] mb = md.digest();

			for (int i=0; i<mb.length; i++) {
				byte temp = mb[i];
				String s = Integer.toHexString(new Byte(temp));
				while (s.length() < 2) {
					s = "0" + s;
				}
				s = s.substring(s.length() - 2);
				out += s;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return out;
	}

}