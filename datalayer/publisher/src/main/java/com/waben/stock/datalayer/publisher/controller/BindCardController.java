package com.waben.stock.datalayer.publisher.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.publisher.service.BindCardService;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.publisher.BindCardInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * 绑卡 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/bindCard")
public class BindCardController implements BindCardInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BindCardService bindCardService;

	@Override
	public Response<BindCardDto> addBankCard(BindCardDto bindCardDto) {
		return new Response<>(
				CopyBeanUtils
						.copyBeanProperties(BindCardDto.class,
								bindCardService.save(bindCardDto.getPublisherId(), bindCardDto.getName(),
										bindCardDto.getIdCard(), bindCardDto.getPhone(), bindCardDto.getBankCard()),
								false));
	}

	@Override
	public Response<List<BindCardDto>> listsByPublisherId(Long publisherId) {
		return new Response<>(
				CopyBeanUtils.copyListBeanPropertiesToList(bindCardService.list(publisherId), BindCardDto.class));
	}

}
