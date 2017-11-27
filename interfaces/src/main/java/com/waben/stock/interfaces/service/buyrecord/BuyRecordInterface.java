package com.waben.stock.interfaces.service.buyrecord;

import java.math.BigDecimal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;

public interface BuyRecordInterface {

	/**
	 * 添加点买记录，此时状态为“发布”
	 * 
	 * @param buyRecordDto
	 *            点买记录
	 * @return 点买记录
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<BuyRecordDto> addBuyRecord(@RequestBody BuyRecordDto buyRecordDto);

	/**
	 * 投资人买入股票，此时状态为“持仓中”
	 * 
	 * @param id
	 *            点买记录id
	 * @param delegateNumber
	 *            委托编号，证券账号购买股票后的交易编号
	 * @param buyingPrice
	 *            买入价格
	 * @param numberOfStrand
	 *            持股数
	 * @return 点买记录
	 */
	@RequestMapping(value = "/buyinto/{id}", method = RequestMethod.PUT)
	Response<BuyRecordDto> buyInto(@PathVariable("id") Long id, String delegateNumber, BigDecimal buyingPrice,
			Integer numberOfStrand);

}
