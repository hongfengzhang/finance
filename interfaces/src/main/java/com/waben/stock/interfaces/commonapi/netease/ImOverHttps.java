package com.waben.stock.interfaces.commonapi.netease;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.interfaces.commonapi.netease.config.NeteaseLiveConfig;
import com.waben.stock.interfaces.commonapi.netease.livebean.NeteaseLiveResponse;
import com.waben.stock.interfaces.commonapi.netease.util.CheckSumBuilder;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.RandomUtil;

/**
 * Im即时通讯统一请求网易云
 * 
 * @author luomengan
 *
 */
public class ImOverHttps {

	public static RestTemplate restTemplate = new RestTemplate();

	private static Logger logger = LoggerFactory.getLogger(ChannelManageOverHttps.class);

	/**
	 * 统一请求网易云IM
	 */
	public static <T> NeteaseLiveResponse<T> doAction(String requestUrl, Object param, Class<T> clazz) {
		// 设置请求头
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("AppKey", NeteaseLiveConfig.AppKey);
		String nonce = RandomUtil.generateNonceStr();
		requestHeaders.set("Nonce", nonce);
		String curTime = String.valueOf(System.currentTimeMillis() / 1000L);
		requestHeaders.set("CurTime", curTime);
		requestHeaders.set("CheckSum", CheckSumBuilder.getCheckSum(NeteaseLiveConfig.AppSecret, nonce, curTime));
		requestHeaders.set("Content-Type", "application/json;charset=utf-8");
		// 请求对象
		HttpEntity<String> requestEntity = new HttpEntity<String>(JacksonUtil.encode(param), requestHeaders);
		// 发送请求
		String resultJson = restTemplate.postForObject(requestUrl, requestEntity, String.class);
		logger.info("请求网易云信响应:" + resultJson);
		// 响应对象
		NeteaseLiveResponse<T> responseObj = JacksonUtil.decode(resultJson,
				JacksonUtil.getGenericType(NeteaseLiveResponse.class, clazz));
		return responseObj;
	}

	public static String objectToQueryString(Object obj) {
		StringBuilder strBuilder = new StringBuilder();
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
				try {
					Method method = clazz.getMethod(methodName);
					// method.invoke(obj);
				} catch (NoSuchMethodException | SecurityException e) {
				}
			}
		}
		return strBuilder.toString();
	}

}
