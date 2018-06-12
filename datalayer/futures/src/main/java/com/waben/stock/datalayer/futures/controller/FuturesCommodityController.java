package com.waben.stock.datalayer.futures.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.entity.FuturesCommodity;
import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;
import com.waben.stock.datalayer.futures.entity.FuturesExchange;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.service.FuturesCommodityService;
import com.waben.stock.datalayer.futures.service.FuturesContractService;
import com.waben.stock.datalayer.futures.service.FuturesCurrencyRateService;
import com.waben.stock.datalayer.futures.service.FuturesExchangeService;
import com.waben.stock.datalayer.futures.service.FuturesOrderService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.admin.futures.FuturesCommodityAdminDto;
import com.waben.stock.interfaces.dto.admin.futures.FuturesTradeTimeDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesCommodityAdminQuery;
import com.waben.stock.interfaces.service.futures.FuturesCommodityInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/commodity")
@Api(description = "期货品种接口列表")
public class FuturesCommodityController implements FuturesCommodityInterface {

	@Autowired
	private FuturesCommodityService commodityService;
	
	@Autowired
	private FuturesContractService contractService;
	
	@Autowired
	private FuturesExchangeService exchangeService;
	
	@Autowired
	private FuturesCurrencyRateService rateService;
	
	@Autowired
	private FuturesOrderService orderService;
	
	@Override
	public Response<PageInfo<FuturesCommodityAdminDto>> pagesAdmin(@RequestBody FuturesCommodityAdminQuery query) {
		Page<FuturesCommodity> page = commodityService.pages(query);
		PageInfo<FuturesCommodityAdminDto> result = PageToPageInfo.pageToPageInfo(page, FuturesCommodityAdminDto.class);
		
		if(result!=null && result.getContent()!=null){
			for(int i=0;i<result.getContent().size();i++){
				FuturesCommodity commodity = page.getContent().get(i);
				result.getContent().get(i).setExchangcode(commodity.getExchange().getCode());
				result.getContent().get(i).setExchangename(commodity.getExchange().getName());
				result.getContent().get(i).setExchangeType(commodity.getExchange().getExchangeType());
				result.getContent().get(i).setExchangeId(commodity.getExchange().getId());
			}
		}
		return new Response<>(result);
	}

	@Override
	public Response<FuturesCommodityAdminDto> save(@RequestBody FuturesCommodityAdminDto dto) {
		FuturesCommodity commodity = CopyBeanUtils.copyBeanProperties(FuturesCommodity.class, dto, false);
		FuturesExchange exchange = exchangeService.findById(dto.getExchangeId());
		commodity.setExchange(exchange);
		commodity.setEnable(false);
		commodity.setUpdateTime(new Date());
		FuturesCommodity result = commodityService.save(commodity);
		FuturesCommodityAdminDto response = CopyBeanUtils.copyBeanProperties(result, new FuturesCommodityAdminDto(), false);
		
		if(response!=null){
			response.setExchangcode(exchange.getCode());
			response.setExchangename(exchange.getName());
			response.setExchangeId(exchange.getId());
			response.setExchangeType(exchange.getExchangeType());
			
			//查询汇率
			FuturesCurrencyRate rate = rateService.findByCurrency(response.getCurrency());
			if(rate!=null){
				response.setRate(rate.getRate());
			}
		}
		return new Response<>(response);
	}

	@Override
	public Response<FuturesCommodityAdminDto> modify(@RequestBody FuturesCommodityAdminDto dto) {
		FuturesCommodity commodity = CopyBeanUtils.copyBeanProperties(FuturesCommodity.class, dto, false);
		
		FuturesExchange exchange = exchangeService.findById(dto.getExchangeId());
		commodity.setExchange(exchange);
		commodity.setEnable(false);
		commodity.setUpdateTime(new Date());
		FuturesCommodity result = commodityService.modify(commodity);
		FuturesCommodityAdminDto response = CopyBeanUtils.copyBeanProperties(result, new FuturesCommodityAdminDto(), false);
		
		if(response!=null){
			response.setExchangcode(exchange.getCode());
			response.setExchangename(exchange.getName());
			response.setExchangeId(exchange.getId());
			response.setExchangeType(exchange.getExchangeType());
			
			//查询汇率
			FuturesCurrencyRate rate = rateService.findByCurrency(response.getCurrency());
			if(rate!=null){
				response.setRate(rate.getRate());
			}
		}
		return new Response<>(response);
	}

	@Override
	public Response<String> deleteCommodity(@PathVariable("id") Long id) {
		List<FuturesContract> result = contractService.findByCommodity(id);
		for(int i=0;i<result.size();i++){
			contractService.deleteContract(result.get(i).getId());
		}
		commodityService.delete(id);
		return new Response<>("1");
	}

	@Override
	public Response<FuturesTradeTimeDto> queryTradeTime(@PathVariable("id") Long id) {
		FuturesCommodity commodity = commodityService.retrieve(id);
		FuturesTradeTimeDto result = CopyBeanUtils.copyBeanProperties(commodity, new FuturesTradeTimeDto(), false);
		result.setCommodityId(commodity.getId());
		return new Response<>(result);
	}

	@Override
	public Response<FuturesTradeTimeDto> saveAndModify(@RequestBody FuturesTradeTimeDto dto) {
		FuturesCommodity commodity = commodityService.retrieve(dto.getCommodityId());
		commodity.setFriTradeTime(dto.getFriTradeTime());
		commodity.setFriTradeTimeDesc(dto.getFriTradeTimeDesc());
		
		commodity.setMonTradeTime(dto.getMonTradeTime());
		commodity.setMonTradeTimeDesc(dto.getMonTradeTimeDesc());
		commodity.setSatTradeTime(dto.getSatTradeTime());
		commodity.setSatTradeTimeDesc(dto.getSatTradeTimeDesc());
		commodity.setSunTradeTime(dto.getSunTradeTime());
		commodity.setSunTradeTimeDesc(dto.getSunTradeTimeDesc());
		commodity.setThuTradeTime(dto.getThuTradeTime());
		commodity.setThuTradeTimeDesc(dto.getThuTradeTimeDesc());
		commodity.setTueTradeTime(dto.getTueTradeTime());
		commodity.setTueTradeTimeDesc(dto.getTueTradeTimeDesc());
		
		commodity.setWedTradeTime(dto.getWedTradeTime());
		commodity.setWedTradeTimeDesc(dto.getWedTradeTimeDesc());
		commodity.setUpdateTime(new Date());
		commodityService.modify(commodity);
		return new Response<>(dto);
	}

	@Override
	public Response<String> isCurrency(Long id) {
		FuturesCommodity commodity = commodityService.retrieve(id);
		boolean isCurrency = commodity.getEnable();
		List<FuturesContract> list = contractService.findByCommodity(commodity.getId());
		if(!isCurrency){
			for(FuturesContract contract : list){
				if(contract.getEnable()){
					isCurrency = true;
				}
			}
			if(isCurrency){
				commodity.setEnable(isCurrency);
			}else{
				throw new ServiceException(ExceptionConstant.CONTRACTTERM_ISCURRENT_EXCEPTION);
			}
		}else{
			List<Long> contractIds = new ArrayList<Long>();
			for(FuturesContract contract : list){
				contractIds.add(contract.getId());
			}
			List<FuturesOrder> order = orderService.findByContractId(contractIds);
			if(order.size()>0){
				throw new ServiceException(ExceptionConstant.CONTRACTTERM_ORDER_OCCUPIED_EXCEPTION);
			}
		}
		return new Response<>("响应成功");
	}

}
