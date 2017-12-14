package com.waben.stock.applayer.strategist.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.applayer.strategist.dto.stockcontent.StockMarketWithFavoriteDto;
import com.waben.stock.applayer.strategist.dto.stockcontent.StockRecommendWithMarketDto;
import com.waben.stock.applayer.strategist.retrivestock.RetriveStockOverHttp;
import com.waben.stock.applayer.strategist.retrivestock.bean.StockKLine;
import com.waben.stock.applayer.strategist.retrivestock.bean.StockMarket;
import com.waben.stock.applayer.strategist.security.SecurityUtil;
import com.waben.stock.applayer.strategist.service.FavoriteStockService;
import com.waben.stock.applayer.strategist.service.StockMarketService;
import com.waben.stock.applayer.strategist.service.StockService;
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
	private StockService stockService;

	@Autowired
	private StockMarketService stockMarketService;

	@Autowired
	private FavoriteStockService favoriteStockService;

	public PageInfo<StockDto> pages(StockQuery stockQuery) {
		Response<PageInfo<StockDto>> response = stockService.pagesByQuery(stockQuery);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockDto findById(Long stockId) {
		Response<StockDto> response = stockService.fetchById(stockId);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockDto findByCode(String stockCode) {
		Response<StockDto> response = stockService.fetchWithExponentByCode(stockCode);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<StockRecommendWithMarketDto> stockRecommend(int page, int size) {
		// TODO 此处模拟返回假数据
		List<StockRecommendWithMarketDto> content = new ArrayList<>();
		if (page == 0) {
			List<String> codes = new ArrayList<>();
			codes.add("000001");
			codes.add("000002");
			codes.add("000004");
			List<StockMarket> list = stockMarketService.listStockMarket(codes);

			StockRecommendWithMarketDto dto1 = new StockRecommendWithMarketDto();
			dto1.setId(1L);
			dto1.setCode("000001");
			dto1.setName("平安银行");
			dto1.setSort(1);
			dto1.setRecommendTime(new Date());
			dto1.setLastPrice(list.get(0).getLastPrice());
			dto1.setUpDropPrice(list.get(0).getUpDropPrice());
			dto1.setUpDropSpeed(list.get(0).getUpDropSpeed());
			StockRecommendWithMarketDto dto2 = new StockRecommendWithMarketDto();
			dto2.setId(2L);
			dto2.setCode("000002");
			dto2.setName("万科A");
			dto2.setSort(2);
			dto2.setRecommendTime(new Date());
			dto2.setLastPrice(list.get(1).getLastPrice());
			dto2.setUpDropPrice(list.get(1).getUpDropPrice());
			dto2.setUpDropSpeed(list.get(1).getUpDropSpeed());
			StockRecommendWithMarketDto dto3 = new StockRecommendWithMarketDto();
			dto3.setId(3L);
			dto3.setCode("000004");
			dto3.setName("国农科技");
			dto3.setSort(3);
			dto3.setRecommendTime(new Date());
			dto3.setLastPrice(list.get(2).getLastPrice());
			dto3.setUpDropPrice(list.get(2).getUpDropPrice());
			dto3.setUpDropSpeed(list.get(2).getUpDropSpeed());

			content.add(dto1);
			content.add(dto2);
			content.add(dto3);

			content.add(dto1);
			content.add(dto2);
			content.add(dto3);

			content.add(dto1);
			content.add(dto2);
			content.add(dto3);
			content.add(dto3);
		}
		if (page == 1) {
			List<String> codes = new ArrayList<>();
			codes.add("000001");
			codes.add("000002");
			codes.add("000004");
			List<StockMarket> list = stockMarketService.listStockMarket(codes);

			StockRecommendWithMarketDto dto1 = new StockRecommendWithMarketDto();
			dto1.setId(1L);
			dto1.setCode("000001");
			dto1.setName("平安银行");
			dto1.setSort(1);
			dto1.setRecommendTime(new Date());
			dto1.setLastPrice(list.get(0).getLastPrice());
			dto1.setUpDropPrice(list.get(0).getUpDropPrice());
			dto1.setUpDropSpeed(list.get(0).getUpDropSpeed());
			StockRecommendWithMarketDto dto2 = new StockRecommendWithMarketDto();
			dto2.setId(2L);
			dto2.setCode("000002");
			dto2.setName("万科A");
			dto2.setSort(2);
			dto2.setRecommendTime(new Date());
			dto2.setLastPrice(list.get(1).getLastPrice());
			dto2.setUpDropPrice(list.get(1).getUpDropPrice());
			dto2.setUpDropSpeed(list.get(1).getUpDropSpeed());
			StockRecommendWithMarketDto dto3 = new StockRecommendWithMarketDto();
			dto3.setId(3L);
			dto3.setCode("000004");
			dto3.setName("国农科技");
			dto3.setSort(3);
			dto3.setRecommendTime(new Date());
			dto3.setLastPrice(list.get(2).getLastPrice());
			dto3.setUpDropPrice(list.get(2).getUpDropPrice());
			dto3.setUpDropSpeed(list.get(2).getUpDropSpeed());

			content.add(dto1);
			content.add(dto2);
			content.add(dto3);
		}
		return new PageInfo<>(content, 1, true, 3L, size, page, true);
	}

	public List<StockKLine> listKLine(String stockCode, Integer type, String startTime, String endTime) {
		return RetriveStockOverHttp.listKLine(restTemplate, stockCode, type, startTime, endTime);
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
				Response<List<FavoriteStockDto>> response = favoriteStockService.listsByPublisherId(publisherId);
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

}
