package com.waben.stock.applayer.promotion.reference.fallback;

import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountQuery;
import org.springframework.stereotype.Component;

import com.waben.stock.applayer.promotion.reference.organization.OrganizationAccountReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

import java.util.List;

/**
 * 机构账户 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class OrganizationAccountReferenceFallback implements OrganizationAccountReference {

	@Override
	public Response<OrganizationAccountDto> fetchByOrgId(Long orgId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<Void> modifyPaymentPassword(Long orgId, String oldPaymentPassword, String paymentPassword) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<OrganizationAccountDto>> pages(OrganizationAccountQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<List<OrganizationAccountDto>> list() {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
