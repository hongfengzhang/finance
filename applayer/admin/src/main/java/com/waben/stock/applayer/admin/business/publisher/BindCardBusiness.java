package com.waben.stock.applayer.admin.business.publisher;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.admin.reference.BindCardReference;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.enums.BindCardResourceType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 绑卡 Business
 * 
 * @author luomengan
 *
 */
@Service
public class BindCardBusiness {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("bindCardReference")
	private BindCardReference service;

	public List<BindCardDto> listsByPublisherId(Long publisherId) {
		Response<List<BindCardDto>> response = service
				.listsByResourceTypeAndResourceId(BindCardResourceType.PUBLISHER.getIndex(), publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
