package com.waben.stock.datalayer.futures.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.service.FuturesOrderService;
import com.waben.stock.interfaces.dto.futures.FuturesOrderDto;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesOrderType;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.futures.FuturesOrderQuery;
import com.waben.stock.interfaces.service.futures.FuturesOrderInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/order")
@Api(description = "期货订单接口列表")
public class FuturesOrderController implements FuturesOrderInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesOrderService futuresOrderService;

	@Override
	public Response<PageInfo<FuturesOrderDto>> pagesOrder(@RequestBody FuturesOrderQuery orderQuery) {
		Page<FuturesOrder> page = futuresOrderService.pagesOrder(orderQuery);
		PageInfo<FuturesOrderDto> result = PageToPageInfo.pageToPageInfo(page, FuturesOrderDto.class);
		return new Response<>(result);
	}

	@Override
	public Response<FuturesOrderDto> addOrder(@RequestBody FuturesOrderDto futuresOrderDto) {
		FuturesOrder order = CopyBeanUtils.copyBeanProperties(FuturesOrder.class, futuresOrderDto, false);
		FuturesContract contract = CopyBeanUtils.copyBeanProperties(FuturesContract.class,
				futuresOrderDto.getContract(), false);
		order.setContract(contract);
		return new Response<>(
				CopyBeanUtils.copyBeanProperties(FuturesOrderDto.class, futuresOrderService.save(order), false));
	}

	@Override
	public Response<FuturesOrderDto> editOrder(@PathVariable Long id, FuturesOrderState state) {
		FuturesOrder order = futuresOrderService.editOrder(id, state);
		return new Response<>(CopyBeanUtils.copyBeanProperties(FuturesOrderDto.class, order, false));
	}

	@Override
	public Response<Integer> countOrderType(Long contractId, FuturesOrderType orderType) {
		return new Response<>(futuresOrderService.countOrderType(contractId, orderType));
	}

	@Override
	public Response<Integer> sumByListOrderContractIdAndPublisherId(@PathVariable Long contractId,
			@PathVariable Long publisherId) {
		return new Response<>(futuresOrderService.sumByListOrderContractIdAndPublisherId(contractId, publisherId));
	}

	@Override
	public Response<List<FuturesOrderDto>> getListFuturesOrderPositionByPublisherId(Long publisherId) {
		List<FuturesOrderDto> orderPositionList = CopyBeanUtils.copyListBeanPropertiesToList(
				futuresOrderService.getListFuturesOrderPositionByPublisherId(publisherId), FuturesOrderDto.class);
		return new Response<>(orderPositionList);
	}

	@Override
	public Response<BigDecimal> settlementOrderPositionByPublisherId(Long publisherId) {
		return new Response<>(futuresOrderService.settlementOrderPositionByPublisherId(publisherId));
	}

	@Override
	public Response<List<FuturesOrderDto>> getListFuturesOrderEntrustByPublisherId(Long publisherId) {
		List<FuturesOrderDto> orderEntrustList = CopyBeanUtils.copyListBeanPropertiesToList(
				futuresOrderService.getListFuturesOrderEntrustByPublisherId(publisherId), FuturesOrderDto.class);
		return new Response<>(orderEntrustList);
	}

	@Override
	public Response<BigDecimal> settlementOrderEntrustByPublisherId(Long publisherId) {
		return new Response<>(futuresOrderService.settlementOrderEntrustByPublisherId(publisherId));
	}

	@Override
	public Response<List<FuturesOrderDto>> getListFuturesOrderUnwindByPublisherId(Long publisherId) {
		List<FuturesOrderDto> orderUnwindList = CopyBeanUtils.copyListBeanPropertiesToList(
				futuresOrderService.getListFuturesOrderUnwindByPublisherId(publisherId), FuturesOrderDto.class);
		return new Response<>(orderUnwindList);
	}

	@Override
	public Response<BigDecimal> settlementOrderUnwindByPublisherId(Long publisherId) {
		return new Response<>(futuresOrderService.settlementOrderUnwindByPublisherId(publisherId));
	}

}
