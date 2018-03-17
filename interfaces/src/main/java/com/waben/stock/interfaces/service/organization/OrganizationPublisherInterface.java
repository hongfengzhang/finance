package com.waben.stock.interfaces.service.organization;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.organization.OrganizationPublisherDto;
import com.waben.stock.interfaces.pojo.Response;

public interface OrganizationPublisherInterface {
	
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<OrganizationPublisherDto> addOrgPublisher(@RequestBody OrganizationPublisherDto orgPublisher);
	
}
