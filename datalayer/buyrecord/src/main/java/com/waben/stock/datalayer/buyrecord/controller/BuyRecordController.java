package com.waben.stock.datalayer.buyrecord.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.buyrecord.service.BuyRecordService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * 点买记录 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/buyRecord")
public class BuyRecordController implements BuyRecordInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BuyRecordService buyRecordService;

	@Override
	public Response<BuyRecordDto> addBuyRecord(@RequestBody BuyRecordDto buyRecordDto) {
		return new Response<>(
				CopyBeanUtils.copyBeanProperties(BuyRecordDto.class, buyRecordService.save(buyRecordDto), false));
	}

	@Override
	public Response<BuyRecordDto> buyInto(Long id, String delegateNumber, BigDecimal buyingPrice,
			Integer numberOfStrand) {
		// TODO Auto-generated method stub
		return null;
	}

}
