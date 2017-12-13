package com.waben.stock.datalayer.buyrecord.business;

import com.waben.stock.datalayer.buyrecord.reference.PublisherReference;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PublisherBusiness {

    @Autowired
    @Qualifier("publisherFeignReference")
    private PublisherReference publisherReference;

    public PublisherDto findById(Long id) {
        Response<PublisherDto> response = publisherReference.fetchById(id);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
