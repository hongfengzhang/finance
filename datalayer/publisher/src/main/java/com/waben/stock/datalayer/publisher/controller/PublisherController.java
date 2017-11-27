package com.waben.stock.datalayer.publisher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.publisher.service.PublisherService;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * @author Created by yuyidi on 2017/11/5.
 * @desc
 */
@RestController
@RequestMapping("/publisher")
public class PublisherController implements PublisherInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PublisherService publisherService;

	@Override
	public Response<PublisherDto> fetchById(Long id) {
		logger.info("获取发布策略人信息:{}", id);
		return new Response<>(
				CopyBeanUtils.copyBeanProperties(PublisherDto.class, publisherService.findById(id), false));
	}

	@Override
	public Response<PublisherDto> fetchByPhone(String phone) {
		return new Response<>(
				CopyBeanUtils.copyBeanProperties(PublisherDto.class, publisherService.findByPhone(phone), false));
	}

	@Override
	public Response<PublisherDto> register(String phone, String password) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(PublisherDto.class,
				publisherService.register(phone, password), false));
	}

	@Override
	public Response<PublisherDto> modifyPassword(String phone, String password) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(PublisherDto.class,
				publisherService.modifyPassword(phone, password), false));
	}

}
