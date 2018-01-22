package com.waben.stock.interfaces.service.publisher;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PublisherQuery;

/**
 * @author Created by yuyidi on 2017/11/12.
 * @desc
 */
public interface PublisherInterface {

	@RequestMapping(value = "/pages", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<PublisherDto>> pages(@RequestBody PublisherQuery publisherQuery);

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Response<PublisherDto> fetchById(@PathVariable("id") Long id);

	@RequestMapping(value = "/phone/{phone}", method = RequestMethod.GET)
	Response<PublisherDto> fetchByPhone(@PathVariable("phone") String phone);

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	Response<PublisherDto> register(@RequestParam(name = "phone") String phone,
			@RequestParam(name = "password") String password, @RequestParam(name = "promoter") String promoter,
			@RequestParam(name = "endType", required = false) String endType);
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PublisherDto> modify(@RequestBody PublisherDto publisherDto);

	@RequestMapping(value = "/{phone}/modifyPassword", method = RequestMethod.PUT)
	Response<PublisherDto> modifyPassword(@PathVariable("phone") String phone,
			@RequestParam(name = "password") String password);
	
	@RequestMapping(value = "/{id}/headportrait", method = RequestMethod.PUT)
	Response<PublisherDto> modiyHeadportrait(@PathVariable("id") Long id,
			@RequestParam(name = "headPortrait") String headPortrait);

	@RequestMapping(value = "/{id}/promotion/count", method = RequestMethod.GET)
	Response<Integer> promotionCount(@PathVariable("id") Long id);

	@RequestMapping(value = "/{id}/promotion/userpages", method = RequestMethod.GET)
	Response<PageInfo<PublisherDto>> pagePromotionUser(@PathVariable("id") Long id,
			@RequestParam(name = "page") int page, @RequestParam(name = "size") int size);

}
