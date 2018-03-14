package com.waben.stock.applayer.promotion.controller;

import com.waben.stock.applayer.promotion.util.SecurityAccount;
import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountQuery;
import com.waben.stock.interfaces.vo.organization.OrganizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.OrganizationAccountFlowBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/orgflow")
@Api(description = "结算管理")
public class OrganizationAccountFlowController {

    @Autowired
    public OrganizationAccountFlowBusiness organizationAccountFlowBusiness;

    @Autowired
    public OrganizationBusiness organizationBusiness;
    @RequestMapping(value = "/pages", method = RequestMethod.POST)
    public Response<PageInfo<OrganizationAccountFlowDto>> pages(OrganizationAccountFlowQuery query) {
        UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
        query.setOrgId(userDto.getOrg().getId());
        return new Response<>(organizationAccountFlowBusiness.pages(query));
    }

    //渠道分成报表
    @RequestMapping(value = "/childpages", method = RequestMethod.POST)
    public Response<PageInfo<OrganizationAccountFlowDto>> childPages(OrganizationAccountFlowQuery query) {
        return new Response<>(organizationAccountFlowBusiness.pages(query));
    }

}
