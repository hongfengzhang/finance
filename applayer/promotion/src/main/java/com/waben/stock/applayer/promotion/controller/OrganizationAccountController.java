package com.waben.stock.applayer.promotion.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.BindCardBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationAccountBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationPublisherBusiness;
import com.waben.stock.applayer.promotion.security.SecurityUtil;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.OrganizationPublisherDto;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountQuery;
import com.waben.stock.interfaces.request.organization.OrganizationAccountRequest;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.organization.OrganizationAccountVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 机构账户 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/organizationAccount")
@Api(description = "代理商资产接口列表")
public class OrganizationAccountController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public OrganizationAccountBusiness accountBusiness;

	@Autowired
	private OrganizationBusiness organizationBusiness;

	@Autowired
	private OrganizationPublisherBusiness publisherBusiness;

	@Autowired
	private BindCardBusiness bindCardBusiness;

	@RequestMapping(value = "/modifyPaymentPassword", method = RequestMethod.PUT)
	public Response<Void> modifyPaymentPassword(String oldPaymentPassword, String paymentPassword) {
		accountBusiness.modifyPaymentPassword(SecurityUtil.getUserDetails().getOrgId(), oldPaymentPassword, paymentPassword);
		return new Response<>();
	}

	@RequestMapping(value = "/orgId/{orgId}", method = RequestMethod.GET)
	public Response<OrganizationAccountDto> fetchByOrgId(@PathVariable("orgId") Long orgId) {
		return new Response<>(accountBusiness.fetchByOrgId(orgId));
	}

	@RequestMapping(value = "/pages",method = RequestMethod.GET)
	@ApiImplicitParam(paramType = "query", dataType = "OrganizationAccountQuery", name = "query", value = "代理商资产查询对象", required = false)
	@ApiOperation(value = "代理商资产分页")
	public Response<PageInfo<OrganizationAccountVo>> pages(OrganizationAccountQuery query){
		if(query.getName()!=null) {
			BindCardDto orgBindCard = bindCardBusiness.findOrgBindCardByName(query.getName());
			if(orgBindCard!=null) {
				query.setId(orgBindCard.getResourceId());
			}
		}
		PageInfo<OrganizationAccountDto> pageInfo = accountBusiness.pages(query);
		List<OrganizationAccountVo> roleVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), OrganizationAccountVo.class);
		PageInfo<OrganizationAccountVo> response = new PageInfo<>(roleVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
		for (int i=0; i<pageInfo.getContent().size(); i++) {
			OrganizationDto organizationDto = pageInfo.getContent().get(i).getOrg();
			response.getContent().get(i).setCode(organizationDto.getCode());
			response.getContent().get(i).setName(organizationDto.getName());
			response.getContent().get(i).setLevel(organizationDto.getLevel());
			response.getContent().get(i).setLevel(organizationDto.getLevel());
			List<OrganizationDto> organizationDtos = organizationBusiness.listByParentId(organizationDto.getParentId());
			response.getContent().get(i).setChildOrgCount(organizationDtos.size());
			List<OrganizationPublisherDto> organizationPublisherDtos = publisherBusiness.findOrganizationPublishersByCode(organizationDto.getCode());
			response.getContent().get(i).setPopPulisherCount(organizationPublisherDtos.size());
			BindCardDto bindCardDto = bindCardBusiness.getOrgBindCard(organizationDto.getId());
			if(bindCardDto!=null) {
				response.getContent().get(i).setOrgPhone(bindCardDto.getPhone());
				response.getContent().get(i).setOrgName(bindCardDto.getName());
			}
		}
		return new Response<>(response);
	}

//	@RequestMapping(value = "/state/{id}", method = RequestMethod.PUT)
//	@ApiImplicitParams({@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "代理商资产id", required = true),@ApiImplicitParam(paramType = "path", dataType = "Integer", name = "state", value = "代理商资产状态（1正常，2冻结）", required = true)})
//	@ApiOperation(value = "修改代理商资产状态")
//	public Response<OrganizationAccountDto> modifyState(@PathVariable Long id, @PathVariable Integer state) {
//		OrganizationAccountDto result = accountBusiness.revisionState(id,state);
//		return new Response<>(result);
//	}


	@ApiImplicitParam(paramType = "query", dataType = "OrganizationAccountRequest", name = "request", value = "代理商资产对象", required = true)
	@ApiOperation(value = "冻结")
	@RequestMapping(value = "/freeze", method = RequestMethod.PUT)
	public Response<OrganizationAccountDto> freeze(OrganizationAccountRequest request) {
		OrganizationAccountDto organizationAccountDto = CopyBeanUtils.copyBeanProperties(OrganizationAccountDto.class,
				request, false);
		OrganizationAccountDto result = accountBusiness.freeze(organizationAccountDto);
		return new Response<>(result);
	}

	@RequestMapping(value = "/state/{id}", method = RequestMethod.PUT)
	@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "代理商资产id", required = true)
	@ApiOperation(value = "解冻")
	public Response<OrganizationAccountDto> recover(@PathVariable Long id) {
		OrganizationAccountDto result = accountBusiness.recover(id);
		return new Response<>(result);
	}

}
