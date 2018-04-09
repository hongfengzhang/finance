package com.waben.stock.interfaces.commonapi.netease;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.interfaces.commonapi.netease.bean.NeteaseAddressParam;
import com.waben.stock.interfaces.commonapi.netease.bean.NeteaseAddressRet;
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
	 * 统一请求网易云直播
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
		System.out.println(resultJson);
		// 响应对象
		NeteaseResponse<T> responseObj = JacksonUtil.decode(resultJson,
				JacksonUtil.getGenericType(NeteaseResponse.class, clazz));
		return responseObj;
	}

	public static void main(String[] args) {
		 // 创建频道
		 NeteaseCreateParam createParam = new NeteaseCreateParam("测试频道");
		 NeteaseResponse<NeteaseCreateRet> createResponse =
		 doAction(NeteaseConfig.CreateUrl, createParam,
		 NeteaseCreateRet.class);
		 System.out.println("创建频道响应结果:" + JacksonUtil.encode(createResponse));
		
		// 获取频道列表
		// NeteaseChannellistParam listParam = new NeteaseChannellistParam();
		// NeteaseResponse<NeteaseChannellistRet> listResponse =
		// doAction(NeteaseConfig.ChannellistUrl, listParam,
		// NeteaseChannellistRet.class);
		// System.out.println("获取频道列表响应结果:" + JacksonUtil.encode(listResponse));
		
		// 重新获取推流地址
//		NeteaseAddressParam addressParam = new NeteaseAddressParam();
//		addressParam.setCid("0b55c14db41d4eda898f87fe4f7a47d8");
//		NeteaseResponse<NeteaseAddressRet> addressResponse = doAction(NeteaseConfig.AddressUrl, addressParam,
//				NeteaseAddressRet.class);
//		System.out.println("获取频道列表响应结果:" + JacksonUtil.encode(addressResponse));
	}

}
