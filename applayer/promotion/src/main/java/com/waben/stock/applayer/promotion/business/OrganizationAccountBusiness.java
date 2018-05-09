package com.waben.stock.applayer.promotion.business;

import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountQuery;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationQuery;
import com.waben.stock.interfaces.vo.organization.OrganizationAccountVo;
import com.waben.stock.interfaces.vo.organization.OrganizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.reference.organization.OrganizationAccountReference;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

import java.util.List;

/**
 * 机构账户 Business
 * 
 * @author luomengan
 *
 */
@Service
public class OrganizationAccountBusiness {

	@Autowired
	@Qualifier("organizationAccountReference")
	private OrganizationAccountReference reference;

	@Autowired
	private OrganizationBusiness organizationBusiness;

	public OrganizationAccountDto fetchByOrgId(Long orgId) {
		Response<OrganizationAccountDto> response = reference.fetchByOrgId(orgId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public void modifyPaymentPassword(Long orgId, String oldPaymentPassword, String paymentPassword) {
		Response<Void> response = reference.modifyPaymentPassword(orgId, oldPaymentPassword, paymentPassword);
		if ("200".equals(response.getCode())) {
			return;
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<OrganizationAccountDto> pages(OrganizationAccountQuery query) {
		Response<PageInfo<OrganizationAccountDto>> response = reference.pages(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public OrganizationAccountDto revisionState(Long id, Integer state) {
		Response<OrganizationAccountDto> response = reference.modifyState(id, state);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
}
