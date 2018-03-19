package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.stockoption.MailUrlInfoService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.MailUrlInfoDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;

@Component
public class MailUrlInfoServiceFallback implements MailUrlInfoService {
    @Override
    public Response<MailUrlInfoDto> add(MailUrlInfoDto mailUrlInfoDto) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
