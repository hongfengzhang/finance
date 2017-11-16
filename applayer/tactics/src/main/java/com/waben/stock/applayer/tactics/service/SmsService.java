package com.waben.stock.applayer.tactics.service;

import java.util.List;

import com.waben.stock.interfaces.enums.SmsType;

public interface SmsService {

	/**
	 * 发送短信
	 * 
	 * @param smsType
	 *            短信类型
	 * @param phone
	 *            手机号码
	 * @param paramValues
	 *            短信参数值，如短信中的动态验证码值
	 */
	public void sendMessage(SmsType smsType, String phone, List<String> paramValues);

}
