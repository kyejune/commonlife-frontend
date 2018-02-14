package com.kolonbenit.benitware.common.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static final String PROP_CLASS_PATH = "properties/";

	/**
	 * Classpath의 Properties 파일 로드
	 * @param propFileName
	 * @return
	 */
	public static Properties getPropertiesFromClasspath(String propFileName) {
		Properties prop = new Properties();
		InputStream is = null;

		try {
			propFileName = PROP_CLASS_PATH + propFileName;
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(propFileName);
			prop.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}

}
