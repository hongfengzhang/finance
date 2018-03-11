package com.waben.stock.applayer.promotion.business;

import com.waben.stock.applayer.promotion.reference.OrganizationAccountFlowReference;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
}
