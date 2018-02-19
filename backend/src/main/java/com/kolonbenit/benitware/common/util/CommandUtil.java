package com.kolonbenit.benitware.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class CommandUtil {

	/**
	 * <pre>
	 * SMS 발송
	 * </pre>
	 * @param arg1 (PhoneNumber)
	 * @param arg2 (Message)
	 */
	public static void runCommandSMS(String arg1, String arg2) {
		arg2 = arg2.replaceAll(" ", "&nbsp;").replaceAll("\r\n", "<br/>").replaceAll("\n", "<br/>");
		try {
			arg2 = new String(arg2.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		StringBuffer sb = null;

		String sOsName = System.getProperty("os.name");
		if (sOsName.indexOf("Windows") > -1) {
			// 로컬 (윈도우)
			sb = new StringBuffer();
			sb.append("java -jar").append(" ");
			sb.append("C:\\awsSns\\awsSns.jar").append(" ").append("SMS").append(" ").append(arg1).append(" ").append(arg2);
		} else {
			// 운영 (리눅스)
			sb = new StringBuffer();
			sb.append("java -jar").append(" ");
			sb.append("/app/srv/awsSns/awsSns.jar").append(" ").append("SMS").append(" ").append(arg1).append(" ").append(arg2);
		}
		String sCommand = sb.toString();

		try {
			Process p = Runtime.getRuntime().exec(sCommand);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;

			while ( (line = br.readLine()) != null ) {
				System.out.println(line);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * <pre>
	 * PUSH 발송
	 * </pre>
	 * @param arg1 (RegistrationId)
	 * @param arg2 (Message)
	 */
	public static void runCommandPUSH_GCM(String arg1, String arg2) {
		arg2 = arg2.replaceAll(" ", "&nbsp;").replaceAll("\r\n", "<br/>").replaceAll("\n", "<br/>");
		try {
			arg2 = new String(arg2.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		StringBuffer sb = null;

		String sOsName = System.getProperty("os.name");
		if (sOsName.indexOf("Windows") > -1) {
			// 로컬 (윈도우)
			sb = new StringBuffer();
			sb.append("java -jar").append(" ");
			sb.append("C:\\awsSns\\awsSns.jar").append(" ").append("PUSH").append(" ").append(arg1).append(" ").append(arg2).append(" ").append("GCM");
		} else {
			// 운영 (리눅스)
			sb = new StringBuffer();
			sb.append("java -jar").append(" ");
			sb.append("/srv/awsSns/awsSns.jar").append(" ").append("PUSH").append(" ").append(arg1).append(" ").append(arg2).append(" ").append("GCM");
		}
		String sCommand = sb.toString();

		try {
			Process p = Runtime.getRuntime().exec(sCommand);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;

			while ( (line = br.readLine()) != null ) {
				System.out.println(line);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
