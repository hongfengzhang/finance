package com.waben.stock.applayer.strategist.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.strategist.reference.AnalogDataReference;
import com.waben.stock.interfaces.dto.manage.AnalogDataDto;
import com.waben.stock.interfaces.enums.AnalogDataType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

/**
 * 模拟数据 Business
 * 
 * @author luomengan
 *
 */
@Service
public class AnalogDataBusiness {

	@Autowired
	@Qualifier("analogDataReference")
	private AnalogDataReference reference;

	public PageInfo<AnalogDataDto> pagesByType(AnalogDataType type, int page, int limit) {
		Response<PageInfo<AnalogDataDto>> response = reference.pagesByType(type.getIndex(), page, limit);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
