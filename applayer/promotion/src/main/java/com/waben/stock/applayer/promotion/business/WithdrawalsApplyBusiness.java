package com.waben.stock.applayer.promotion.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.reference.organization.WithdrawalsApplyReference;
import com.waben.stock.interfaces.dto.organization.WithdrawalsApplyDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.WithdrawalsApplyQuery;

/**
 * 提现申请 Business
 * 
 * @author luomengan
 *
 */
@Service
public class WithdrawalsApplyBusiness {

	@Autowired
	@Qualifier("withdrawalsApplyReference")
	private WithdrawalsApplyReference reference;

	public WithdrawalsApplyDto addition(WithdrawalsApplyDto apply) {
		Response<WithdrawalsApplyDto> response = reference.addition(apply);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<WithdrawalsApplyDto> pagesByQuery(WithdrawalsApplyQuery applyQuery) {
		Response<PageInfo<WithdrawalsApplyDto>> response = reference.pagesByQuery(applyQuery);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public WithdrawalsApplyDto changeState(Long applyId, String stateIndex) {
		Response<WithdrawalsApplyDto> response = reference.changeState(applyId, stateIndex);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
