package com.waben.stock.datalayer.publisher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.publisher.entity.Publisher;
import com.waben.stock.datalayer.publisher.service.PublisherService;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PublisherQuery;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;

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
	public Response<PageInfo<PublisherDto>> pages(@RequestBody PublisherQuery publisherQuery) {
		Page<Publisher> pages = publisherService.pages(publisherQuery);
		PageInfo<PublisherDto> result = new PageInfo<>(pages, PublisherDto.class);
		return new Response<>(result);
	}

	@Override
	public Response<PublisherDto> fetchById(@PathVariable Long id) {
		logger.info("获取发布策略人信息:{}", id);
		return new Response<>(
				CopyBeanUtils.copyBeanProperties(PublisherDto.class, publisherService.findById(id), false));
	}

	@Override
	public Response<PublisherDto> fetchByPhone(@PathVariable String phone) {
		return new Response<>(
				CopyBeanUtils.copyBeanProperties(PublisherDto.class, publisherService.findByPhone(phone), false));
	}

	@Override
	public Response<PublisherDto> register(String phone, String password, String promoter) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(PublisherDto.class,
				publisherService.register(phone, password, promoter), false));
	}

	@Override
	public Response<PublisherDto> modifyPassword(@PathVariable String phone, String password) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(PublisherDto.class,
				publisherService.modifyPassword(phone, password), false));
	}

	@Override
	public Response<Integer> promotionCount(@PathVariable Long id) {
		return new Response<>(publisherService.promotionCount(id));
	}

	@Override
	public Response<PageInfo<PublisherDto>> pagePromotionUser(@PathVariable Long id, int page, int size) {
		Page<Publisher> pageData = publisherService.pagePromotionUser(id, page, size);
		PageInfo<PublisherDto> result = PageToPageInfo.pageToPageInfo(pageData, PublisherDto.class);
		return new Response<>(result);
	}

}
