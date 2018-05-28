package com.waben.stock.futuresgateway.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.futuresgateway.cache.RedisCache;
import com.waben.stock.futuresgateway.dao.FuturesContractDao;
import com.waben.stock.futuresgateway.entity.FuturesContract;
import com.waben.stock.futuresgateway.pojo.FuturesContractLineData;
import com.waben.stock.futuresgateway.twsapi.TwsConstant;
import com.waben.stock.futuresgateway.util.JacksonUtil;

/**
 * 期货行情 Service
 * 
 * @author luomengan
 *
 */
@Service
public class FuturesMarketService {

	@Autowired
	private FuturesContractDao contractDao;

	@Autowired
	private RedisCache redisCache;

	private SimpleDateFormat daySdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat fullSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public FuturesContract market(String symbol) {
		return contractDao.retrieveContractBySymbol(symbol);
	}

	public List<FuturesContractLineData> timeLine(String symbol, Integer dayCount) {
		List<FuturesContractLineData> result = new ArrayList<>();

		List<FuturesContractLineData> cacheDataList = new ArrayList<>();
		// 从redis中获取
		FuturesContract contract = contractDao.retrieveContractBySymbol(symbol);
		Map<String, String> map = redisCache
				.hgetAll(String.format(TwsConstant.TimeLine_RedisKey, contract.getId() + "_" + contract.getSymbol()));
		Collection<String> datas = map.values();
		for (String data : datas) {
			FuturesContractLineData lineData = JacksonUtil.decode(data, FuturesContractLineData.class);
			cacheDataList.add(lineData);
		}
		// 按时间倒序排序
		Collections.sort(cacheDataList, new TimeDownComparator());
		// 返回特定天数的分时数据
		if (dayCount == null || dayCount <= 0) {
			dayCount = 1;
		}
		Map<String, Boolean> dayMap = new HashMap<>();
		for (FuturesContractLineData lineData : cacheDataList) {
			String timeStr = daySdf.format(lineData.getTime());
			if (dayMap.containsKey(timeStr)) {
				result.add(lineData);
			} else {
				if (dayMap.keySet().size() < dayCount) {
					result.add(lineData);
					dayMap.put(timeStr, true);
				}
			}
		}
		return result;
	}

	private class TimeDownComparator implements Comparator<FuturesContractLineData> {
		@Override
		public int compare(FuturesContractLineData o1, FuturesContractLineData o2) {
			Date time1 = o1.getTime();
			Date time2 = o2.getTime();
			return time2.compareTo(time1);
		}
	}

	public List<FuturesContractLineData> dayLine(String symbol, String startTimeStr, String endTimeStr) {
		List<FuturesContractLineData> result = new ArrayList<>();
		// 获取开始和结束时间
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = fullSdf.parse(startTimeStr);
			endTime = fullSdf.parse(endTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (startTime == null) {
			startTime = new Date();
		}
		if (endTime == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startTime);
			cal.add(Calendar.YEAR, -1);
			endTime = cal.getTime();
		}
		List<FuturesContractLineData> cacheDataList = new ArrayList<>();
		// 从redis中获取
		FuturesContract contract = contractDao.retrieveContractBySymbol(symbol);
		Map<String, String> map = redisCache
				.hgetAll(String.format(TwsConstant.TimeLine_RedisKey, contract.getId() + "_" + contract.getSymbol()));
		Collection<String> datas = map.values();
		for (String data : datas) {
			FuturesContractLineData lineData = JacksonUtil.decode(data, FuturesContractLineData.class);
			cacheDataList.add(lineData);
		}
		// 按时间倒序排序
		Collections.sort(cacheDataList, new TimeDownComparator());
		// 返回特定天数的分时数据
		for (FuturesContractLineData lineData : cacheDataList) {
			Date time = lineData.getTime();
			if (time.getTime() >= startTime.getTime() && time.getTime() <= endTime.getTime()) {
				result.add(lineData);
			}
		}
		return result;
	}

	public List<FuturesContractLineData> minsLine(String symbol, Integer mins, Integer dayCount) {
		List<FuturesContractLineData> result = new ArrayList<>();
		List<FuturesContractLineData> cacheDataList = new ArrayList<>();
		// 从redis中获取
		String redisKey = null;
		if (mins == null || mins < 1) {
			redisKey = TwsConstant.Min1Line_RedisKey;
		} else if (mins == 1) {
			redisKey = TwsConstant.Min1Line_RedisKey;
		} else if (mins == 3) {
			redisKey = TwsConstant.Mins3Line_RedisKey;
		} else if (mins == 5) {
			redisKey = TwsConstant.Mins5Line_RedisKey;
		} else if (mins == 15) {
			redisKey = TwsConstant.Mins15Line_RedisKey;
		} else {
			return result;
		}
		FuturesContract contract = contractDao.retrieveContractBySymbol(symbol);
		Map<String, String> map = redisCache
				.hgetAll(String.format(redisKey, contract.getId() + "_" + contract.getSymbol()));
		Collection<String> datas = map.values();
		for (String data : datas) {
			FuturesContractLineData lineData = JacksonUtil.decode(data, FuturesContractLineData.class);
			cacheDataList.add(lineData);
		}
		// 按时间倒序排序
		Collections.sort(cacheDataList, new TimeDownComparator());
		// 返回特定天数的分时数据
		if (dayCount == null || dayCount <= 0) {
			dayCount = 1;
		}
		Map<String, Boolean> dayMap = new HashMap<>();
		for (FuturesContractLineData lineData : cacheDataList) {
			String timeStr = daySdf.format(lineData.getTime());
			if (dayMap.containsKey(timeStr)) {
				result.add(lineData);
			} else {
				if (dayMap.keySet().size() < dayCount) {
					result.add(lineData);
					dayMap.put(timeStr, true);
				}
			}
		}
		return result;
	}

}
