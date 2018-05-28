package com.waben.stock.interfaces.commonapi.retrivefutures;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesGatewayOrder;
import com.waben.stock.interfaces.enums.FuturesActionType;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.RequestParamBuilder;

public class TradeFuturesOverHttp {

	private static RestTemplate restTemplate = new RestTemplate();

	private static String baseUrl = "http://10.0.0.48:9092/";

	public static FuturesGatewayOrder retriveByGatewayId(Long gatewayOrderId) {
		String url = baseUrl + "futuresOrder/" + gatewayOrderId;
		String response = restTemplate.getForObject(url, String.class);
		Response<FuturesGatewayOrder> responseObj = JacksonUtil.decode(response,
				JacksonUtil.getGenericType(Response.class, FuturesGatewayOrder.class));
		if ("200".equals(responseObj.getCode())) {
			return responseObj.getResult();
		} else {
			throw new RuntimeException("根据网关订单ID获取订单异常!" + responseObj.getCode());
		}
	}

	public static FuturesGatewayOrder placeOrder(String symbol, Integer outerOrderId, FuturesActionType action,
			BigDecimal totalQuantity, Integer userOrderType, BigDecimal entrustPrice) {
		String url = baseUrl + "futuresOrder/";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("symbol", symbol);
		paramMap.put("outerOrderId", outerOrderId);
		paramMap.put("action", action.name());
		paramMap.put("totalQuantity", totalQuantity);
		paramMap.put("userOrderType", userOrderType);
		paramMap.put("entrustPrice", entrustPrice);
		String queryString = RequestParamBuilder.build(paramMap);
		HttpEntity<String> requestEntity = new HttpEntity<String>(queryString);
		String response = restTemplate.postForObject(url, requestEntity, String.class);
		Response<FuturesGatewayOrder> responseObj = JacksonUtil.decode(response,
				JacksonUtil.getGenericType(Response.class, FuturesGatewayOrder.class));
		if ("200".equals(responseObj.getCode())) {
			return responseObj.getResult();
		} else {
			throw new RuntimeException("根据网关订单ID获取订单异常!" + responseObj.getCode());
		}
	}

}
