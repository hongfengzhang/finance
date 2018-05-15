package com.waben.stock.interfaces.util;

import java.util.Calendar;

public class IdCardUtil {

	/** 中国公民身份证号码最小长度。 */
	public final int CHINA_ID_MIN_LENGTH = 15;

	/** 中国公民身份证号码最大长度。 */
	public final int CHINA_ID_MAX_LENGTH = 18;

	/**
	 * 根据身份编号获取年龄
	 *
	 * @param idCard
	 *            身份编号
	 * @return 年龄
	 */
	public static int getAgeByIdCard(String idCard) {
		int iAge = 0;
		Calendar cal = Calendar.getInstance();
		String year = idCard.substring(6, 10);
		int iCurrYear = cal.get(Calendar.YEAR);
		iAge = iCurrYear - Integer.valueOf(year);
		return iAge;
	}
	
	public static void testMain(String[] args) {
		System.out.println(getAgeByIdCard("360730199112094312"));
	}

}
