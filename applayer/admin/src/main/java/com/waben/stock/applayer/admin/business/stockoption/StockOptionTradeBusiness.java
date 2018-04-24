package com.waben.stock.applayer.admin.business.stockoption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.admin.reference.StockOptionTradeReference;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionAdminDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.stockoption.StockOptionQueryDto;

/**
 * 期权交易 Business
 * 
 * @author luomengan
 */
@Service
public class StockOptionTradeBusiness {

	@Autowired
	@Qualifier("stockOptionTradeReference")
	private StockOptionTradeReference reference;

	public PageInfo<StockOptionAdminDto> adminPagesByQuery(StockOptionQueryDto query) {
		Response<PageInfo<StockOptionAdminDto>> response = reference.adminPagesByQuery(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
