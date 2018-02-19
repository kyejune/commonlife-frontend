package com.kolonbenit.benitware.common.util;

import java.text.DecimalFormat;

public class FormattingUtil {

	/**
	 * 핸드폰 번호 형식 체크 및 하이픈 포함하여 반환
	 * @param strOrgCell
	 * @return
	 */
	public static String cellPhoneNumHyphen(String strOrgCell) {
		String strCell = "";
		String regExp = "^(01[016789])-?(\\d{3,4})-?(\\d{4})$";

		if ( strOrgCell.matches(regExp) ) {
			strCell = strOrgCell.replaceAll(regExp, "$1-$2-$3");
		}
		return strCell;
	}

	/**
	 * 핸드폰 번호 형식 체크 및 하이픈 없이 반환
	 * @param strOrgCell
	 * @return
	 */
	public static String cellPhoneNumUnHyphen(String strOrgCell) {
		String strCell = "";
		String regExp = "^(01[016789])-?(\\d{3,4})-?(\\d{4})$";

		if ( strOrgCell.matches(regExp) ) {
			strCell = strOrgCell.replaceAll(regExp, "$1$2$3");
		}
		return strCell;
	}

	/**
	 * 숫자 금액 형식 반환(세자리 콤마)
	 * @param num
	 * @return
	 */
	public static String convertMoneyFormat(int num) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}

}
