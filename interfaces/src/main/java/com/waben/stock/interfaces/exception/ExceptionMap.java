package com.waben.stock.interfaces.exception;

import java.util.HashMap;
import java.util.Map;

import com.waben.stock.interfaces.constants.ExceptionConstant;

public class ExceptionMap {

	public static Map<String, String> exceptionMap = new HashMap<String, String>();

	static {
		exceptionMap.put(ExceptionConstant.UNKNOW_EXCEPTION, "服务器未知异常");
		exceptionMap.put(ExceptionConstant.DATANOTFOUND_EXCEPTION, "数据没有找到");
		exceptionMap.put(ExceptionConstant.ARGUMENT_EXCEPTION, "参数异常");
		exceptionMap.put(ExceptionConstant.SECURITY_METHOD_UNSUPPORT_EXCEPTION, "安全验证方法不支持异常");

		exceptionMap.put(ExceptionConstant.MENU_SERVICE_EXCEPTION, "菜单服务异常");
		exceptionMap.put(ExceptionConstant.STAFF_NOT_FOUND_EXCEPTION, "员工信息未找到");

		exceptionMap.put(ExceptionConstant.SENDMESSAGE_FAILED_EXCEPTION, "发送短信失败");
		exceptionMap.put(ExceptionConstant.SENDMESSAGE_INTERVAL_TOOSHORT_EXCEPTION, "短信发送间隔时间太短");
		exceptionMap.put(ExceptionConstant.VERIFICATIONCODE_INVALID_EXCEPTION, "验证码错误或者验证码已过期");
		exceptionMap.put(ExceptionConstant.PHONE_BEEN_REGISTERED_EXCEPTION, "该手机号已被注册");
		exceptionMap.put(ExceptionConstant.PHONE_WRONG_EXCEPTION, "错误的手机号码");
		exceptionMap.put(ExceptionConstant.USERNAME_OR_PASSWORD_ERROR_EXCEPTION, "用户名或者密码错误");
		exceptionMap.put(ExceptionConstant.PHONE_ISNOT_REGISTERED_EXCEPTION, "该手机号尚未注册");
		exceptionMap.put(ExceptionConstant.BANKCARD_ALREADY_BIND_EXCEPTION, "该银行卡已绑定，不能重复绑定");
		exceptionMap.put(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION, "账户可用余额不足");

		exceptionMap.put(ExceptionConstant.STOCK_ALREADY_FAVORITE_EXCEPTION, "该股票已收藏，不能重复收藏");
	}
}
