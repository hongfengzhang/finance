package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.publisher.PublisherService;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PublisherQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PublisherBusiness {

    @Autowired
    @Qualifier("publisherFeignService")
    private PublisherService publisherService;

    public PageInfo<PublisherDto> pages(PublisherQuery query) {
        Response<PageInfo<PublisherDto>> response = publisherService.pages(query);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
