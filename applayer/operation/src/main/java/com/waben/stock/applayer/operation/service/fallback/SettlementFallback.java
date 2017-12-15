package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.buyrecord.SettlementService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.SettlementDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SettlementQuery;
import org.springframework.stereotype.Component;

@Component
public class SettlementFallback implements SettlementService{
    @Override
    public Response<PageInfo<SettlementDto>> pagesByQuery(SettlementQuery query) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
