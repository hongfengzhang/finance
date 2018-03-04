package com.waben.stock.datalayer.stockoption.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.stockoption.entity.StockOptionCycle;
import com.waben.stock.datalayer.stockoption.service.StockOptionCycleService;
import com.waben.stock.interfaces.dto.stockoption.StockOptionCycleDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.stockoption.StockOptionCycleInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * 期权周期
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/stockoptioncycle")
public class StockOptionCycleController implements StockOptionCycleInterface {
	
	@Autowired
	private StockOptionCycleService service;

	@Override
	public Response<List<StockOptionCycleDto>> lists() {
		List<StockOptionCycle> cycles = service.lists();
        List<StockOptionCycleDto> result = CopyBeanUtils.copyListBeanPropertiesToList(cycles, StockOptionCycleDto.class);
        return new Response<>(result);
	}
	
}
