package com.waben.stock.applayer.promotion.business.futures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.waben.stock.interfaces.service.futures.FuturesTradeInterface;
import com.waben.stock.interfaces.service.organization.OrganizationPublisherInterface;
import com.waben.stock.interfaces.service.publisher.RealNameInterface;

@Service
public class FuturesTradeBusiness {

	@Autowired
	@Qualifier("futuresTradeInterface")
	private FuturesTradeInterface reference;
	
	@Autowired
	@Qualifier("organizationPublisherInterface")
	private OrganizationPublisherInterface publisherReference;
	
	@Autowired
	@Qualifier("realNameInterface")
	private RealNameInterface realnameInterface;
	
	private Long getOrgId(@SessionAttribute Long orgId){
		return orgId;
	}
}
