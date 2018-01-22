package com.waben.stock.applayer.operation.service.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.operation.service.publisher.PaymentOrderService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.PaymentOrderDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PaymentOrderQuery;

@Component
public class PaymentOrderServiceFallback implements PaymentOrderService {

	@Override
	public Response<PageInfo<PaymentOrderDto>> pages(PaymentOrderQuery query) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PaymentOrderDto> fetchByPaymentNo(String paymentNo) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PaymentOrderDto> addPaymentOrder(PaymentOrderDto paymentOrderDto) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PaymentOrderDto> changeState(String paymentNo, String stateIndex) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
