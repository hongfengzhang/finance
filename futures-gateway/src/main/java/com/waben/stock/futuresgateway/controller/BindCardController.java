package com.waben.stock.futuresgateway.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.futuresgateway.entity.BindCard;
import com.waben.stock.futuresgateway.pojo.Response;
import com.waben.stock.futuresgateway.service.BindCardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 绑卡 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/bindCard")
@Api(description = "绑卡接口列表")
public class BindCardController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public BindCardService bindCardService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取绑卡")
	public Response<BindCard> fetchById(@PathVariable Long id) {
		return new Response<>(bindCardService.getBindCardInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取绑卡分页数据")
	public Response<Page<BindCard>> bindCards(int page, int limit) {
		return new Response<>((Page<BindCard>) bindCardService.bindCards(page, limit));
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取绑卡列表")
	public Response<List<BindCard>> list() {
		return new Response<>(bindCardService.list());
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加绑卡", hidden = true)
	public Response<BindCard> addition(BindCard bindCard) {
		return new Response<>(bindCardService.addBindCard(bindCard));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改绑卡", hidden = true)
	public Response<BindCard> modification(BindCard bindCard) {
		return new Response<>(bindCardService.modifyBindCard(bindCard));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除绑卡", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		bindCardService.deleteBindCard(id);
		return new Response<Long>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除绑卡（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		bindCardService.deleteBindCards(ids);
		return new Response<Boolean>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取绑卡列表(后台管理)", hidden = true)
	public Response<List<BindCard>> adminList() {
		return new Response<>(bindCardService.list());
	}

}
