package com.waben.stock.applayer.strategist.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.enums.SmsType;
import com.waben.stock.interfaces.exception.ServiceException;

/**
 * 发送成功的短信 缓存
 * 
 * @author luomengan
 *
 */
public class SmsCache {

	/**
	 * 短信有效期，15分钟
	 */
	public static final long effectiveDuration = 15 * 60 * 1000;
	/**
	 * 短信发送最短间隔，1分钟，即1分钟内不能重复发送
	 */
	public static final long sendingInterval = 1 * 60 * 1000;
	/**
	 * 短信验证码缓存，key为smsType_phone，value为验证码
	 */
	public static final Map<String, VerificationCodeInfo> verificationCodeCache = Collections
			.synchronizedMap(new HashMap<String, VerificationCodeInfo>());

	/**
	 * 获取短信验证码缓存key
	 */
	public static String getCacheKey(SmsType smsType, String phone) {
		return smsType.name() + "_" + phone;
	}

	/**
	 * 缓存验证码信息
	 */
	public static void cache(SmsType smsType, String phone, String verificationCode) {
		verificationCodeCache.put(getCacheKey(smsType, phone), new VerificationCodeInfo(verificationCode));
	}

	/**
	 * 检查发送条件
	 */
	public static void checkSendCondition(SmsType smsType, String phone) {
		VerificationCodeInfo oldCode = verificationCodeCache.get(getCacheKey(smsType, phone));
		if (oldCode != null && oldCode.getLatelyIntervalTime().getTime() > new Date().getTime()) {
			throw new ServiceException(ExceptionConstant.SENDMESSAGE_INTERVAL_TOOSHORT_EXCEPTION);
		}
	}

	/**
	 * 匹配验证码
	 */
	public static void matchVerificationCode(SmsType smsType, String phone, String verificationCode) {
		VerificationCodeInfo oldCode = verificationCodeCache.get(getCacheKey(smsType, phone));
		if (!(oldCode != null && oldCode.getVerificationCode().equals(verificationCode)
				&& oldCode.getEffectiveTime().getTime() > new Date().getTime())) {
			throw new ServiceException(ExceptionConstant.VERIFICATIONCODE_INVALID_EXCEPTION);
		}
	}

	/**
	 * 短信验证码信息
	 * 
	 * @author luomengan
	 *
	 */
	public static class VerificationCodeInfo {

		private String verificationCode;

		private Date sendTime;

		private Date effectiveTime;

		private Date latelyIntervalTime;

		public VerificationCodeInfo(String verificationCode) {
			this.verificationCode = verificationCode;
			this.sendTime = new Date();
			this.effectiveTime = new Date(this.sendTime.getTime() + effectiveDuration);
			this.latelyIntervalTime = new Date(this.sendTime.getTime() + sendingInterval);
		}

		public String getVerificationCode() {
			return verificationCode;
		}

		public void setVerificationCode(String verificationCode) {
			this.verificationCode = verificationCode;
		}

		public Date getSendTime() {
			return sendTime;
		}

		public void setSendTime(Date sendTime) {
			this.sendTime = sendTime;
		}

		public Date getEffectiveTime() {
			return effectiveTime;
		}

		public void setEffectiveTime(Date effectiveTime) {
			this.effectiveTime = effectiveTime;
		}

		public Date getLatelyIntervalTime() {
			return latelyIntervalTime;
		}

		public void setLatelyIntervalTime(Date latelyIntervalTime) {
			this.latelyIntervalTime = latelyIntervalTime;
		}

	}

}
