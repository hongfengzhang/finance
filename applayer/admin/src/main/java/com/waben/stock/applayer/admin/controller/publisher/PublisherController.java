package com.waben.stock.applayer.admin.controller.publisher;

import java.util.List;

import com.waben.stock.applayer.admin.security.SecurityUtil;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.waben.stock.applayer.admin.business.publisher.BindCardBusiness;
import com.waben.stock.applayer.admin.business.publisher.CapitalAccountBusiness;
import com.waben.stock.applayer.admin.business.publisher.PublisherBusiness;
import com.waben.stock.interfaces.dto.admin.publisher.CapitalAccountAdminDto;
import com.waben.stock.interfaces.dto.admin.publisher.PublisherAdminDto;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.publisher.CapitalAccountAdminQuery;
import com.waben.stock.interfaces.pojo.query.admin.publisher.PublisherAdminQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 发布人 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/publisher")
@Api(description = "发布人")
public class PublisherController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PublisherBusiness business;

	@Autowired
	private CapitalAccountBusiness accountBusiness;

	@Autowired
	private BindCardBusiness bindCardBusiness;

	@GetMapping("/pages")
	@ApiOperation(value = "查询发布人")
	public Response<PageInfo<PublisherAdminDto>> pages(PublisherAdminQuery query) {
		return new Response<>(business.adminPagesByQuery(query));
	}

	@GetMapping("/detail/{id}")
	@ApiOperation(value = "查看发布人详情")
	public Response<CapitalAccountAdminDto> pages(@PathVariable Long id) {
		CapitalAccountAdminQuery query = new CapitalAccountAdminQuery();
		query.setPublisherId(id);
		PageInfo<CapitalAccountAdminDto> pageInfo = accountBusiness.adminPagesByQuery(query);
		if (pageInfo.getContent() != null && pageInfo.getContent().size() > 0) {
			return new Response<>(pageInfo.getContent().get(0));
		} else {
			return new Response<>();
		}
	}

	@GetMapping("/bindcard/lists/{id}")
	@ApiOperation(value = "查询绑卡列表")
	public Response<List<BindCardDto>> bindCardList(@PathVariable Long id) {
		return new Response<>(bindCardBusiness.listsByPublisherId(id));
	}

	@PostMapping("/defriend/{id}")
	@ApiOperation(value = "拉黑")
	@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "会员id", required = true)
	public Response<PublisherDto> defriend(@PathVariable Long id) {
		PublisherDto response = business.defriend(id);
		return new Response<>(response);
	}

	@PostMapping("/recover/{id}")
	@ApiOperation(value = "恢复")
	@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "会员id", required = true)
	public Response<PublisherDto> recover(@PathVariable Long id) {
		PublisherDto response = business.recover(id);
		return new Response<>(response);
	}

	@PutMapping("/{id}/{password}")
	@ApiImplicitParams({@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "会员id", required = true),@ApiImplicitParam(paramType = "path", dataType = "String", name = "password", value = "会员密码", required = true)})
	@ApiOperation(value = "修改密码")
	public Response<PublisherDto> password(@PathVariable Long id,@PathVariable String password) {
		PublisherDto response = business.password(id,password);
		return new Response<>(response);
	}


	@GetMapping("/{id}")
	@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "会员id", required = true)
	@ApiOperation(value = "通过会员id获取会员")
	public Response<PublisherDto> fetchById(@PathVariable Long id) {
		PublisherDto respone = business.findById(id);
		return new Response<>(respone);
	}
}
