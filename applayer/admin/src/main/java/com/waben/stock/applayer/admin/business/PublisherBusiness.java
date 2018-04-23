package com.waben.stock.applayer.admin.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.waben.stock.applayer.admin.reference.PublisherReference;
import com.waben.stock.interfaces.dto.admin.publisher.PublisherAdminDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.publisher.PublisherAdminQuery;

/**
 * 发布人 Business
 * 
 * @author luomengan
 */
@Service
public class PublisherBusiness {

	@Autowired
	@Qualifier("publisherReference")
	private PublisherReference reference;

	public PageInfo<PublisherAdminDto> adminPagesByQuery(@RequestBody PublisherAdminQuery query) {
		Response<PageInfo<PublisherAdminDto>> response = reference.adminPagesByQuery(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
