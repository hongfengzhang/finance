package com.waben.stock.applayer.admin.reference.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.admin.reference.StockOptionOrgReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

@Component
public class StockOptionOrgReferenceFallback implements StockOptionOrgReference {

	@Override
	public Response<List<StockOptionOrgDto>> lists() {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
