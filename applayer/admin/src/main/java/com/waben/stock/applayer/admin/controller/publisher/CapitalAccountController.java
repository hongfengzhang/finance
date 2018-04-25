package com.waben.stock.applayer.admin.controller.publisher;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.admin.business.publisher.CapitalAccountBusiness;
import com.waben.stock.interfaces.dto.admin.publisher.CapitalAccountAdminDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.publisher.CapitalAccountAdminQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 资金账户 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/capitalAccount")
@Api(description = "资金账户")
public class CapitalAccountController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CapitalAccountBusiness business;

	@GetMapping("/pages")
	@ApiOperation(value = "查询资金账户")
	public Response<PageInfo<CapitalAccountAdminDto>> pages(CapitalAccountAdminQuery query) {
		return new Response<>(business.adminPagesByQuery(query));
	}

	@PutMapping("/state/{id}/{state}")
	@ApiImplicitParams({@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "账户id", required = true),@ApiImplicitParam(paramType = "path", dataType = "Integer", name = "state", value = "账户状态｛正常（1），冻结（2）｝", required = true)})
	@ApiOperation(value = "设置资金账户状态")
	public Response<CapitalAccountDto> modifyState(@PathVariable Long id, @PathVariable Integer state) {
		CapitalAccountDto response = business.revisionState(id, state);
		return new Response<>(response);
	}

	@PutMapping("/account/{id}/{availableBalance}")
	@ApiImplicitParams({@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "账户id", required = true),@ApiImplicitParam(paramType = "path", dataType = "BigDecimal", name = "availableBalance", value = "帐号可用余额", required = true)})
	@ApiOperation(value = "设置资金账户可用余额")
	public Response<CapitalAccountDto> modifyAccount(@PathVariable Long id, @PathVariable BigDecimal availableBalance) {
		CapitalAccountDto response = business.revisionAccount(id, availableBalance);
		return new Response<>(response);
	}
}
