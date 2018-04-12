package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.activity.PublisherTicketService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.PublisherTicketDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherTicketBusiness {
    @Autowired
    @Qualifier("publisherTicketFeignService")
    private PublisherTicketService publisherTicketService;

    public List<PublisherTicketDto> pages(int pageNo, Integer pageSize) {
        Response<List<PublisherTicketDto>> response = publisherTicketService.getPublisherTicketList(pageNo, pageSize);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
