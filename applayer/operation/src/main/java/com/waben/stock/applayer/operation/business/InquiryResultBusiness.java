package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stockoption.InquiryResultService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.InquiryResultDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InquiryResultBusiness {
    @Autowired
    @Qualifier("inquiryresultFeignService")
    private InquiryResultService inquiryResultService;

    public InquiryResultDto add(InquiryResultDto inquiryResultDto) {
        Response<InquiryResultDto> response = inquiryResultService.add(inquiryResultDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public InquiryResultDto fetchByTrade(Long trade) {
        Response<InquiryResultDto> response = inquiryResultService.findByTrade(trade);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
