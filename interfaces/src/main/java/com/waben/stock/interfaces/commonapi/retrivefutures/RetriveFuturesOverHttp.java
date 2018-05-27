package com.waben.stock.interfaces.commonapi.retrivefutures;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractLineData;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.StringUtil;

public class RetriveFuturesOverHttp {

	private static RestTemplate restTemplate = new RestTemplate();

	private static String baseUrl = "http://10.0.0.48:9092/";

	public static FuturesContractMarket market(String symbol) {
		String url = baseUrl + "market/" + symbol;
		String response = restTemplate.getForObject(url, String.class);
		return JacksonUtil.decode(response, FuturesContractMarket.class);
	}

	public static List<FuturesContractLineData> timeLine(String symbol, Integer dayCount) {
		String url = baseUrl + "market/" + symbol + "/timeline?flag=1";
		if (dayCount != null) {
			url += "&dayCount=" + dayCount;
		}
		String response = restTemplate.getForObject(url, String.class);
		List<FuturesContractLineData> responseObj = JacksonUtil.decode(response,
				JacksonUtil.getGenericType(List.class, FuturesContractLineData.class));
		return responseObj;
	}

	public static List<FuturesContractLineData> dayLine(String symbol, String startTime, String endTime) {
		try {
			String url = baseUrl + "market/" + symbol + "/dayLine?flag=1";
			if (!StringUtil.isEmpty(startTime)) {
				url += "&startTime=" + URLEncoder.encode(startTime, "UTF-8");
			}
			if (!StringUtil.isEmpty(endTime)) {
				url += "&endTime=" + URLEncoder.encode(endTime, "UTF-8");
			}
			String response = restTemplate.getForObject(url, String.class);
			List<FuturesContractLineData> responseObj = JacksonUtil.decode(response,
					JacksonUtil.getGenericType(List.class, FuturesContractLineData.class));
			return responseObj;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("http获取期货日K线异常!", e);
		}
	}

	public static List<FuturesContractLineData> minsLine(String symbol, Integer mins, Integer dayCount) {
		String url = baseUrl + "market/" + symbol + "/minsLine?flag=1";
		if (mins != null) {
			url += "&mins=" + mins;
		}
		if (dayCount != null) {
			url += "&dayCount=" + dayCount;
		}
		String response = restTemplate.getForObject(url, String.class);
		List<FuturesContractLineData> responseObj = JacksonUtil.decode(response,
				JacksonUtil.getGenericType(List.class, FuturesContractLineData.class));
		return responseObj;
	}

}
