package com.waben.stock.applayer.promotion.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.promotion.reference.organization.OrganizationPublisherReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.OrganizationPublisherDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 机构推广的发布人 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class OrganizationPublisherReferenceFallback implements OrganizationPublisherReference {

	@Override
	public Response<OrganizationPublisherDto> addOrgPublisher(OrganizationPublisherDto orgPublisher) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<OrganizationPublisherDto> fetchOrgPublisher(Long publisherId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
