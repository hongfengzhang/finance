package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.stock.LossService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockcontent.LossDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.LossQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.stereotype.Component;

@Component
public class LossServiceFallback implements LossService {

    @Override
    public Response<PageInfo<LossDto>> pagesByQuery(LossQuery lossQuery) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<LossDto> fetchById(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<LossDto> modify(LossDto lossDto) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
