package com.waben.stock.applayer.admin.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.admin.reference.AnalogDataReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.AnalogDataDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

/**
 * 模拟数据 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class AnalogDataReferenceFallback implements AnalogDataReference {

	@Override
	public Response<PageInfo<AnalogDataDto>> pagesByType(String typeIndex, int page, int limit) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
