package com.waben.stock.datalayer.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.manage.entity.PutForward;
import com.waben.stock.datalayer.manage.service.PutForwardService;
import com.waben.stock.interfaces.dto.admin.futures.PutForwardDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.manage.PutForwardInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/putforward")
@Api(description = "提现设置接口列表")
public class PutForwardController implements PutForwardInterface {
	
	@Autowired
	private PutForwardService service;
	

	@Override
	public Response<PageInfo<PutForwardDto>> pages() {
		Page<PutForward> page = service.pages();
		PageInfo<PutForwardDto> pages = PageToPageInfo.pageToPageInfo(page, PutForwardDto.class);
		return new Response<>(pages);
	}

	@Override
	public PutForwardDto saveAndModify(@RequestBody PutForwardDto dto) {
		PutForward put = CopyBeanUtils.copyBeanProperties(PutForward.class, dto, false);
		PutForward result = service.saveAndModify(put);
		PutForwardDto page = CopyBeanUtils.copyBeanProperties(result, new PutForwardDto(), false);
		return page;
	}

}
