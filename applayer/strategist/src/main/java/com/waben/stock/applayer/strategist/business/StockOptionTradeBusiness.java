package com.waben.stock.applayer.strategist.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.applayer.strategist.dto.stockoption.StockOptionTradeWithMarketDto;
import com.waben.stock.applayer.strategist.reference.StockOptionTradeReference;
import com.waben.stock.interfaces.commonapi.retrivestock.RetriveStockOverHttp;
import com.waben.stock.interfaces.commonapi.retrivestock.bean.StockMarket;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeUserQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * 期权交易 Business
 * 
 * @author luomengan
 *
 */
@Service
public class StockOptionTradeBusiness {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Qualifier("stockOptionTradeReference")
	private StockOptionTradeReference tradeReference;

	public StockOptionTradeDto add(StockOptionTradeDto stockOptionTradeDto) {
		Response<StockOptionTradeDto> response = tradeReference.add(stockOptionTradeDto);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<StockOptionTradeDto> pagesByUserQuery(StockOptionTradeUserQuery query) {
		Response<PageInfo<StockOptionTradeDto>> response = tradeReference.pagesByUserQuery(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
	
	public StockOptionTradeDto userRight(Long publisherId, Long id) {
		Response<StockOptionTradeDto> response = tradeReference.userRight(publisherId, id);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockOptionTradeWithMarketDto wrapMarketInfo(StockOptionTradeDto trade) {
		List<StockOptionTradeDto> list = new ArrayList<>();
		list.add(trade);
		List<StockOptionTradeWithMarketDto> marketList = wrapMarketInfo(list);
		return marketList.get(0);
	}

	public List<StockOptionTradeWithMarketDto> wrapMarketInfo(List<StockOptionTradeDto> list) {
		List<StockOptionTradeWithMarketDto> result = CopyBeanUtils.copyListBeanPropertiesToList(list,
				StockOptionTradeWithMarketDto.class);
		List<String> codes = new ArrayList<>();
		for (StockOptionTradeWithMarketDto trade : result) {
			codes.add(trade.getStockCode());
		}
		if (codes.size() > 0) {
			List<StockMarket> stockMarketList = RetriveStockOverHttp.listStockMarket(restTemplate, codes);
			if (stockMarketList != null && stockMarketList.size() > 0) {
				for (int i = 0; i < stockMarketList.size(); i++) {
					StockMarket market = stockMarketList.get(i);
					StockOptionTradeWithMarketDto record = result.get(i);
					record.setStockName(market.getName());
					record.setLastPrice(market.getLastPrice());
					record.setUpDropPrice(market.getUpDropPrice());
					record.setUpDropSpeed(market.getUpDropSpeed());
				}
			}
		}
		return result;
	}

}
