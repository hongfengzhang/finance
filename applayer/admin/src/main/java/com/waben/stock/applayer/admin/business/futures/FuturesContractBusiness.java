package com.waben.stock.applayer.admin.business.futures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.applayer.admin.business.stockoption.HolidayBusiness;
import com.waben.stock.interfaces.service.futures.FuturesContractInterface;

@Service
public class FuturesContractBusiness {
	
	@Autowired
	@Qualifier("futuresContractInterface")
	private FuturesContractInterface futuresContractInterface;

	@Autowired
	private HolidayBusiness holidayBusiness;

	@Autowired
	private RestTemplate restTemplate;
	
	
}
