package com.waben.stock.interfaces.service.publisher;

import java.math.BigDecimal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;

public interface CapitalFlowInterface {

	/**
	 * 分页查询资金流水
	 * 
	 * @param query
	 *            查询条件
	 * @return 资金流水
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<CapitalFlowDto>> pagesByQuery(@RequestBody CapitalFlowQuery query);
	
	/**
	 * 获取推广赚取的总佣金
	 * 
	 * @param publisherId
	 *            发布人ID
	 * @return 推广赚取的总佣金
	 */
	@RequestMapping(value = "/{publisherId}/promotion/amount", method = RequestMethod.GET)
	Response<BigDecimal> promotionTotalAmount(@PathVariable("publisherId") Long publisherId);
	
	/**
	 * 根据流水ID获取流水信息
	 * @param capitalFlowId
	 * @return
	 */
	@RequestMapping(value = "/{capitalFlowId}/view/", method = RequestMethod.GET)
	Response<CapitalFlowDto> fetchById(@PathVariable("capitalFlowId") Long capitalFlowId);

}
