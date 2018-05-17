package com.waben.stock.interfaces.service.organization;

import java.util.List;

import com.waben.stock.interfaces.vo.organization.OrganizationVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.organization.OrganizationDetailDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.TreeNode;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.form.organization.OrganizationForm;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationQuery;

public interface OrganizationInterface {

	/**
	 * 添加机构
	 * 
	 * @param orgForm
	 *            机构表单数据
	 * @return 机构
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<OrganizationDto> addition(@RequestBody OrganizationForm orgForm);

	/**
	 * 获取机构分页数据
	 * 
	 * @param query
	 *            查询条件
	 * @return 机构分页数据
	 */
	@RequestMapping(value = "/adminPage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<OrganizationDto>> adminPage(@RequestBody OrganizationQuery query);

	/**
	 * 获取机构树
	 * 
	 * @param orgId
	 *            树最顶部的机构ID
	 * @return 机构树节点列表
	 */
	@RequestMapping(value = "/adminTree", method = RequestMethod.GET)
	List<TreeNode> adminTree(@RequestParam("orgId") Long orgId);

	/**
	 * 根据父机构ID获取下级机构列表
	 * 
	 * @param parentId
	 *            父机构ID
	 * @return 下级机构列表
	 */
	@RequestMapping(value = "/listByParentId", method = RequestMethod.GET)
	Response<List<OrganizationDto>> listByParentId(@RequestParam("parentId") Long parentId);

	/**
	 * 获取机构详情
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 机构详情
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	Response<OrganizationDetailDto> detail(@RequestParam("orgId") Long orgId);

	/**
	 * 修改机构名称
	 * 
	 * @param id
	 *            机构Id
	 * @param name
	 *            机构名称
	 * @return 机构
	 */
	@RequestMapping(value = "/modifyName", method = RequestMethod.PUT)
	Response<OrganizationDto> modifyName(@RequestParam("id") Long id, @RequestParam("name") String name);

	/**
	 * 获取机构绑卡信息
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 绑卡信息
	 */
	@RequestMapping(value = "/{orgId}/bindcard", method = RequestMethod.GET)
	Response<BindCardDto> fetchBindCard(@PathVariable("orgId") Long orgId);

	/**
	 * 保存机构绑卡信息
	 * 
	 * @param orgId
	 *            机构ID
	 * @param bindCardDto
	 *            绑卡信息
	 * @return 绑卡信息
	 */
	@RequestMapping(value = "/{orgId}/bindcard", method = RequestMethod.POST)
	Response<BindCardDto> saveBindCard(@PathVariable("orgId") Long orgId, @RequestBody BindCardDto bindCardDto);

	/**
			* 获取机构分页数据
	 *
			 * @param query
	 *            查询条件
	 * @return 带有总分成机构分页数据
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<OrganizationDto>> pages(@RequestBody OrganizationQuery query);
}