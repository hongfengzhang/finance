package com.waben.stock.interfaces.constants;

/**
 * @author Created by yuyidi on 2017/9/24.
 * @desc
 */
public class ExceptionConstant {

	// 系统内部异常
	public static final String NETFLIX_CIRCUIT_EXCEPTION = "1000";
	public static final String UNKNOW_EXCEPTION = "1001";
	public static final String DATETIME_ERROR = "1002";
	public static final String DATANOTFOUND_EXCEPTION = "1003";
	public static final String ARGUMENT_EXCEPTION = "1004";

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
	// 原始密码不匹配
	public static final String ORIGINAL_PASSWORD_MISMATCH_EXCEPTION = "2007";
	// 手机号不匹配
	public static final String PHONE_MISMATCH_EXCEPTION = "2008";
	// 已经设置过支付密码，修改支付密码需要验证码
	public static final String MODIFY_PAYMENTPASSWORD_NEEDVALIDCODE_EXCEPTION = "2009";
	// 不能绑定信用卡
	public static final String CREDITCARD_NOTSUPPORT_EXCEPTION = "2010";
	// 未找到该卡号对应的银行，请检查输入的信息是否正确
	public static final String BANKCARD_NOTRECOGNITION_EXCEPTION = "2011";
	// 不支持的银行卡号
	public static final String BANKCARD_NOTSUPPORT_EXCEPTION = "2012";
	// 用户不匹配
	public static final String PUBLISHERID_NOTMATCH_EXCEPTION = "2013";
	// 银行卡信息有误，请检查输入的信息是否正确
	public static final String BANKCARDINFO_NOTMATCH_EXCEPTION = "2014";

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

	// 点买服务、股票异常
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
	// 退回保证金发生异常
	public static final String BUYRECORD_RETURNRESERVEFUND_EXCEPTION = "6012";
	// 非交易时间段
	public static final String BUYRECORD_NONTRADINGPERIOD_EXCEPTION = "6013";
	// 该股票已停牌
	public static final String STOCK_SUSPENSION_EXCEPTION = "6014";
	// 提现异常
	public static final String WITHDRAWALS_EXCEPTION = "6015";
	// 充值异常
	public static final String RECHARGE_EXCEPTION = "6016";
	// 用户选择不递延，不能进行递延操作
	public static final String BUYRECORD_USERNOTDEFERRED_EXCEPTION = "6017";
	// 该点买记录已经递延过了，不能重复递延
	public static final String BUYRECORD_ALREADY_DEFERRED_EXCEPTION = "6018";
	// 当前用户为测试用户，不能进行提现操作
	public static final String TESTUSER_NOWITHDRAWALS_EXCEPTION = "6019";
	// 当前用户不能参与该策略，或该策略为一次性参与活动且当前用户已经参与
	public static final String STRATEGYQUALIFY_NOTENOUGH_EXCEPTION = "6020";
	// 申请市值不足购买一手
	public static final String APPLYAMOUNT_NOTENOUGH_BUYSTOCK_EXCEPTION = "6021";
	// 持仓第二天之后才能申请卖出
	public static final String USERSELLAPPLY_NOTMATCH_EXCEPTION = "6022";
	// 买入中和买入锁定状态下才能撤单
	public static final String BUYRECORD_REVOKE_NOTSUPPORT_EXCEPTION = "6023";
	// 不支持购买创业板的股票
	public static final String DEVELOPSTOCK_NOTSUPPORT_EXCEPTION = "6024";
	// 银联支付单笔最大额度为5000
	public static final String UNIONPAY_SINGLELIMIT_EXCEPTION = "6025";
	// 该股票已涨停，不能购买
	public static final String STOCK_ARRIVEUPLIMIT_EXCEPTION = "6026";
	// 该股票已跌停，不能购买
	public static final String STOCK_ARRIVEDOWNLIMIT_EXCEPTION = "6027";
	// ST、*ST不能购买
	public static final String ST_STOCK_CANNOTBUY_EXCEPTION = "6028";

	// 投资人服务异常
	public static final String INVESTOR_NOT_FOUND_EXCEPTION = "7001";
	public static final String INVESTOR_SECURITIES_LOGIN_EXCEPTION = "7002";
	public static final String INVESTOR_STOCKACCOUNT_MONEY_NOT_ENOUGH = "7003";
	public static final String INVESTOR_EXCHANGE_TYPE_NOT_SUPPORT_EXCEPTION = "7004";
	public static final String INVESTOR_STOCKACCOUNT_NOT_EXIST = "7005";
	public static final String INVESTOR_STOCKENTRUST_BUY_ERROR = "7006";
	public static final String INVESTOR_STOCKENTRUST_FETCH_ERROR = "7007";

	// 经纪人服务异常
	// 机构不存在
	public static final String ORGANIZATION_NOTEXIST_EXCEPTION = "8001";
	// 机构类别不存在
	public static final String ORGANIZATIONCATEGORY_NOTEXIST_EXCEPTION = "8002";

}
