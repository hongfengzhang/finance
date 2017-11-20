package com.waben.stock.interfaces.constants;

/**
 * @author Created by yuyidi on 2017/9/24.
 * @desc
 */
public class ExceptionConstant {

	public static final String UNKNOW_EXCEPTION = "1001";
	public static final String DATETIME_ERROR = "1002";

	// 发送短信失败
	public static final String SENDMESSAGE_FAILED_EXCEPTION = "2001";
	// 短信发送间隔时间太短
	public static final String SENDMESSAGE_INTERVAL_TOOSHORT_EXCEPTION = "2002";
	// 验证码错误或者验证码已过期
	public static final String VERIFICATIONCODE_INVALID_EXCEPTION = "2003";
	// 该手机号已被注册
	public static final String PHONE_BEEN_REGISTERED_EXCEPTION = "2004";
	// 错误的手机号码
	public static final String PHONE_WRONG_EXCEPTION = "2005";
	// 用户名或者密码错误
	public static final String USERNAME_OR_PASSWORD_ERROR_EXCEPTION = "2006";
	// 该手机号尚未注册
	public static final String PHONE_ISNOT_REGISTERED_EXCEPTION = "2007";
	// 该股票已收藏，不能重复收藏
	public static final String STOCK_ALREADY_FAVORITE_EXCEPTION = "2008";

}
