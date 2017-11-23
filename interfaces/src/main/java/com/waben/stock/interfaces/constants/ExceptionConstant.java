package com.waben.stock.interfaces.constants;

/**
 * @author Created by yuyidi on 2017/9/24.
 * @desc
 */
public class ExceptionConstant {

	// 系统内部异常
	public static final String UNKNOW_EXCEPTION = "1001";
	public static final String DATETIME_ERROR = "1002";
	public static final String DATANOTFOUND_EXCEPTION = "1003";
	public static final String ARGUMENT_EXCEPTION = "1004";

	/* 业务异常 */
	// 系统管理业务异常
	// 菜单服务异常
	public static final String MENU_SERVICE_EXCEPTION = "3001";
	// 权限服务异常
	public static final String SECURITY_METHOD_UNSUPPORT_EXCEPTION = "4001";
	// 员工服务异常
	public static final String STAFF_NOT_FOUND_EXCEPTION = "5001";

	/* 业务异常--个人中心 */
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
	// 该银行卡已绑定，不能重复绑定
	public static final String BANKCARD_ALREADY_BIND_EXCEPTION = "2008";
	// 账户可用余额不足
	public static final String AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION = "2009";

	/* 业务异常--自选股票 */
	// 该股票已收藏，不能重复收藏
	public static final String STOCK_ALREADY_FAVORITE_EXCEPTION = "3001";

}
