package com.waben.stock.interfaces.commonapi.maike;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.interfaces.commonapi.maike.bean.QuoteParam;
import com.waben.stock.interfaces.commonapi.maike.util.RSAUtils;
import com.waben.stock.interfaces.commonapi.netease.ChannelManageOverHttps;
import com.waben.stock.interfaces.util.JacksonUtil;

public class QuoteOverHttp {

	public static RestTemplate restTemplate = new RestTemplate();

	private static Logger logger = LoggerFactory.getLogger(ChannelManageOverHttps.class);

	private static final String PublicKey = "0Ptb4BGXh1hbV6L7jYLF8ifaBfs11bgBoN0eu6Mr56oRHkV+chSLtaoEQ8mJfNcV2EEQvNCZAzaiL8xNt46M1X9zJtfHBCIQroe7pNYvwfwiZF7gz+94a9rpM/K2ExYAE8ob0mPak+u2Y3s1/o4gvxYmXROmyBnBoglerTogROU=";

	private static final String Token = "0Ptb4BGXh1hbV6L7jYLF8ifaBfs11bgBoN0eu6Mr56oRHkV+chSLtaoEQ8mJfNcV2EEQvNCZAzaiL8xNt46M1X9zJtfHBCIQroe7pNYvwfwiZF7gz+94a9rpM/K2ExYAE8ob0mPak+u2Y3s1/o4gvxYmXROmyBnBoglerTogROU=";

	private static final String TokenPrefix = "Bearer";

	private static final String UKey = "一飞冲天";

	public static void doAction(Object param) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String requestUrl = "http://120.79.188.0/H-Api/HanApi.ashx";
		// 设置请求头
		HttpHeaders requestHeaders = new HttpHeaders();
		Map<String, String> keyMap = RSAUtils.createKeys(1024);
		String publicKey = keyMap.get("publicKey");
		String token = RSAUtils.publicEncrypt(TokenPrefix + Token, RSAUtils.getPublicKey(publicKey));
		requestHeaders.set("Token", token);
		requestHeaders.set("Content-Type", "application/json;charset=UTF-8");
		// 请求对象
		String paramStr = JacksonUtil.encode(param);
		logger.info("请求迈科询价接口数据:" + paramStr);
		HttpEntity<String> requestEntity = new HttpEntity<String>(paramStr, requestHeaders);
		// 发送请求
		String resultJson = restTemplate.postForObject(requestUrl, requestEntity, String.class);
		logger.info("请求迈科询价接口响应:" + resultJson);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {

		QuoteParam param = new QuoteParam(String.valueOf(System.currentTimeMillis()), UKey, 0, Token);
		doAction(param);
	}

}
