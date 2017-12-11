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
	public static final String NETFLIX_CIRCUIT_EXCEPTION = "1005";

	// 发送短信失败
	public static final String SENDMESSAGE_FAILED_EXCEPTION = "1005";
	// 短信发送间隔时间太短
	public static final String SENDMESSAGE_INTERVAL_TOOSHORT_EXCEPTION = "1006";
	// 验证码错误或者验证码已过期
	public static final String VERIFICATIONCODE_INVALID_EXCEPTION = "1007";

	// 发布人服务异常
	// 该手机号已被注册
	public static final String PHONE_BEEN_REGISTERED_EXCEPTION = "2001";
	// 错误的手机号码
	public static final String PHONE_WRONG_EXCEPTION = "2002";
	// 用户名或者密码错误
	public static final String USERNAME_OR_PASSWORD_ERROR_EXCEPTION = "2003";
	// 该手机号尚未注册
	public static final String PHONE_ISNOT_REGISTERED_EXCEPTION = "2004";
	// 该银行卡已绑定，不能重复绑定
	public static final String BANKCARD_ALREADY_BIND_EXCEPTION = "2005";
	// 该股票已收藏，不能重复收藏
	public static final String STOCK_ALREADY_FAVORITE_EXCEPTION = "2006";

	/* 业务异常 */
	// 系统管理业务异常
	// 菜单服务异常
	public static final String MENU_SERVICE_EXCEPTION = "3001";
	// 权限服务异常
	public static final String SECURITY_METHOD_UNSUPPORT_EXCEPTION = "3002";
	// 员工服务异常
	public static final String STAFF_NOT_FOUND_EXCEPTION = "3003";
	// 角色服务异常
	public static final String ROLE_NOT_FOUND_EXCEPTION = "3004";
	public static final String PERMISSION_NOT_FOUND_EXCEPTION = "3005";

	// 点买服务异常
	// 账户可用余额不足
	public static final String AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION = "6001";
	// 买入或者卖出前需进行锁定操作
	public static final String BUYRECORD_ISNOTLOCK_EXCEPTION = "6002";
	// 投资人必须和买入锁定时的投资人一致
	public static final String BUYRECORD_INVESTORID_NOTMATCH_EXCEPTION = "6003";
	// 点买记录状态不匹配，不支持该操作
	public static final String BUYRECORD_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION = "6004";
	// 申请卖出发布人不匹配，不支持该操作
	public static final String BUYRECORD_PUBLISHERID_NOTMATCH_EXCEPTION = "6005";
	// 点买记录不存在
	public static final String BUYRECORD_NOT_FOUND_EXCEPTION = "6006";
	// 点买发布扣款发生异常，如账户已扣款，请联系客服人员
	public static final String BUYRECORD_POST_DEBITFAILED_EXCEPTION = "6007";
	// 支付密码未设置
	public static final String PAYMENTPASSWORD_NOTSET_EXCEPTION = "6008";
	// 支付密码错误
	public static final String PAYMENTPASSWORD_WRONG_EXCEPTION = "6009";
	// 点买异常
	public static final String BUYRECORD_SAVE_EXCEPTION = "6010";
	// 申请卖出只能由发布人本人操作
	public static final String BUYRECORD_SELLAPPLY_NOTMATCH_EXCEPTION = "6011";

	// 投资人服务异常
	public static final String INVESTOR_NOT_FOUND_EXCEPTION = "7001";
	public static final String INVESTOR_SECURITIES_LOGIN_EXCEPTION = "7002";
	public static final String INVESTOR_STOCKACCOUNT_MONEY_NOT_ENOUGH = "7003";
	public static final String INVESTOR_EXCHANGE_TYPE_NOT_SUPPORT_EXCEPTION = "7004";
	public static final String INVESTOR_STOCKACCOUNT_NOT_EXIST = "7005";
	public static final String INVESTOR_STOCKENTRUST_BUY_ERROR = "7006";
	public static final String INVESTOR_STOCKENTRUST_FETCH_ERROR = "7007";
}
