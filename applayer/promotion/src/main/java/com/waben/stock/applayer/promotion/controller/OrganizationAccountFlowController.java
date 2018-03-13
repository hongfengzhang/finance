package com.waben.stock.applayer.promotion.controller;

import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountQuery;
import com.waben.stock.interfaces.vo.organization.OrganizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.OrganizationAccountFlowBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationQuery;
import com.waben.stock.interfaces.service.organization.OrganizationAccountFlowInterface;
import com.waben.stock.interfaces.util.JacksonUtil;
import io.swagger.annotations.Api;

import java.util.List;

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

    @RequestMapping("/childpages")
    public Response<PageInfo<OrganizationVo>> childPages(OrganizationAccountFlowQuery query) {
        OrganizationAccountQuery organizationAccountQuery = new OrganizationAccountQuery();
        OrganizationQuery organizationQuery = new OrganizationQuery();
        organizationQuery.setParentId(query.getOrgId());
        PageInfo<OrganizationVo> organizations = organizationBusiness.pages(organizationQuery);
        List<OrganizationAccountFlowDto> organizationAccountFlowDtos = organizationAccountFlowBusiness.list();
        List<OrganizationVo> organizationVos = organizations.getContent();
        for (OrganizationVo org : organizationVos) {
            for (OrganizationAccountFlowDto oafd:organizationAccountFlowDtos){
                if (oafd.getOrg().getId() == org.getId()){
                        org.setAmount(org.getAmount().add(oafd.getAmount()));
                }
            }
        }
        organizations.setContent(organizationVos);
        return new Response<>(organizations);
    }
}
