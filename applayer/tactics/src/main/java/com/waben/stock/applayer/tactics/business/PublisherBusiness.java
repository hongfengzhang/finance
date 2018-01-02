package com.waben.stock.applayer.tactics.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.service.PublisherService;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 发布人 Business
 * 
 * @author luomengan
 *
 */
@Service
public class PublisherBusiness {

	@Autowired
	private PublisherService service;

	public PublisherDto findById(Long publisherId) {
		Response<PublisherDto> response = service.fetchById(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
