package com.waben.stock.applayer.tactics.business.futures;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.dto.futures.FuturesOrderMarketDto;
import com.waben.stock.interfaces.commonapi.retrivefutures.RetriveFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
import com.waben.stock.interfaces.dto.futures.FuturesOrderDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.futures.FuturesOrderInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

@Service
public class FuturesOrderBusiness {

	@Autowired
	@Qualifier("futuresOrderInterface")
	private FuturesOrderInterface futuresOrderInterface;

	public Integer sumUserNum(Long contractId, Long publisherId) {
		Response<Integer> response = futuresOrderInterface.sumByListOrderContractIdAndPublisherId(contractId,
				publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FuturesOrderDto buy(FuturesOrderDto orderDto) {
		Response<FuturesOrderDto> response = futuresOrderInterface.addOrder(orderDto);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<FuturesOrderDto> getListFuturesOrderPositionByPublisherId(Long publisherId) {
		Response<List<FuturesOrderDto>> response = futuresOrderInterface
				.getListFuturesOrderPositionByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<FuturesOrderMarketDto> getListFuturesOrderPositionMarket(Long publisherId) {
		List<FuturesOrderDto> orderList = getListFuturesOrderPositionByPublisherId(publisherId);
		List<FuturesOrderMarketDto> futuresOrderMarketList = CopyBeanUtils.copyListBeanPropertiesToList(orderList,
				FuturesOrderMarketDto.class);
		if (futuresOrderMarketList != null && futuresOrderMarketList.size() > 0) {
			for (FuturesOrderMarketDto orderMarket : futuresOrderMarketList) {
				FuturesContractMarket market = RetriveFuturesOverHttp.market(orderMarket.getContractSymbol());
				orderMarket.setLastPrice(market.getLastPrice());
			}
		}
		return futuresOrderMarketList;
	}

	public BigDecimal settlementOrderPositionByPublisherId(Long publisherId) {
		Response<BigDecimal> response = futuresOrderInterface.settlementOrderPositionByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<FuturesOrderDto> getListFuturesOrderEntrustByPublisherId(Long publisherId) {
		Response<List<FuturesOrderDto>> response = futuresOrderInterface
				.getListFuturesOrderEntrustByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<FuturesOrderMarketDto> getListFuturesOrderEntrustMarket(Long publisherId) {
		List<FuturesOrderDto> orderList = getListFuturesOrderEntrustByPublisherId(publisherId);
		List<FuturesOrderMarketDto> futuresOrderMarketList = CopyBeanUtils.copyListBeanPropertiesToList(orderList,
				FuturesOrderMarketDto.class);
		if (futuresOrderMarketList != null && futuresOrderMarketList.size() > 0) {
			for (FuturesOrderMarketDto orderMarket : futuresOrderMarketList) {
				FuturesContractMarket market = RetriveFuturesOverHttp.market(orderMarket.getContractSymbol());
				orderMarket.setLastPrice(market.getLastPrice());
			}
		}
		return futuresOrderMarketList;
	}

	public BigDecimal settlementOrderEntrustByPublisherId(Long publisherId) {
		Response<BigDecimal> response = futuresOrderInterface.settlementOrderEntrustByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<FuturesOrderDto> getListFuturesOrderUnwindByPublisherId(Long publisherId) {
		Response<List<FuturesOrderDto>> response = futuresOrderInterface
				.getListFuturesOrderUnwindByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<FuturesOrderMarketDto> getListFuturesOrderUnwindMarket(Long publisherId) {
		return CopyBeanUtils.copyListBeanPropertiesToList(getListFuturesOrderUnwindByPublisherId(publisherId),
				FuturesOrderMarketDto.class);
	}

	public BigDecimal settlementOrderUnwindByPublisherId(Long publisherId) {
		Response<BigDecimal> response = futuresOrderInterface.settlementOrderUnwindByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
}
