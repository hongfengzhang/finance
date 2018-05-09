package com.waben.stock.applayer.promotion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.zxing.WriterException;
import com.waben.stock.applayer.promotion.business.BindCardBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.applayer.promotion.util.PoiUtil;
import com.waben.stock.applayer.promotion.util.QRCodeUtil;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.OrganizationDetailDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.OrganizationStaDto;
import com.waben.stock.interfaces.dto.organization.TreeNode;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.form.organization.OrganizationForm;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationQuery;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationStaQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 机构 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/organization")
@Api(description = "机构接口列表")
public class OrganizationController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public OrganizationBusiness business;

	@Autowired
	public BindCardBusiness bindCardBusiness;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@PreAuthorize("hasRole('ORG_MANAGE')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Response<OrganizationDto> addition(OrganizationForm orgForm) {
		return new Response<>(business.addition(orgForm));
	}

	@RequestMapping(value = "/adminPage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<PageInfo<OrganizationDto>> adminPage(@RequestBody OrganizationQuery query) {
		return new Response<>(business.adminPage(query));
	}

	@RequestMapping(value = "/pages", method = RequestMethod.POST)
	public Response<PageInfo<OrganizationDto>> pages(@RequestBody OrganizationQuery query) {
		// UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
		// if(!"admin".equals(userDto.getUsername())){
		// query.setParentId(userDto.getOrg().getId());
		// }
		return new Response<>(business.pages(query));
	}

	@RequestMapping(value = "/adminTree", method = RequestMethod.GET)
	public List<TreeNode> adminTree(Long orgId) {
		return business.adminTree(orgId);
	}

	@RequestMapping(value = "/listByParentId", method = RequestMethod.GET)
	public Response<List<OrganizationDto>> listByParentId(Long parentId) {
		return new Response<>(business.listByParentId(parentId));
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ApiOperation(value = "获取登录平台或代理商账号详细信息")
	public Response<OrganizationDetailDto> detail(Long orgId) {
		// OrganizationQuery query = new OrganizationQuery();
		// query.setOrgId(orgId);
		// query.setState("DETAIL");
		// PageInfo<OrganizationDetailDto> pageInfo =
		// business.adminAgentPageByQuery(query);
		// if (pageInfo.getContent() != null && pageInfo.getContent().size() >
		// 0) {
		// return new Response<>(pageInfo.getContent().get(0));
		// } else {
		// return new Response<>();
		// }
		return new Response<>(business.detail(orgId));
	}

	@RequestMapping(value = "/modifyName", method = RequestMethod.POST)
	@ApiOperation(value = "编辑代理商信息")
	public Response<OrganizationDto> modifyName(Long id, String name, String billCharge, String level) {
		return new Response<>(business.modifyName(id, name, billCharge, level));
	}

	@RequestMapping(value = "/{orgid}/bindcard", method = RequestMethod.GET)
	@ApiOperation(value = "获取绑卡信息")
	public Response<BindCardDto> fetchBindCard(@PathVariable("orgid") Long orgId) {
		return new Response<>(bindCardBusiness.getOrgBindCard(orgId));
	}

	@RequestMapping(value = "/{orgid}/bindcard", method = RequestMethod.POST)
	@ApiOperation(value = "编辑绑卡信息")
	public Response<BindCardDto> saveBindCard(@PathVariable("orgid") Long orgId, BindCardDto bindCardDto) {
		return new Response<>(bindCardBusiness.orgBindCard(orgId, bindCardDto));
	}

	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	@ApiOperation(value = "获取推广二维码")
	public Response<OrganizationDetailDto> qrcode(Long orgId) throws IOException, WriterException {
		OrganizationDetailDto dto = business.detail(orgId);
		Map<String, String> contentMap = Maps.newHashMap();
		contentMap.put("name", String.valueOf(dto.getName()));
		contentMap.put("code", String.valueOf(dto.getCode()));
		contentMap.put("state", String.valueOf(dto.getState()));
		String content = JSON.toJSONString(contentMap);
		return new Response<>(QRCodeUtil.create(content, 200, 200));
	}

	@RequestMapping(value = "/adminAgentPage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取代理商列表")
	public Response<PageInfo<OrganizationDetailDto>> adminAgentPage(@RequestBody OrganizationQuery query) {
		return new Response<>(business.adminAgentPageByQuery(query));
	}

	@RequestMapping(value = "/singleSta/{currentOrgId}", method = RequestMethod.GET)
	@ApiOperation(value = "单个代理商数据统计")
	public Response<OrganizationStaDto> singleSta(@PathVariable("currentOrgId") Long currentOrgId) {
		OrganizationStaQuery query = new OrganizationStaQuery();
		query.setCurrentOrgId(currentOrgId);
		query.setQueryType(1);
		query.setPage(0);
		query.setSize(1);
		PageInfo<OrganizationStaDto> pages = business.adminStaPageByQuery(query);
		OrganizationStaDto result = null;
		if (pages.getContent() != null && pages.getContent().size() > 0) {
			result = pages.getContent().get(0);
		}
		return new Response<>(result);
	}

	@RequestMapping(value = "/childrenSta/{currentOrgId}", method = RequestMethod.GET)
	@ApiOperation(value = "下级代理商数据统计")
	public Response<PageInfo<OrganizationStaDto>> childrenSta(@PathVariable("currentOrgId") Long currentOrgId,
			OrganizationStaQuery query) {
		query.setCurrentOrgId(currentOrgId);
		query.setQueryType(2);
		return new Response<>(business.adminStaPageByQuery(query));
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ApiOperation(value = "导出代理商数据")
	public void export(OrganizationQuery query, HttpServletResponse svrResponse) {
		query.setPage(0);
		query.setSize(Integer.MAX_VALUE);
		PageInfo<OrganizationDetailDto> result = business.adminAgentPageByQuery(query);
		File file = null;
		FileInputStream is = null;
		try {
			String fileName = "agnet_" + String.valueOf(System.currentTimeMillis());
			file = File.createTempFile(fileName, ".xls");
			List<String> columnDescList = columnDescList();
			List<List<String>> dataList = dataList(result.getContent());
			PoiUtil.writeDataToExcel("代理商数据", file, columnDescList, dataList);

			is = new FileInputStream(file);
			svrResponse.setContentType("application/vnd.ms-excel");
			svrResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
			IOUtils.copy(is, svrResponse.getOutputStream());
			svrResponse.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION, "导出代理商数据到excel异常：" + e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			if (file != null) {
				file.delete();
			}
		}
	}

	private List<List<String>> dataList(List<OrganizationDetailDto> content) {
		List<List<String>> result = new ArrayList<>();
		for (OrganizationDetailDto trade : content) {
			String state = "";
			if (trade.getState() != null) {
				state = trade.getState().getState();
			}
			List<String> data = new ArrayList<>();
			data.add(String.valueOf(trade.getId() == null ? "" : trade.getId()));
			data.add(trade.getCode() == null ? "" : trade.getCode());
			data.add(trade.getName() == null ? "" : trade.getName());
			data.add(String.valueOf(trade.getLevel() == null ? "" : (trade.getLevel() + "级")));
			data.add(String.valueOf(trade.getChildOrgCount() == null ? "" : trade.getChildOrgCount()));
			data.add(String.valueOf(trade.getPublisherCount() == null ? "" : trade.getPublisherCount()));
			data.add(String.valueOf(trade.getBalance() == null ? "" : trade.getBalance()));
			data.add(trade.getAgnetName() == null ? "" : trade.getAgnetName());
			data.add(trade.getPhone() == null ? "" : trade.getPhone());
			data.add(state);
			data.add(trade.getCreateTime() != null ? sdf.format(trade.getCreateTime()) : "");
			result.add(data);
		}
		return result;
	}

	private List<String> columnDescList() {
		List<String> result = new ArrayList<>();
		result.add("代理商ID");
		result.add("代理商代码");
		result.add("代理商名称");
		result.add("代理商成级");
		result.add("下线代理");
		result.add("推广客户");
		result.add("账户余额");
		result.add("代理商姓名");
		result.add("手机号");
		result.add("状态");
		result.add("创建时间");
		return result;
	}

}
