package com.waben.stock.applayer.admin.business.futures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.applayer.admin.business.stockoption.HolidayBusiness;
import com.waben.stock.interfaces.dto.admin.futures.FuturesOrderAdminDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;
import com.waben.stock.interfaces.service.futures.FuturesTradeInterface;

/**
 * 期货交易 Business
 * 
 * @author pengzhenliang
 */
@Service
public class FuturesOrderBusiness {
	
	@Autowired
	@Qualifier("futuresTradeInterface")
	private FuturesTradeInterface reference;
	
	@Autowired
	private HolidayBusiness holidayBusiness;

	@Autowired
	private RestTemplate restTemplate;
	
	public PageInfo<FuturesOrderAdminDto> adminPagesByQuery(FuturesTradeAdminQuery query) {
		Response<PageInfo<FuturesOrderAdminDto>> response = reference.adminPagesByQuery(query);
		if("200".equals(response.getCode())){
			
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
	

}
