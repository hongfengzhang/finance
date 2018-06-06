package com.waben.stock.interfaces.commonapi.retrivefutures;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractLineData;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.StringUtil;

public class RetriveFuturesOverHttp {

	private static RestTemplate restTemplate = new RestTemplate();

	private static String baseUrl = "http://10.0.0.48:9092/";

	public static FuturesContractMarket market(String symbol) {
		String url = baseUrl + "market/" + symbol;
		String response = restTemplate.getForObject(url, String.class);
		Response<FuturesContractMarket> responseObj = JacksonUtil.decode(response,
				JacksonUtil.getGenericType(Response.class, FuturesContractMarket.class));
		if(responseObj!=null){
			if ("200".equals(responseObj.getCode())) {
				FuturesContractMarket market = responseObj.getResult();
				// TODO 因盈透测试账户没有返回最新价，此处先写死一个最新价返回给前端调试，后续删除
				if(market==null){
					market = new FuturesContractMarket();
				}
				market.setLastPrice(
						new BigDecimal(1300).add(new BigDecimal(Math.random() * 2)).setScale(4, RoundingMode.DOWN));
				responseObj.setResult(market);
				return responseObj.getResult();
			} else {
				throw new RuntimeException("http获取期货行情异常!" + responseObj.getCode());
			}
		}else{
			FuturesContractMarket market = new FuturesContractMarket();
			// TODO 因盈透测试账户没有返回最新价，此处先写死一个最新价返回给前端调试，后续删除
			if(market==null){
				market = new FuturesContractMarket();
			}
			market.setLastPrice(
					new BigDecimal(1300).add(new BigDecimal(Math.random() * 2)).setScale(4, RoundingMode.DOWN));
			return market;
		}
	}

	public static List<FuturesContractLineData> timeLine(String symbol, Integer dayCount) {
		String url = baseUrl + "market/" + symbol + "/timeline?flag=1";
		if (dayCount != null) {
			url += "&dayCount=" + dayCount;
		}
		String response = restTemplate.getForObject(url, String.class);
		Response<List<FuturesContractLineData>> responseObj = JacksonUtil.decode(response, JacksonUtil
				.getGenericType(Response.class, JacksonUtil.getGenericType(List.class, FuturesContractLineData.class)));
		if ("200".equals(responseObj.getCode())) {
			return responseObj.getResult();
		} else {
			throw new RuntimeException("http获取期分时图数据异常!" + responseObj.getCode());
		}
	}

	public static List<FuturesContractLineData> dayLine(String symbol, String startTime, String endTime) {
		String url = baseUrl + "market/" + symbol + "/dayline?flag=1";
		if (!StringUtil.isEmpty(startTime)) {
			url += "&startTime=" + startTime;
		}
		if (!StringUtil.isEmpty(endTime)) {
			url += "&endTime=" + endTime;
		}
		String response = restTemplate.getForObject(url, String.class);
		Response<List<FuturesContractLineData>> responseObj = JacksonUtil.decode(response, JacksonUtil
				.getGenericType(Response.class, JacksonUtil.getGenericType(List.class, FuturesContractLineData.class)));
		if ("200".equals(responseObj.getCode())) {
			return responseObj.getResult();
		} else {
			throw new RuntimeException("http获取期日K线图数据异常!" + responseObj.getCode());
		}
	}

	public static List<FuturesContractLineData> minsLine(String symbol, Integer mins, Integer dayCount) {
		String url = baseUrl + "market/" + symbol + "/minsline?flag=1";
		if (mins != null) {
			url += "&mins=" + mins;
		}
		if (dayCount != null) {
			url += "&dayCount=" + dayCount;
		}
		String response = restTemplate.getForObject(url, String.class);
		Response<List<FuturesContractLineData>> responseObj = JacksonUtil.decode(response, JacksonUtil
				.getGenericType(Response.class, JacksonUtil.getGenericType(List.class, FuturesContractLineData.class)));
		if ("200".equals(responseObj.getCode())) {
			return responseObj.getResult();
		} else {
			throw new RuntimeException("http获取期分钟K线图数据异常!" + responseObj.getCode());
		}
	}

}
