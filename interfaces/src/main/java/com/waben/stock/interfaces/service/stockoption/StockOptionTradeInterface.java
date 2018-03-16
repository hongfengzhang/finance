package com.waben.stock.interfaces.service.stockoption;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeUserQuery;

import java.util.List;

public interface StockOptionTradeInterface {

	/**
	 * 添加期权申购，此时状态为“待确认”
	 *
	 * @param stockOptionTradeDto
	 *            期权交易信息
	 * @return 期权交易信息
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<StockOptionTradeDto> add(@RequestBody StockOptionTradeDto stockOptionTradeDto);

	@RequestMapping(value = "/userpages", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<StockOptionTradeDto>> pagesByUserQuery(@RequestBody StockOptionTradeUserQuery query);
	
	/**
	 * 用户申请提前行权，此时状态为“申请行权”
	 *
	 * @param publisherId
	 *            发布人ID
	 * @param id
	 *            期权交易id
	 * @return 期权交易
	 */
	@RequestMapping(value = "/{publisherId}/userright/{id}", method = RequestMethod.PUT)
	Response<StockOptionTradeDto> userRight(@PathVariable("publisherId") Long publisherId, @PathVariable("id") Long id);

	/**
	 * 分页查询期权申购信息
	 *
	 * @param query
	 *            查询条件
	 * @return 结算记录
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<StockOptionTradeDto>> pagesByQuery(@RequestBody StockOptionTradeQuery query);

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Response<StockOptionTradeDto> fetchById(@PathVariable("id") Long id);

	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	Response<StockOptionTradeDto> modify(@PathVariable("id") Long id);

    @RequestMapping(value = "/settlement/{id}", method = RequestMethod.PUT)
    Response<StockOptionTradeDto> settlement(@PathVariable("id") Long id);

	@RequestMapping(value = "/success/{id}", method = RequestMethod.PUT)
	Response<StockOptionTradeDto> success(@PathVariable("id") Long id);

	@RequestMapping(value = "/fail/{id}", method = RequestMethod.PUT)
	Response<StockOptionTradeDto> fail(@PathVariable("id") Long id);

	@RequestMapping(value = "/exercise/{id}", method = RequestMethod.PUT)
	Response<StockOptionTradeDto> exercise(@PathVariable("id") Long id);

	@RequestMapping(value = "/state/{state}", method = RequestMethod.GET)
	Response<List<StockOptionTradeDto>> stockOptionsWithState(@PathVariable("state") Integer state);
}
