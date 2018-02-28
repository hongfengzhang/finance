package com.waben.stock.datalayer.promotion.controller;

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

import com.waben.stock.datalayer.promotion.entity.BindingCard;
import com.waben.stock.datalayer.promotion.service.BindingCardService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 绑卡 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/bindingCard")
@Api(description = "绑卡接口列表")
public class BindingCardController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public BindingCardService bindingCardService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取绑卡")
	public Response<BindingCard> fetchById(@PathVariable Long id) {
		return new Response<>(bindingCardService.getBindingCardInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取绑卡分页数据")
	public Response<Page<BindingCard>> bindingCards(int page, int limit) {
		return new Response<>((Page<BindingCard>) bindingCardService.bindingCards(page, limit));
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取绑卡列表")
	public Response<List<BindingCard>> list() {
		return new Response<>(bindingCardService.list());
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加绑卡", hidden = true)
	public Response<BindingCard> addition(BindingCard bindingCard) {
		return new Response<>(bindingCardService.addBindingCard(bindingCard));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改绑卡", hidden = true)
	public Response<BindingCard> modification(BindingCard bindingCard) {
		return new Response<>(bindingCardService.modifyBindingCard(bindingCard));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除绑卡", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		bindingCardService.deleteBindingCard(id);
		return new Response<Long>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除绑卡（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		bindingCardService.deleteBindingCards(ids);
		return new Response<Boolean>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取绑卡列表(后台管理)", hidden = true)
	public Response<List<BindingCard>> adminList() {
		return new Response<>(bindingCardService.list());
	}

}
