package com.waben.stock.interfaces.service.publisher;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.pojo.Response;

public interface FavoriteStockInterface {

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<FavoriteStockDto> add(@RequestBody FavoriteStockDto favoriteStockDto);

	@RequestMapping(value = "/{publisherId}/{stockIds}", method = RequestMethod.DELETE)
	Response<String> drop(@PathVariable(name = "publisherId") Long publisherId,
			@RequestParam(name = "stockIds") String stockIds);

	@RequestMapping(value = "/{publisherId}/top/{stockIds}", method = RequestMethod.PUT)
	Response<String> top(@PathVariable(name = "publisherId") Long publisherId,
			@RequestParam(name = "stockIds") String stockIds);

	@RequestMapping(value = "/{publisherId}/lists", method = RequestMethod.GET)
	Response<List<FavoriteStockDto>> listsByPublisherId(@PathVariable(name = "publisherId") Long publisherId);
	
	@RequestMapping(value = "/{publisherId}/listsStockId", method = RequestMethod.GET)
	Response<List<Long>> listsStockId(@PathVariable(name = "publisherId") Long publisherId);

}