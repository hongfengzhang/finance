package com.waben.stock.applayer.strategist.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.waben.stock.applayer.strategist.reference.PublisherReference;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 发布人 Business
 * 
 * @author luomengan
 *
 */
@Service
public class PublisherBusiness {

	@Autowired
	@Qualifier("publisherReference")
	private PublisherReference publisherReference;
	
	@Value("${custom.operation.server}")
	private String operationServer;

	public PublisherDto findById(Long publisherId) {
		Response<PublisherDto> response = publisherReference.fetchById(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PublisherDto register(String phone, String password, String promoter, String endType) {
		Response<PublisherDto> response = publisherReference.register(phone, password, promoter, endType);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PublisherDto modifyPassword(String phone, String password) {
		Response<PublisherDto> response = publisherReference.modifyPassword(phone, password);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PublisherDto modifyHeadPortrait(Long publisherId, String headPortrait) {
		Response<PublisherDto> response = publisherReference.modiyHeadportrait(publisherId, headPortrait);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
	
	public String uploadHeadPortrait(Long publisherId, MultipartFile file) {
		// TODO
		String headPortrait = "http://img.jf258.com/uploads/2014-04-09/042211205.jpg";
		if(headPortrait != null && !"".equals(headPortrait)) {
			modifyHeadPortrait(publisherId, headPortrait);
		}
		return headPortrait;
	}

}
