package com.waben.stock.interfaces.commonapi.netease;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.interfaces.commonapi.netease.bean.NeteaseCreateParam;
import com.waben.stock.interfaces.commonapi.netease.bean.NeteaseCreateRet;
import com.waben.stock.interfaces.commonapi.netease.bean.NeteaseResponse;
import com.waben.stock.interfaces.commonapi.netease.config.NeteaseConfig;
import com.waben.stock.interfaces.commonapi.netease.util.CheckSumBuilder;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.RandomUtil;

public class ChannelManageOverHttps {

	public static RestTemplate restTemplate = new RestTemplate();

	/**
	 * 创建频道
	 */
	public static <T> NeteaseResponse<T> doAction(String requestUrl, Object param, Class<T> clazz) {
		// 设置请求头
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("AppKey", NeteaseConfig.AppKey);
		String nonce = RandomUtil.generateNonceStr();
		requestHeaders.set("Nonce", nonce);
		String curTime = String.valueOf(System.currentTimeMillis() / 1000L);
		requestHeaders.set("CurTime", curTime);
		requestHeaders.set("CheckSum", CheckSumBuilder.getCheckSum(NeteaseConfig.AppSecret, nonce, curTime));
		requestHeaders.set("Content-Type", "application/json;charset=utf-8");
		// 请求对象
		HttpEntity<String> requestEntity = new HttpEntity<String>(JacksonUtil.encode(param), requestHeaders);
		// 发送请求
		String resultJson = restTemplate.postForObject(requestUrl, requestEntity, String.class);
		// 响应对象
		NeteaseResponse<T> responseObj = JacksonUtil.decode(resultJson,
				JacksonUtil.getGenericType(NeteaseResponse.class, clazz));
		return responseObj;
	}

	public static void testMain(String[] args) {
		// 创建频道
		NeteaseCreateParam createParam = new NeteaseCreateParam("测试频道");
		NeteaseResponse<NeteaseCreateRet> createResponse = doAction(NeteaseConfig.CreateUrl, createParam,
				NeteaseCreateRet.class);
		System.out.println("创建频道响应结果:" + JacksonUtil.encode(createResponse));
	}

}
