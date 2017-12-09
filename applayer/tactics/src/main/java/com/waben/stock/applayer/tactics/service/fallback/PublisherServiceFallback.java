package com.waben.stock.applayer.tactics.service.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.tactics.service.PublisherService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

/**
 * @author Created by yuyidi on 2017/11/4.
 * @desc
 */
@Component
public class PublisherServiceFallback implements PublisherService {

	@Override
	public Response<PublisherDto> fetchById(Long id) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherDto> fetchByPhone(String phone) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherDto> register(String phone, String password, String promoter) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherDto> modifyPassword(String phone, String password) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<Integer> promotionCount(Long id) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PageInfo<PublisherDto>> pagePromotionUser(Long id, int page, int size) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
