package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.publisher.PublisherService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PublisherQuery;
import org.springframework.stereotype.Component;

@Component
public class PublisherServiceFallback implements PublisherService{
    @Override
    public Response<PageInfo<PublisherDto>> pages(PublisherQuery publisherQuery) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PublisherDto> fetchById(Long id) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PublisherDto> fetchByPhone(String phone) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PublisherDto> register(String phone, String password, String promoter) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }


    @Override
    public Response<PublisherDto> modifyPassword(String phone, String password) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
