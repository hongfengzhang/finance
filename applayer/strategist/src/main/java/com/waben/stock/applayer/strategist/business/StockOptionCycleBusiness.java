package com.waben.stock.applayer.strategist.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.strategist.reference.StockOptionCycleReference;
import com.waben.stock.interfaces.dto.stockoption.StockOptionCycleDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 期权周期 Business
 * 
 * @author luomengan
 *
 */
@Service
public class StockOptionCycleBusiness {

	@Autowired
	@Qualifier("stockOptionCycleReference")
	private StockOptionCycleReference stockOptionCycleReference;

	public StockOptionCycleDto fetchByCycle(Integer cycle) {
		Response<StockOptionCycleDto> response = stockOptionCycleReference.fetchByCycle(cycle);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
	
	public List<StockOptionCycleDto> lists() {
		Response<List<StockOptionCycleDto>> response = stockOptionCycleReference.lists();
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockOptionCycleDto fetchById(Long id) {
		Response<StockOptionCycleDto> response = stockOptionCycleReference.fetchById(id);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
