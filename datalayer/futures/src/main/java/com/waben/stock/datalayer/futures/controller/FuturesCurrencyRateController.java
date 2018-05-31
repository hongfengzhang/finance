package com.waben.stock.datalayer.futures.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;
import com.waben.stock.datalayer.futures.service.FuturesCurrencyRateService;
import com.waben.stock.interfaces.dto.futures.FuturesCurrencyRateDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.futures.FuturesCurrencyRateInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/currencyRate")
@Api(description = "期货汇率接口列表")
public class FuturesCurrencyRateController implements FuturesCurrencyRateInterface {

	@Autowired
	private FuturesCurrencyRateService currencyRateService;

	@Override
	public Response<FuturesCurrencyRateDto> addCurrencyRate(@RequestBody FuturesCurrencyRateDto dto) {
		FuturesCurrencyRate rate = CopyBeanUtils.copyBeanProperties(FuturesCurrencyRate.class, dto, false);
		FuturesCurrencyRate result = currencyRateService.addCurrencyRate(rate);
		FuturesCurrencyRateDto resultDto = CopyBeanUtils.copyBeanProperties(result, new FuturesCurrencyRateDto(),
				false);
		return new Response<>(resultDto);
	}

	@Override
	public Response<FuturesCurrencyRateDto> modifyCurrencyRate(@RequestBody FuturesCurrencyRateDto dto) {
		FuturesCurrencyRate rate = CopyBeanUtils.copyBeanProperties(FuturesCurrencyRate.class, dto, false);
		FuturesCurrencyRate result = currencyRateService.modifyCurrencyRate(rate);
		FuturesCurrencyRateDto resultDto = CopyBeanUtils.copyBeanProperties(result, new FuturesCurrencyRateDto(),
				false);
		return new Response<>(resultDto);
	}

	@Override
	public void deleteCurrencyRate(@PathVariable Long id) {
		currencyRateService.deleteCurrencyRate(id);

	}

	@Override
	public Response<PageInfo<FuturesCurrencyRateDto>> list() {
		Page<FuturesCurrencyRate> page = currencyRateService.list();
		PageInfo<FuturesCurrencyRateDto> result = PageToPageInfo.pageToPageInfo(page, FuturesCurrencyRateDto.class);
		return new Response<>(result);
	}

	@Override
	public Response<FuturesCurrencyRateDto> findByCurrency(String currency) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(FuturesCurrencyRateDto.class,
				currencyRateService.findByCurrency(currency), false));
	}

}
