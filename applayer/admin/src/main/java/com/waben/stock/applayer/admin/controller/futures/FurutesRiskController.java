package com.waben.stock.applayer.admin.controller.futures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.admin.business.futures.FuturesCurrencyRateBusiness;
import com.waben.stock.interfaces.dto.futures.FuturesCurrencyRateDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/futuresConRisk")
@Api(description="期货风控")
public class FurutesRiskController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FuturesCurrencyRateBusiness business;
	
	@RequestMapping(value = "/currencyRate/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增汇率")
    public Response<FuturesCurrencyRateDto> save(FuturesCurrencyRateDto query){
		FuturesCurrencyRateDto result = business.save(query);
        return new Response<>(result);
    }
	
	@RequestMapping(value = "/currencyRate/modify", method = RequestMethod.POST)
    @ApiOperation(value = "修改汇率")
    public Response<FuturesCurrencyRateDto> modify(FuturesCurrencyRateDto exchangeDto){
		FuturesCurrencyRateDto result = business.modify(exchangeDto);
        return new Response<>(result);
    }
	
	@PutMapping(value = "/currencyRate/delete/{id}")
    @ApiOperation(value = "删除汇率")
    public Response<Integer> delete(@PathVariable("id") Long id){
        business.deleteCurrencyRate(id);
        return new Response<>(1);
    }
	
	@RequestMapping(value = "/currencyRate/listAll", method = RequestMethod.POST)
    @ApiOperation(value = "查询所有汇率信息")
	public Response<PageInfo<FuturesCurrencyRateDto>> list(){
		return new Response<>(business.list());
	}
}
