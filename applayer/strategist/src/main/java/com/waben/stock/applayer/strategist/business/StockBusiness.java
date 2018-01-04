package com.waben.stock.applayer.strategist.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.applayer.strategist.dto.stockcontent.StockDiscDto;
import com.waben.stock.applayer.strategist.dto.stockcontent.StockMarketWithFavoriteDto;
import com.waben.stock.applayer.strategist.dto.stockcontent.StockRecommendWithMarketDto;
import com.waben.stock.applayer.strategist.reference.FavoriteStockReference;
import com.waben.stock.applayer.strategist.reference.StockReference;
import com.waben.stock.applayer.strategist.retrivestock.RetriveStockOverHttp;
import com.waben.stock.applayer.strategist.retrivestock.bean.StockKLine;
import com.waben.stock.applayer.strategist.retrivestock.bean.StockMarket;
import com.waben.stock.applayer.strategist.retrivestock.bean.StockTimeLine;
import com.waben.stock.applayer.strategist.security.SecurityUtil;
import com.waben.stock.applayer.strategist.service.StockMarketService;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;

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
		if (holidayBusiness.isTradeDay()) {
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

}
