package com.waben.stock.applayer.tactics.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.ServiceException;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送
 * 
 * @author luomengan
 *
 */
@Service
public class JiguangService {

	private static final String APP_KEY = "973418bb64e24cbfc54af5bb";

	private static final String MASTER_SECRET = "2109cbbb2e856dcf7f112384";

	Logger logger = LoggerFactory.getLogger(getClass());

	private PushPayload buildPushObjectForSingle(String registrationId, String alert, Map<String, String> extras) {
		Builder builder = PushPayload.newBuilder();
		builder.setPlatform(Platform.all());
		builder.setAudience(
				Audience.newBuilder().addAudienceTarget(AudienceTarget.registrationId(registrationId)).build());
		builder.setNotification(Notification.android(alert, "优股网", extras));
		builder.setNotification(Notification.ios(alert, extras));
		return builder.build();
	}

	private PushPayload buildPushObjectForMultiple(String[] registrationIdArr, String alert,
			Map<String, String> extras) {
		Builder builder = PushPayload.newBuilder();
		builder.setPlatform(Platform.all());
		builder.setAudience(
				Audience.newBuilder().addAudienceTarget(AudienceTarget.registrationId(registrationIdArr)).build());
		builder.setNotification(Notification.newBuilder()
				.addPlatformNotification(IosNotification.newBuilder().setAlert(alert).addExtras(extras).build())
				.addPlatformNotification(
						AndroidNotification.newBuilder().setTitle("优股网").setAlert(alert).addExtras(extras).build())
				.build());
		builder.setOptions(Options.newBuilder().setApnsProduction(true).build());
		return builder.build();
	}

	private PushPayload buildPushObjectForAllDevice(String alert, Map<String, String> extras) {
		Builder builder = PushPayload.newBuilder();
		builder.setPlatform(Platform.all());
		builder.setAudience(Audience.newBuilder().setAll(true).build());
		builder.setNotification(Notification.ios(alert, extras));
		builder.setNotification(Notification.android(alert, "优股网", extras));
		// ture为生产环境，false为开发环境
		builder.setOptions(Options.newBuilder().setApnsProduction(true).build());
		return builder.build();
	}

	/**
	 * 推送通知
	 */
	private void pushNotification(PushPayload payload) {
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
		try {
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);
		} catch (APIConnectionException e) {
			logger.error("Connection error, should retry later", e);
			throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION,
					"Jiguang push connection error, should retry later");
		} catch (APIRequestException e) {
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
			throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION,
					"Jiguang push should review the error, and fix the request");
		}
	}

	public void pushAllDevice(String alert, Map<String, String> extras) {
		pushNotification(buildPushObjectForAllDevice(alert, extras));
	}

	public void pushSingleDevice(String registrationId, String alert, Map<String, String> extras) {
		pushNotification(buildPushObjectForSingle(registrationId, alert, extras));
	}

	public void pushMultipleDevice(String[] registrationIdArr, String alert, Map<String, String> extras) {
		pushNotification(buildPushObjectForMultiple(registrationIdArr, alert, extras));
	}

}
