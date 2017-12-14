package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.stock.AmountValueService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockcontent.AmountValueDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.AmountValueQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.stereotype.Component;

@Component
public class AmountValueServiceFallback implements AmountValueService {

    @Override
    public Response<PageInfo<AmountValueDto>> pagesByQuery(AmountValueQuery amountValueQuery) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
