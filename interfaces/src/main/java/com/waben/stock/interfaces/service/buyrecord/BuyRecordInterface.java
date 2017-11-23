package com.waben.stock.interfaces.service.buyrecord;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;

public interface BuyRecordInterface {

	@RequestMapping(value = "/addBuyRecord", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<BuyRecordDto> addBuyRecord(@RequestBody BuyRecordDto buyRecordDto);
	
}
