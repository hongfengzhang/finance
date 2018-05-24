package com.waben.stock.applayer.operation.service.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.operation.service.publisher.PaymentOrderReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.PaymentOrderDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PaymentOrderQuery;

/**
 * 支付订单 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class PaymentOrderReferenceFallback implements PaymentOrderReference {

	@Override
	public Response<PaymentOrderDto> fetchByPaymentNo(String paymentNo) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PaymentOrderDto> addPaymentOrder(PaymentOrderDto paymentOrderDto) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PaymentOrderDto> changeState(String paymentNo, String stateIndex) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<PaymentOrderDto>> pages(PaymentOrderQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}
	
	@Override
	public Response<PageInfo<PaymentOrderDto>> pagesByQuery(PaymentOrderQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PaymentOrderDto> fetchById(Long paymentId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PaymentOrderDto> modifyPaymentOrder(PaymentOrderDto paymentOrderDto) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
