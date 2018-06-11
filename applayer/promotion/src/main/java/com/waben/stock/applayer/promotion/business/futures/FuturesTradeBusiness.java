package com.waben.stock.applayer.promotion.business.futures;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.dto.admin.futures.FutresOrderEntrustDto;
import com.waben.stock.interfaces.dto.admin.futures.FuturesOrderAdminDto;
import com.waben.stock.interfaces.dto.organization.FuturesTradeOrganizationDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.OrganizationPublisherDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.dto.publisher.RealNameDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;
import com.waben.stock.interfaces.service.futures.FuturesTradeInterface;
import com.waben.stock.interfaces.service.organization.OrganizationInterface;
import com.waben.stock.interfaces.service.organization.OrganizationPublisherInterface;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;
import com.waben.stock.interfaces.service.publisher.RealNameInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

@Service
public class FuturesTradeBusiness {

	@Autowired
	@Qualifier("futuresTradeInterface")
	private FuturesTradeInterface reference;
	
	@Autowired
	private OrganizationPublisherInterface organizationPublisherReference;
	
	@Autowired
	private PublisherInterface publisherInterface;
	
	@Autowired
	private RealNameInterface realnameInterface;
	
	@Autowired
	private OrganizationInterface orgReference;
	
	private List<Long> getOrgId(){
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		Long orgId = (Long)request.getAttribute("orgId");
		Long orgId = Long.valueOf(1);
		
		//获取机构及子机构下的客户
		Response<List<OrganizationPublisherDto>> result = organizationPublisherReference.findByOrgId(orgId);
		List<Long> publisherIds = new ArrayList<Long>();
		for(OrganizationPublisherDto publisher:result.getResult()){
			publisherIds.add(publisher.getPublisherId());
		}
		return publisherIds;
	}
	
	public Response<PageInfo<FuturesTradeOrganizationDto>> pagesOrganizationOrder(FuturesTradeAdminQuery query){
		query.setPublisherIds(getOrgId());
		Response<PageInfo<FuturesTradeOrganizationDto>> pagesResponse = new Response<PageInfo<FuturesTradeOrganizationDto>>();
		Response<PageInfo<FuturesOrderAdminDto>> response = reference.adminPagesByQuery(query);
		if(response!=null && response.getResult()!=null){
			List<FuturesOrderAdminDto> list = response.getResult().getContent();
			List<FuturesTradeOrganizationDto> result = CopyBeanUtils.copyListBeanPropertiesToList(list, FuturesTradeOrganizationDto.class);
			for(FuturesTradeOrganizationDto dto:result){
				if(dto.getPublisherId()!=null){
					PublisherDto pu = publisherInterface.fetchById(dto.getPublisherId()).getResult();
					if(pu!=null){
						dto.setPublisherPhone(pu.getPhone());
					}
					RealNameDto re = realnameInterface.fetchByResourceId(dto.getPublisherId()).getResult();
					if(re!=null){
						dto.setPublisherName(re.getName());
					}
					Response<OrganizationPublisherDto> opDto = organizationPublisherReference.fetchOrgPublisher(dto.getPublisherId());
					if(opDto.getResult()!=null){
						Response<OrganizationDto> org = orgReference.fetchByOrgId(opDto.getResult().getOrgId());
						if(org.getResult()!=null){
							dto.setOrgName(org.getResult().getCode()+"/"+org.getResult().getName());
						}
					}
				}
			}
			PageInfo<FuturesTradeOrganizationDto> pageInfo = new PageInfo<>(result, response.getResult().getTotalPages(), response.getResult().getLast(), response.getResult().getTotalElements(), query.getSize(), query.getPage(), response.getResult().getFrist());
			pagesResponse.setCode("200");
			pagesResponse.setResult(pageInfo);
			pagesResponse.setMessage("响应成功");
		}
		return pagesResponse;
	}
	
	public Response<PageInfo<FutresOrderEntrustDto>> pagesOrganizationEntrustOrder(FuturesTradeAdminQuery query){
		query.setPublisherIds(getOrgId());
		Response<PageInfo<FutresOrderEntrustDto>> response = reference.pagesOrderEntrust(query);
		if(response.getCode().equals("200")&&response.getResult()!=null&&response.getResult().getContent()!=null){
			for(FutresOrderEntrustDto dto : response.getResult().getContent()){
				if(dto.getPublisherId()!=null){
					PublisherDto pu = publisherInterface.fetchById(dto.getPublisherId()).getResult();
					if(pu!=null){
						dto.setPublisherPhone(pu.getPhone());
					}
					RealNameDto re = realnameInterface.fetchByResourceId(dto.getPublisherId()).getResult();
					if(re!=null){
						dto.setPublisherName(re.getName());
					}
					Response<OrganizationPublisherDto> opDto = organizationPublisherReference.fetchOrgPublisher(dto.getPublisherId());
					if(opDto.getResult()!=null){
						Response<OrganizationDto> org = orgReference.fetchByOrgId(opDto.getResult().getOrgId());
						if(org.getResult()!=null){
							dto.setOrgName(org.getResult().getCode()+"/"+org.getResult().getName());
						}
					}
				}
			}
		}
		return response;
	}
}
