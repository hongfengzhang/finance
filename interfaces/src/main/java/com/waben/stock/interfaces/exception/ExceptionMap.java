package com.waben.stock.interfaces.exception;

import com.waben.stock.interfaces.constants.ExceptionConstant;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMap {

	public static Map<String, String> exceptionMap = new HashMap<String, String>();

	static {
		exceptionMap.put(ExceptionConstant.UNKNOW_EXCEPTION, "服务器未知异常");

		exceptionMap.put(ExceptionConstant.SENDMESSAGE_FAILED_EXCEPTION, "发送短信失败");
		exceptionMap.put(ExceptionConstant.SENDMESSAGE_INTERVAL_TOOSHORT_EXCEPTION, "短信发送间隔时间太短");
		exceptionMap.put(ExceptionConstant.VERIFICATIONCODE_INVALID_EXCEPTION, "验证码错误或者验证码已过期");
		exceptionMap.put(ExceptionConstant.PHONE_BEEN_REGISTERED_EXCEPTION, "该手机号已被注册");
		exceptionMap.put(ExceptionConstant.PHONE_WRONG_EXCEPTION, "错误的手机号码");
		exceptionMap.put(ExceptionConstant.USERNAME_OR_PASSWORD_ERROR_EXCEPTION, "用户名或者密码错误");
		exceptionMap.put(ExceptionConstant.PHONE_ISNOT_REGISTERED_EXCEPTION, "该手机号尚未注册");
		exceptionMap.put(ExceptionConstant.STOCK_ALREADY_FAVORITE_EXCEPTION, "该股票已收藏，不能重复收藏");
	}
}
