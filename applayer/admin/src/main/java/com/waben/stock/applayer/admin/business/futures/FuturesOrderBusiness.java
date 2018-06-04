package com.waben.stock.applayer.admin.business.futures;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.dto.admin.futures.FutresOrderEntrustDto;
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
	private PublisherInterface publisherInterface;
	
	@Autowired
	private RealNameInterface realnameInterface;
	
	private List<Long> queryPublishIds(FuturesTradeAdminQuery query){
		List<Long> publisherIds = new ArrayList<Long>();
		if(query.getPublisherPhone()!=null){
			if(publisherInterface.fetchByPhone(query.getPublisherPhone()).getResult()!=null){
				String publisherId = publisherInterface.fetchByPhone(query.getPublisherPhone()).getResult().getId().toString();
				publisherIds.add(Long.valueOf(publisherId));
			};
		}
		
		if(query.getPublisherName()!=null){
			if(publisherIds.size()==0 ){
				List<RealNameDto> real = realnameInterface.findByName(query.getPublisherName()).getResult();
				for (RealNameDto realNameDto : real) {
					publisherIds.add(Long.valueOf(realNameDto.getResourceId().toString()));
				}
			}else{
				publisherIds.clear();
				publisherIds.add(Long.valueOf("-1"));
			}
		}
		return publisherIds;
	}
	
	public PageInfo<FutresOrderEntrustDto> pagesOrderEntrust(FuturesTradeAdminQuery query){
		query.setPublisherIds(queryPublishIds(query));
		Response<PageInfo<FutresOrderEntrustDto>> response = reference.pagesOrderEntrust(query);
		if(response.getResult().getContent().size()>0){
			for (FutresOrderEntrustDto dto : response.getResult().getContent()) {
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
		}
		if("200".equals(response.getCode())){
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
	
	public PageInfo<FuturesOrderAdminDto> adminPagesByQuery(FuturesTradeAdminQuery query) {
		List<Long> publisherIds = new ArrayList<Long>();
		if(query.getPublisherPhone()!=null && !"".equals(query.getPublisherPhone())){
			if(publisherInterface.fetchByPhone(query.getPublisherPhone()).getResult()!=null){
				String publisherId = publisherInterface.fetchByPhone(query.getPublisherPhone()).getResult().getId().toString();
				if(publisherId !=null && !"".equals(publisherId)){
					publisherIds.add(Long.valueOf(publisherId));
				}else{
					return null;
				}
			};
		}else if(query.getPublisherName()!=null && !"".equals(query.getPublisherName())){
			List<RealNameDto> real = realnameInterface.findByName(query.getPublisherName()).getResult();
			if(real==null||real.size()==0){
				return null;
			}else{
				for (RealNameDto realNameDto : real) {
					publisherIds.add(Long.valueOf(realNameDto.getResourceId().toString()));
				}
			}
			
		}
		
//		if(query.getPublisherName()!=null && !"".equals(query.getPublisherName())){
//			if(publisherIds.size()==0 ){
//			}else{
//				publisherIds.clear();
//				publisherIds.add(Long.valueOf("-1"));
//			}
//		}
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
