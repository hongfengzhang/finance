package com.waben.stock.applayer.admin.business.publisher;

import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.util.PasswordCrypt;
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

	public PublisherDto defriend(Long id) {
		Response<PublisherDto> response = reference.defriend(id);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PublisherDto fingById(Long id) {
		Response<PublisherDto> response = reference.fetchById(id);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PublisherDto recover(Long id) {
		Response<PublisherDto> response = reference.recover(id);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PublisherDto password(Long id, String password) {
		Response<PublisherDto> response = reference.fetchById(id);
		if ("200".equals(response.getCode())) {
			response.getResult().setPassword(PasswordCrypt.crypt(password));
			reference.modify(response.getResult());
			//TODO   发送信息通知用户
		}
		throw new ServiceException(response.getCode());
	}
}
