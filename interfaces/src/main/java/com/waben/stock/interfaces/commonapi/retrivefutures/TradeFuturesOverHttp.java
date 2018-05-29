package com.waben.stock.interfaces.commonapi.retrivefutures;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

	/**
	 * 下单
	 * 
	 * @param domain
	 *            所属应用域
	 * @param symbol
	 *            合约标识
	 * @param outerOrderId
	 *            订单ID
	 * @param action
	 *            期货交易动作
	 * @param totalQuantity
	 *            交易量
	 * @param userOrderType
	 *            用户订单类型
	 *            <ul>
	 *            <li>1市价订单</li>
	 *            <li>2委托价订单</li>
	 * @param entrustPrice
	 *            委托价格
	 * @return 期货网关订单
	 */
	public static FuturesGatewayOrder placeOrder(String domain, String symbol, Long outerOrderId,
			FuturesActionType action, BigDecimal totalQuantity, Integer userOrderType, BigDecimal entrustPrice) {
		String url = baseUrl + "futuresOrder/";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("domain", domain);
		paramMap.put("symbol", symbol);
		paramMap.put("outerOrderId", outerOrderId);
		paramMap.put("action", action.name());
		paramMap.put("totalQuantity", totalQuantity);
		paramMap.put("userOrderType", userOrderType);
		if (entrustPrice != null) {
			paramMap.put("entrustPrice", entrustPrice);
		}
		String queryString = RequestParamBuilder.build(paramMap);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		HttpEntity<String> requestEntity = new HttpEntity<String>(queryString, requestHeaders);
		String response = restTemplate.postForObject(url, requestEntity, String.class);
		Response<FuturesGatewayOrder> responseObj = JacksonUtil.decode(response,
				JacksonUtil.getGenericType(Response.class, FuturesGatewayOrder.class));
		if ("200".equals(responseObj.getCode())) {
			return responseObj.getResult();
		} else {
			throw new RuntimeException("根据网关订单ID获取订单异常!" + responseObj.getCode());
		}
	}

	public static void testMain(String[] args) {
		placeOrder("test.com", "GC", 1L, FuturesActionType.BUY, new BigDecimal(4), 1, new BigDecimal(1.0));
	}

}
