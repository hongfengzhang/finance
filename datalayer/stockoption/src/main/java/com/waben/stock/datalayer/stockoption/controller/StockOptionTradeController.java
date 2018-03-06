package com.waben.stock.datalayer.stockoption.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.stockoption.entity.StockOptionTrade;
import com.waben.stock.datalayer.stockoption.service.StockOptionTradeService;
import com.waben.stock.interfaces.dto.stockoption.OfflineStockOptionTradeDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeUserQuery;
import com.waben.stock.interfaces.service.stockoption.StockOptionTradeInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;

@RestController
@RequestMapping("/stockoptiontrade")
public class StockOptionTradeController implements StockOptionTradeInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StockOptionTradeService stockOptionTradeService;

	@Override
	public Response<PageInfo<StockOptionTradeDto>> pagesByQuery(@RequestBody StockOptionTradeQuery query) {
		Page<StockOptionTrade> page = stockOptionTradeService.pagesByQuery(query);
		PageInfo<StockOptionTradeDto> result = PageToPageInfo.pageToPageInfo(page, StockOptionTradeDto.class);
		for (int i = 0; i < page.getContent().size(); i++) {
			OfflineStockOptionTradeDto offlineStockOptionTradeDto = CopyBeanUtils.copyBeanProperties(
					OfflineStockOptionTradeDto.class, page.getContent().get(i).getOfflineTrade(), false);
			result.getContent().get(i).setOfflineTradeDto(offlineStockOptionTradeDto);
		}
		return new Response<>(result);
	}

	@Override
	public Response<StockOptionTradeDto> fetchById(@PathVariable Long id) {
		StockOptionTradeDto result = CopyBeanUtils.copyBeanProperties(StockOptionTradeDto.class,
				stockOptionTradeService.findById(id), false);
		return new Response<>(result);
	}

	@Override
	public Response<StockOptionTradeDto> settlement(@PathVariable Long id) {
		StockOptionTradeDto result = CopyBeanUtils.copyBeanProperties(StockOptionTradeDto.class,
				stockOptionTradeService.settlement(id), false);
		return new Response<>(result);
	}

	@Override
	public Response<StockOptionTradeDto> success(@PathVariable Long id) {
		StockOptionTrade result = stockOptionTradeService.success(id);
		StockOptionTradeDto stockOptionTradeDto = CopyBeanUtils.copyBeanProperties(StockOptionTradeDto.class, result,
				false);
		return new Response<>(stockOptionTradeDto);
	}

	@Override
	public Response<StockOptionTradeDto> fail(@PathVariable Long id) {
		StockOptionTrade result = stockOptionTradeService.fail(id);
		StockOptionTradeDto stockOptionTradeDto = CopyBeanUtils.copyBeanProperties(StockOptionTradeDto.class, result,
				false);
		return new Response<>(stockOptionTradeDto);
	}

	@Override
	public Response<StockOptionTradeDto> exercise(@PathVariable Long id) {
		StockOptionTrade result = stockOptionTradeService.exercise(id);
		StockOptionTradeDto stockOptionTradeDto = CopyBeanUtils.copyBeanProperties(StockOptionTradeDto.class, result,
				false);
		return new Response<>(stockOptionTradeDto);
	}

	@Override
	public Response<StockOptionTradeDto> add(@RequestBody StockOptionTradeDto stockOptionTradeDto) {
		logger.info("发布人{}申购期权{}，名义本金 {}!", stockOptionTradeDto.getPublisherId(), stockOptionTradeDto.getStockCode(),
				stockOptionTradeDto.getNominalAmount());
		StockOptionTrade stockOptionTrade = CopyBeanUtils.copyBeanProperties(StockOptionTrade.class,
				stockOptionTradeDto, false);
		return new Response<>(CopyBeanUtils.copyBeanProperties(StockOptionTradeDto.class,
				stockOptionTradeService.save(stockOptionTrade), false));
	}

	@Override
	public Response<PageInfo<StockOptionTradeDto>> pagesByUserQuery(@RequestBody StockOptionTradeUserQuery query) {
		Page<StockOptionTrade> page = stockOptionTradeService.pagesByUserQuery(query);
		PageInfo<StockOptionTradeDto> result = PageToPageInfo.pageToPageInfo(page, StockOptionTradeDto.class);
		return new Response<>(result);
	}

	@Override
	public Response<StockOptionTradeDto> userRight(@PathVariable Long publisherId, @PathVariable Long id) {
		logger.info("发布人{}申请行权期权交易{}!", publisherId, id);
		StockOptionTrade trade = stockOptionTradeService.userRight(publisherId, id);
		return new Response<>(CopyBeanUtils.copyBeanProperties(StockOptionTradeDto.class, trade, false));
	}

}
