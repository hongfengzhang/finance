package com.waben.stock.applayer.promotion.controller;

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
    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public Response<PageInfo<OrganizationAccountFlowDto>> pages(OrganizationAccountFlowQuery query) {
        return new Response<>(organizationAccountFlowBusiness.pages(query));
    }


}
