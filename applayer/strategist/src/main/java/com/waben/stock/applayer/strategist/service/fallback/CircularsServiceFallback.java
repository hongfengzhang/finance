package com.waben.stock.applayer.strategist.service.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.strategist.service.CircularsService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 通告 断路器回调
 * 
 * @author luomengan
 *
 */
@Component
public class CircularsServiceFallback implements CircularsService {

	@Override
	public Response<List<CircularsDto>> fetchCirculars(Boolean enable) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
