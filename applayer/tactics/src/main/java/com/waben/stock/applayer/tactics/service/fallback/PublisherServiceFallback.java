package com.waben.stock.applayer.tactics.service.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.tactics.service.PublisherService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * @author Created by yuyidi on 2017/11/4.
 * @desc
 */
@Component
public class PublisherServiceFallback implements PublisherService {

	@Override
	public Response<PublisherDto> findById(Long id) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherDto> register(String phone, String password) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherDto> findBySerialCode(String serialCode) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherDto> modifyPassword(String phone, String password) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PublisherDto> findByPhone(String phone) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<String> modifyPaymentPassword(String serialCode, String paymentPassword) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
