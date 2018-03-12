package com.waben.stock.applayer.promotion.service.fallback;


import com.waben.stock.applayer.promotion.service.organization.OrganizationAccountFlowService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationServiceFlowFallback implements OrganizationAccountFlowService {
    @Override
    public Response<PageInfo<OrganizationAccountFlowDto>> pages(OrganizationAccountFlowQuery query) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PageInfo<OrganizationAccountFlowDto>> childpages(OrganizationAccountFlowQuery query) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<List<OrganizationAccountFlowDto>> list() {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

}
