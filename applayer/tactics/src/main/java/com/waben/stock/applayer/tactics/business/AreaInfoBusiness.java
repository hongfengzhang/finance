package com.waben.stock.applayer.tactics.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.service.AreaInfoService;
import com.waben.stock.interfaces.dto.manage.AreaInfoDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 区域 Business
 * 
 * @author luomengan
 *
 */
@Service
public class AreaInfoBusiness {

	@Autowired
	private AreaInfoService areaInfoService;

	public List<AreaInfoDto> findByParentCode(String parentCode) {
		Response<List<AreaInfoDto>> response = areaInfoService.fetchByParentCode(parentCode);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
