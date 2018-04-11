package com.waben.stock.applayer.promotion.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.reference.organization.OrganizationPublisherReference;
import com.waben.stock.interfaces.dto.organization.OrganizationPublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 机构推广的发布人 Business
 * 
 * @author luomengan
 *
 */
@Service
public class OrganizationPublisherBusiness {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("organizationPublisherReference")
	private OrganizationPublisherReference reference;

	public OrganizationPublisherDto addOrgPublisher(Long publisherId, String orgCode) {
		OrganizationPublisherDto orgPublisher = new OrganizationPublisherDto();
		orgPublisher.setOrgCode(orgCode);
		orgPublisher.setPublisherId(publisherId);
		Response<OrganizationPublisherDto> response = reference.addOrgPublisher(orgPublisher);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
