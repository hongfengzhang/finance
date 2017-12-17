package com.waben.stock.applayer.tactics.retrivestock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.waben.stock.applayer.tactics.retrivestock.bean.StockExponentVariety;
import com.waben.stock.applayer.tactics.retrivestock.bean.StockKLine;
import com.waben.stock.applayer.tactics.retrivestock.bean.StockMarket;
import com.waben.stock.interfaces.util.JacksonUtil;

public class RetriveStockOverHttp {

	public static List<StockMarket> listStockMarket(RestTemplate restTemplate, List<String> codes) {
		String url = "http://lemi.esongbai.com/stk/stk/list.do?codes="
				+ codes.toString().substring(1, codes.toString().length() - 1).replaceAll(" ", "");
		String response = restTemplate.getForObject(url, String.class);
		try {
			JsonNode dataNode = JacksonUtil.objectMapper.readValue(response, JsonNode.class).get("data");
			JavaType javaType = JacksonUtil.objectMapper.getTypeFactory().constructParametricType(ArrayList.class,
					StockMarket.class);
			List<StockMarket> list = JacksonUtil.objectMapper.readValue(dataNode.toString(), javaType);
			return list;
		} catch (IOException e) {
			throw new RuntimeException("http获取股票行情异常!", e);
		}
	}

	public static List<StockExponentVariety> listStockExponentVariety(RestTemplate restTemplate) {
		String url = "http://lemi.esongbai.com/order/order/getStockExponentVariety.do";
		String response = restTemplate.getForObject(url, String.class);
		try {
			JsonNode dataNode = JacksonUtil.objectMapper.readValue(response, JsonNode.class).get("data");
			JavaType javaType = JacksonUtil.objectMapper.getTypeFactory().constructParametricType(ArrayList.class,
					StockExponentVariety.class);
			List<StockExponentVariety> list = JacksonUtil.objectMapper.readValue(dataNode.toString(), javaType);
			return list;
		} catch (IOException e) {
			throw new RuntimeException("http获取指数品种列表异常!", e);
		}
	}

	public static List<StockKLine> listKLine(RestTemplate restTemplate, String stockCode, Integer type,
			String startTime, String endTime, Integer limit) {
		StringBuilder url = new StringBuilder("http://lemi.esongbai.com/stk/stk/kline.do?code=" + stockCode);
		if (type == 1) {
			url.append("&type=day");
		} else if (type == 2) {
			url.append("&type=week");
		} else {
			url.append("&type=day");
		}
		if (startTime != null && !"".equals(startTime)) {
			url.append("&startTime=" + startTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			url.append("&endTime=" + endTime);
		}
		if (limit != null && limit > 0) {
			url.append("&limit=" + limit);
		}

		String response = restTemplate.getForObject(url.toString(), String.class);
		try {
			JsonNode dataNode = JacksonUtil.objectMapper.readValue(response, JsonNode.class).get("data");
			JavaType javaType = JacksonUtil.objectMapper.getTypeFactory().constructParametricType(ArrayList.class,
					StockKLine.class);
			List<StockKLine> list = JacksonUtil.objectMapper.readValue(dataNode.toString(), javaType);
			return list;
		} catch (IOException e) {
			throw new RuntimeException("http获取K线图数据异常!", e);
		}
	}
}
