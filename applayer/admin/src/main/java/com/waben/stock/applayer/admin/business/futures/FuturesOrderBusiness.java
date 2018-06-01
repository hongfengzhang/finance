package com.waben.stock.applayer.admin.business.futures;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.applayer.admin.business.stockoption.HolidayBusiness;
import com.waben.stock.interfaces.dto.admin.futures.FuturesOrderAdminDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.dto.publisher.RealNameDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;
import com.waben.stock.interfaces.service.futures.FuturesTradeInterface;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;
import com.waben.stock.interfaces.service.publisher.RealNameInterface;

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
	
	@Autowired
	private PublisherInterface publisherInterface;
	
	@Autowired
	private RealNameInterface realnameInterface;
	
	public PageInfo<FuturesOrderAdminDto> adminPagesByQuery(FuturesTradeAdminQuery query) {
		List<String> publisherIds = new ArrayList<String>();
		if(query.getPublisherPhone()!=null){
			if(publisherInterface.fetchByPhone(query.getPublisherPhone()).getResult()!=null){
				String publisherId = publisherInterface.fetchByPhone(query.getPublisherPhone()).getResult().getId().toString();
				publisherIds.add(publisherId);
			};
		}
		
		if(query.getPublisherName()!=null){
			if(publisherIds.size()==0 ){
				List<RealNameDto> real = realnameInterface.findByName(query.getPublisherName());
				for (RealNameDto realNameDto : real) {
					publisherIds.add(realNameDto.getResourceId().toString());
				}
			}else{
				publisherIds.clear();
				publisherIds.add("-1");
			}
		}
		query.setPublisherIds(publisherIds);
		Response<PageInfo<FuturesOrderAdminDto>> response = reference.adminPagesByQuery(query);
		for (FuturesOrderAdminDto dto : response.getResult().getContent()) {
			if(dto.getPublisherId()!=null){
				PublisherDto pu = publisherInterface.fetchById(dto.getPublisherId()).getResult();
				if(pu!=null){
					dto.setPublisherPhone(pu.getPhone());
				}
				RealNameDto re = realnameInterface.fetchByResourceId(dto.getPublisherId()).getResult();
				if(re!=null){
					dto.setPublisherName(re.getName());
				}
			}
		}
		if("200".equals(response.getCode())){
			
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
	

}
