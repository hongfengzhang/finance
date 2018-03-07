package com.waben.stock.datalayer.promotion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.promotion.pojo.bean.PromotionBuyRecordBean;
import com.waben.stock.datalayer.promotion.pojo.query.PromotionBuyRecordQuery;
import com.waben.stock.datalayer.promotion.service.PromotionBuyRecordService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 推广渠道产生的策略 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/promotionBuyRecord")
@Api(description = "推广渠道产生的策略接口列表")
public class PromotionBuyRecordController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public PromotionBuyRecordService promotionBuyRecordService;

	@PostMapping("/adminPage")
	@ApiOperation(value = "获取推广渠道产生的策略分页数据(后台管理)")
	public Response<Page<PromotionBuyRecordBean>> adminPage(@RequestBody PromotionBuyRecordQuery query) {
		return new Response<>((Page<PromotionBuyRecordBean>) promotionBuyRecordService.pagesByQuery(query));
	}

}
