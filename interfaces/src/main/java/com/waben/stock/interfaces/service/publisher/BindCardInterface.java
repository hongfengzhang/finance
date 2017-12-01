package com.waben.stock.interfaces.service.publisher;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.pojo.Response;

public interface BindCardInterface {

	/**
	 * 绑卡
	 * 
	 * @param bindCardDto
	 *            绑卡信息
	 * @return 绑卡信息
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<BindCardDto> addBankCard(@RequestBody BindCardDto bindCardDto);

	/**
	 * 获取某个发布人的绑卡列表
	 * 
	 * @param publisherId
	 *            发布人ID
	 * @return 绑卡列表
	 */
	@RequestMapping(value = "/{publisherId}/lists", method = RequestMethod.GET)
	Response<List<BindCardDto>> listsByPublisherId(@PathVariable("publisherId") Long publisherId);

}
