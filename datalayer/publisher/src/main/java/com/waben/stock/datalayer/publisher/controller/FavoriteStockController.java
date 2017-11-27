package com.waben.stock.datalayer.publisher.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.publisher.service.FavoriteStockService;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.publisher.FavoriteStockInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * 收藏股票 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/favoriteStock")
public class FavoriteStockController implements FavoriteStockInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FavoriteStockService favoriteStockService;

	@Override
	public Response<FavoriteStockDto> add(FavoriteStockDto favoriteStockDto) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(FavoriteStockDto.class,
				favoriteStockService.save(favoriteStockDto.getPublisherId(), favoriteStockDto.getStockId(),
						favoriteStockDto.getName(), favoriteStockDto.getCode(), favoriteStockDto.getPinyinAbbr()),
				false));
	}

	@Override
	public Response<String> drop(Long publisherId, String stockIds) {
		String[] stockIdStrArr = stockIds.split("-");
		Long[] stockIdArr = new Long[stockIdStrArr.length];
		for (int i = 0; i < stockIdStrArr.length; i++) {
			stockIdArr[i] = Long.parseLong(stockIdStrArr[i]);
		}
		favoriteStockService.remove(publisherId, stockIdArr);
		return new Response<>("successful");
	}

	@Override
	public Response<String> top(Long publisherId, String stockIds) {
		String[] stockIdStrArr = stockIds.split("-");
		Long[] stockIdArr = new Long[stockIdStrArr.length];
		for (int i = 0; i < stockIdStrArr.length; i++) {
			stockIdArr[i] = Long.parseLong(stockIdStrArr[i]);
		}
		favoriteStockService.top(publisherId, stockIdArr);
		return new Response<>("successful");
	}

	@Override
	public Response<List<FavoriteStockDto>> listsByPublisherId(Long publisherId) {
		return new Response<>(CopyBeanUtils.copyListBeanPropertiesToList(favoriteStockService.list(publisherId),
				FavoriteStockDto.class));
	}

	@Override
	public Response<List<Long>> listsStockId(Long publisherId) {
		return new Response<>(favoriteStockService.listStockIdByPublisherId(publisherId));
	}

}
