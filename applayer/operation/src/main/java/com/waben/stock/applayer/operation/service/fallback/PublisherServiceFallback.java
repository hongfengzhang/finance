package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.publisher.PublisherService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PublisherQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublisherServiceFallback implements PublisherService{
    @Override
    public Response<PageInfo<PublisherDto>> pages(PublisherQuery publisherQuery) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PublisherDto> fetchById(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PublisherDto> fetchByPhone(String phone) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PublisherDto> register(String phone, String password, String promoter, String endType) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }


    @Override
    public Response<PublisherDto> modifyPassword(String phone, String password) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

	@Override
	public Response<Integer> promotionCount(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<PublisherDto>> pagePromotionUser(Long id, int page, int size) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

    @Override
    public Response<List<PublisherDto>> fetchPublishers() {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<List<PublisherDto>> fetchByIsTest(Boolean test) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
	public Response<PublisherDto> modiyHeadportrait(Long id, String headPortrait) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PublisherDto> modify(PublisherDto publisherDto) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}


}
