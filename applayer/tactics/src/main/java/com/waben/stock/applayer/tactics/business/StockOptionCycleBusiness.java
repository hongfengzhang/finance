package com.waben.stock.applayer.tactics.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.waben.stock.applayer.tactics.reference.StockOptionCycleReference;
import com.waben.stock.interfaces.dto.stockoption.StockOptionCycleDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 期权周期 Business
 * 
 * @author luomengan
 *
 */
public class StockOptionCycleBusiness {

	@Autowired
	@Qualifier("stockOptionCycleReference")
	private StockOptionCycleReference stockOptionCycleReference;

	public List<StockOptionCycleDto> lists() {
		Response<List<StockOptionCycleDto>> response = stockOptionCycleReference.lists();
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
