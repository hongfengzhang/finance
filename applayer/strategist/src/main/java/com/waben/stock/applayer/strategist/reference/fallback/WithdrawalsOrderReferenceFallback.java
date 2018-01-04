package com.waben.stock.applayer.strategist.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.strategist.reference.WithdrawalsOrderReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.WithdrawalsOrderDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 支提现订单 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class WithdrawalsOrderReferenceFallback implements WithdrawalsOrderReference {

	@Override
	public Response<WithdrawalsOrderDto> fetchByWithdrawalsNo(String withdrawalsNo) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<WithdrawalsOrderDto> addWithdrawalsOrder(WithdrawalsOrderDto withdrawalsOrderDto) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<WithdrawalsOrderDto> modifyWithdrawalsOrder(WithdrawalsOrderDto withdrawalsOrderDto) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<WithdrawalsOrderDto> changeState(String withdrawalsNo, String stateIndex) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
