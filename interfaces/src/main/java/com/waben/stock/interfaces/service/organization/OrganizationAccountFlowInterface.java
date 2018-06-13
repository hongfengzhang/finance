package com.waben.stock.interfaces.service.organization;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.organization.AgentCapitalManageDto;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowWithTradeInfoDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.AgentCapitalManageQuery;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;

@FeignClient(name = "organization", path = "organizationAccountFlow", qualifier = "organizationAccountFlowInterface")
public interface OrganizationAccountFlowInterface {

	@RequestMapping(value = "/pagesWithTradeInfo", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<OrganizationAccountFlowWithTradeInfoDto>> pagesWithTradeInfo(
			@RequestBody OrganizationAccountFlowQuery query);

	@RequestMapping(value = "/list", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<List<OrganizationAccountFlowDto>> list();

	/**
	 * 获取资金流水和佣金结算数据
	 * 
	 * @param flowQuery
	 *            查询条件
	 * @return 资金流水和佣金结算
	 */
	@RequestMapping(value = "/agent/capital/manage", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<AgentCapitalManageDto>> pageAgentCapitalManage(@RequestBody AgentCapitalManageQuery query);

}
