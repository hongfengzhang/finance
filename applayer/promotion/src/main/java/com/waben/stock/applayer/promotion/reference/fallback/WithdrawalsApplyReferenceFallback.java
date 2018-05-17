package com.waben.stock.applayer.promotion.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.promotion.reference.organization.WithdrawalsApplyReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.WithdrawalsApplyDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.WithdrawalsApplyQuery;

/**
 * 提现申请 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class WithdrawalsApplyReferenceFallback implements WithdrawalsApplyReference {

	@Override
	public Response<WithdrawalsApplyDto> addition(WithdrawalsApplyDto apply) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<WithdrawalsApplyDto>> pagesByQuery(WithdrawalsApplyQuery applyQuery) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<WithdrawalsApplyDto> changeState(Long applyId, String stateIndex) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<WithdrawalsApplyDto> fetchById(Long id) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<WithdrawalsApplyDto> fetchByApplyNo(String applyNo) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
