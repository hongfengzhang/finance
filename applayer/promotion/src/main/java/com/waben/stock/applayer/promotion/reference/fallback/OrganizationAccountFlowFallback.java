package com.waben.stock.applayer.promotion.reference.fallback;

import com.waben.stock.applayer.promotion.reference.OrganizationAccountFlowReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;
import org.springframework.stereotype.Component;

@Component
public class OrganizationAccountFlowFallback implements OrganizationAccountFlowReference {
    @Override
    public Response<PageInfo<OrganizationAccountFlowDto>> pages(OrganizationAccountFlowQuery query) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
