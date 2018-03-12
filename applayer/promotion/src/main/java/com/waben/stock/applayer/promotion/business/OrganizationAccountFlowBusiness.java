package com.waben.stock.applayer.promotion.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.reference.organization.OrganizationAccountFlowReference;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;

@Service
public class OrganizationAccountFlowBusiness {

    @Autowired
    @Qualifier("organizationAccountFlowReference")
    private OrganizationAccountFlowReference organizationAccountFlowReference;

    public PageInfo<OrganizationAccountFlowDto> pages(OrganizationAccountFlowQuery query) {
        Response<PageInfo<OrganizationAccountFlowDto>> response = organizationAccountFlowReference.pages(query);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    public PageInfo<OrganizationAccountFlowDto> childPages(OrganizationAccountFlowQuery query) {
        Response<PageInfo<OrganizationAccountFlowDto>> response = organizationAccountFlowReference.childpages(query);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
