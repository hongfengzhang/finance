package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.stockoption.InquiryResultService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.InquiryResultDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;

@Component
public class InquiryResultServiceFallback implements InquiryResultService{
    @Override
    public Response<InquiryResultDto> add(InquiryResultDto inquiryResultDto) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
