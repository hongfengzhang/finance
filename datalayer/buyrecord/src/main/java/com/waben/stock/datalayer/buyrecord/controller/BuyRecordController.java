package com.waben.stock.datalayer.buyrecord.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.buyrecord.service.BuyRecordService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;

/**
 * 点买记录 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/buyRecord")
public class BuyRecordController implements BuyRecordInterface {

	@Autowired
	private BuyRecordService buyRecordService;

	@Override
	public Response<BuyRecordDto> addBuyRecord(@RequestBody BuyRecordDto buyRecordDto) {
		return new Response<>(buyRecordService.addBuyRecord(buyRecordDto));
	}

}
