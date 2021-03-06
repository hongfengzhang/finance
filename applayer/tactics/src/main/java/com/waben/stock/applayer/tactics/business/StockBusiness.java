package com.waben.stock.applayer.tactics.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.applayer.tactics.dto.stockcontent.StockDiscDto;
import com.waben.stock.applayer.tactics.dto.stockcontent.StockMarketWithFavoriteDto;
import com.waben.stock.applayer.tactics.dto.stockcontent.StockRecommendWithMarketDto;
import com.waben.stock.applayer.tactics.reference.FavoriteStockReference;
import com.waben.stock.applayer.tactics.reference.StockReference;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.applayer.tactics.service.RedisCache;
import com.waben.stock.applayer.tactics.service.StockMarketService;
import com.waben.stock.interfaces.commonapi.retrivestock.RetriveStockOverHttp;
import com.waben.stock.interfaces.commonapi.retrivestock.bean.StockKLine;
import com.waben.stock.interfaces.commonapi.retrivestock.bean.StockMarket;
import com.waben.stock.interfaces.commonapi.retrivestock.bean.StockTimeLine;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.RedisCacheKeyType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.JacksonUtil;

/**
 * 股票 Business
 * 
 * @author luomengan
 *
 */
@Service
public class StockBusiness {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Qualifier("stockReference")
	private StockReference stockReference;

	@Autowired
	private StockMarketService stockMarketService;

	@Autowired
	@Qualifier("favoriteStockReference")
	private FavoriteStockReference favoriteStockReference;

	@Autowired
	private HolidayBusiness holidayBusiness;

	@Autowired
	private RedisCache redisCache;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public PageInfo<StockDto> pages(StockQuery stockQuery) {
		Response<PageInfo<StockDto>> response = stockReference.pagesByQuery(stockQuery);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockDto findById(Long stockId) {
		Response<StockDto> response = stockReference.fetchById(stockId);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockDto findByCode(String stockCode) {
		Response<StockDto> response = stockReference.fetchWithExponentByCode(stockCode);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	private List<StockRecommendWithMarketDto> convertStockRecommend(List<StockMarket> marketList) {
		List<StockRecommendWithMarketDto> result = new ArrayList<>();
		for (int i = 0; i < marketList.size(); i++) {
			StockMarket market = marketList.get(i);
			StockRecommendWithMarketDto inner = new StockRecommendWithMarketDto();
			inner.setId(new Long(i + 1));
			inner.setCode(market.getInstrumentId());
			inner.setName(market.getName());
			inner.setSort(i + 1);
			inner.setRecommendTime(new Date());
			inner.setLastPrice(market.getLastPrice());
			inner.setUpDropPrice(market.getUpDropPrice());
			inner.setUpDropSpeed(market.getUpDropSpeed());
			result.add(inner);
		}
		return result;
	}

	public PageInfo<StockRecommendWithMarketDto> stockRecommend(int page, int size) {
		// TODO 此处模拟返回假数据
		List<StockRecommendWithMarketDto> content = new ArrayList<>();
		if (page == 0) {
			List<String> codes = new ArrayList<>();
			codes.add("000001");
			codes.add("000002");
			codes.add("000004");
			codes.add("000005");
			codes.add("000008");
			codes.add("000009");
			codes.add("000010");

			List<StockMarket> list = stockMarketService.listStockMarket(codes);
			content = convertStockRecommend(list);
		}
		return new PageInfo<>(content, 1, true, 3L, size, page, true);
	}

	public List<StockKLine> listKLine(String stockCode, Integer type, String startTime, String endTime, Integer limit) {
		return RetriveStockOverHttp.listKLine(restTemplate, stockCode, type, startTime, endTime, limit);
	}

	public List<StockTimeLine> listTimeLine(String stockCode) {
		return RetriveStockOverHttp.listTimeLine(restTemplate, stockCode);
	}

	public StockMarketWithFavoriteDto marketByCode(String code) {
		if (code != null && !"".equals(code)) {
			List<String> codes = new ArrayList<>();
			codes.add(code);
			StockMarket market = RetriveStockOverHttp.listStockMarket(restTemplate, codes).get(0);
			StockMarketWithFavoriteDto result = CopyBeanUtils.copyBeanProperties(StockMarketWithFavoriteDto.class,
					market, false);
			Long publisherId = SecurityUtil.getUserId();
			if (publisherId != null) {
				Response<List<FavoriteStockDto>> response = favoriteStockReference.listsByPublisherId(publisherId);
				if (response.getCode().equals("200")) {
					for (FavoriteStockDto favorite : response.getResult()) {
						if (favorite.getCode().equals(code)) {
							result.setFavorite(true);
						}
					}
				}
			}
			result.setIsTradeTime(holidayBusiness.isTradeTime());
			return result;
		}
		return null;
	}

	public StockDiscDto disc(String code) {
		StockDiscDto result = new StockDiscDto();

		List<String> codes = new ArrayList<>();
		codes.add(code);
		StockMarket market = RetriveStockOverHttp.listStockMarket(restTemplate, codes).get(0);
		result.setCode(market.getInstrumentId());
		result.setHighestPrice(market.getHighestPrice());
		result.setLowestPrice(market.getLowestPrice());
		result.setName(market.getName());
		result.setUpDropPrice(market.getUpDropPrice());
		result.setUpDropSpeed(market.getUpDropSpeed());

		List<StockKLine> kLine = listKLine(code, 1, null, null, 1);
		if (kLine != null && kLine.size() > 0) {
			result.setYesterdayClosePrice(kLine.get(0).getClosePrice());
		}
		if (holidayBusiness.isTradeDay(new Date())) {
			List<StockTimeLine> timeLine = listTimeLine(code);
			if (timeLine != null && timeLine.size() > 0 && sdf.format(new Date()).equals(timeLine.get(0).getDay())) {
				result.setTodayOpenPrice(timeLine.get(0).getOpenPrice());
			}
		}
		return result;
	}

	public boolean isSuspension(String stockCode) {
		List<String> codes = new ArrayList<>();
		codes.add(stockCode);
		StockMarket market = RetriveStockOverHttp.listStockMarket(restTemplate, codes).get(0);
		return market.getStatus() == 0;
	}

	/**
	 * 检查股票是否可以购买，停牌、涨停、跌停不能购买
	 */
	public void checkStock(String stockCode) {
		List<String> codes = new ArrayList<>();
		codes.add(stockCode);
		StockMarket market = RetriveStockOverHttp.listStockMarket(restTemplate, codes).get(0);
		if (market.getStatus() == 0) {
			throw new ServiceException(ExceptionConstant.STOCK_SUSPENSION_EXCEPTION);
		} else if (market.getUpDropSpeed().compareTo(new BigDecimal(0.1)) >= 0) {
			throw new ServiceException(ExceptionConstant.STOCK_ARRIVEUPLIMIT_EXCEPTION);
		} else if (market.getUpDropSpeed().compareTo(new BigDecimal(-0.1)) <= 0) {
			throw new ServiceException(ExceptionConstant.STOCK_ARRIVEDOWNLIMIT_EXCEPTION);
		} else if (market.getName().toUpperCase().startsWith("ST") || market.getName().toUpperCase().startsWith("*ST")
				|| market.getName().toUpperCase().startsWith("S*ST")) {
			throw new ServiceException(ExceptionConstant.ST_STOCK_CANNOTBUY_EXCEPTION);
		}
		// 判断数据库中的状态是否可用
		StockDto stock = findByCode(stockCode);
		if (!stock.getStatus()) {
			throw new ServiceException(ExceptionConstant.BLACKLIST_STOCK_EXCEPTION);
		}
	}

	/**
	 * 检查股票是否连续两个涨停
	 */
	public void check2LimitUp(String stockCode) {
		List<StockKLine> list = RetriveStockOverHttp.listKLine(restTemplate, stockCode, 1, null, null, 3);
		if (list != null && list.size() >= 3) {
			StockKLine day1 = list.get(0);
			StockKLine day2 = list.get(1);
			StockKLine day3 = list.get(2);
			BigDecimal speed1 = day1.getClosePrice().subtract(day2.getClosePrice()).divide(day2.getClosePrice(), 4,
					RoundingMode.HALF_EVEN);
			BigDecimal speed2 = day2.getClosePrice().subtract(day3.getClosePrice()).divide(day3.getClosePrice(), 4,
					RoundingMode.HALF_EVEN);
			if (speed1.compareTo(new BigDecimal("0.1")) >= 0 && speed2.compareTo(new BigDecimal("0.1")) >= 0) {
				throw new ServiceException(ExceptionConstant.STOCKOPTION_2UPLIMIT_CANNOTBY_EXCEPTION);
			}
		}
	}

	public List<StockMarket> ranking(String exponent, int rankType, int size) {
		String code = "4609";
		if ("2A01".equals(exponent)) {
			code = "4609";
		} else if ("1A0001".equals(exponent)) {
			code = "4353";
		} else if ("399006".equals(exponent)) {
			code = "4621";
		}
		Map<String, String> map = redisCache.hgetAll(String.format(RedisCacheKeyType.StockMarket.getKey(), code));
		Collection<String> stockStrs = map.values();
		List<StockMarket> stockList = new ArrayList<>();
		for (String stockStr : stockStrs) {
			StockMarket stockMarket = JacksonUtil.decode(stockStr, StockMarket.class);
			if (stockMarket.getUpDropSpeed() != null && stockMarket.getStatus() == 1) {
				stockList.add(stockMarket);
			}
		}
		if (rankType == 1) {
			// 涨幅榜
			Collections.sort(stockList, new SpeedUpComparator());
		} else if (rankType == 2) {
			// 跌幅榜
			Collections.sort(stockList, new SpeedDownComparator());
		} else if (rankType == 3) {
			// 价格降序
			Collections.sort(stockList, new PriceUpComparator());
		} else if (rankType == 4) {
			// 价格升序
			Collections.sort(stockList, new PriceDownComparator());
		}

		List<StockMarket> result = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			if (i < stockList.size()) {
				result.add(stockList.get(i));
			}
		}
		return result;
	}

	private class SpeedUpComparator implements Comparator<StockMarket> {
		@Override
		public int compare(StockMarket o1, StockMarket o2) {
			BigDecimal upDropSpeed1 = o1.getUpDropSpeed();
			BigDecimal upDropSpeed2 = o2.getUpDropSpeed();
			return upDropSpeed1.compareTo(upDropSpeed2) * -1;
		}
	}

	private class SpeedDownComparator implements Comparator<StockMarket> {
		@Override
		public int compare(StockMarket o1, StockMarket o2) {
			BigDecimal upDropSpeed1 = o1.getUpDropSpeed();
			BigDecimal upDropSpeed2 = o2.getUpDropSpeed();
			return upDropSpeed1.compareTo(upDropSpeed2);
		}
	}

	private class PriceUpComparator implements Comparator<StockMarket> {
		@Override
		public int compare(StockMarket o1, StockMarket o2) {
			BigDecimal price1 = o1.getLastPrice();
			BigDecimal price2 = o2.getLastPrice();
			return price1.compareTo(price2) * -1;
		}
	}

	private class PriceDownComparator implements Comparator<StockMarket> {
		@Override
		public int compare(StockMarket o1, StockMarket o2) {
			BigDecimal price1 = o1.getLastPrice();
			BigDecimal price2 = o2.getLastPrice();
			return price1.compareTo(price2);
		}
	}

}
