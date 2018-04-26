package com.waben.stock.applayer.admin.business.stockoption;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.admin.reference.StockOptionOrgReference;
import com.waben.stock.applayer.admin.reference.StockOptionTradeReference;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionAdminDto;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionBlacklistAdminDto;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionRiskAdminDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionAmountLimitDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionQuoteDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.stockoption.StockOptionAdminQuery;
import com.waben.stock.interfaces.pojo.query.admin.stockoption.StockOptionRiskAdminQuery;

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

	@Autowired
	@Qualifier("stockOptionOrgReference")
	private StockOptionOrgReference orgReference;

	public List<StockOptionOrgDto> orgList() {
		Response<List<StockOptionOrgDto>> response = orgReference.lists();
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<StockOptionAdminDto> adminPagesByQuery(StockOptionAdminQuery query) {
		Response<PageInfo<StockOptionAdminDto>> response = reference.adminPagesByQuery(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockOptionTradeDto fail(Long id) {
		Response<StockOptionTradeDto> response = reference.fail(id);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockOptionTradeDto turnover(Long id, Long orgid, BigDecimal orgRightMoneyRatio, BigDecimal buyingPrice) {
		Response<StockOptionTradeDto> response = reference.turnover(id, orgid, orgRightMoneyRatio, buyingPrice);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockOptionTradeDto mark(Long id, Boolean isMark) {
		Response<StockOptionTradeDto> response = reference.mark(id, isMark);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockOptionTradeDto settlement(Long id, BigDecimal sellingPrice) {
		Response<StockOptionTradeDto> response = reference.settlement(id, sellingPrice);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<StockOptionRiskAdminDto> adminNormalRiskPagesByQuery(StockOptionRiskAdminQuery query) {
		Response<PageInfo<StockOptionRiskAdminDto>> response = reference.adminNormalRiskPagesByQuery(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<StockOptionRiskAdminDto> adminAbnormalRiskPagesByQuery(StockOptionRiskAdminQuery query) {
		Response<PageInfo<StockOptionRiskAdminDto>> response = reference.adminAbnormalRiskPagesByQuery(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<StockOptionBlacklistAdminDto> adminBlackRiskPagesByQuery(StockOptionRiskAdminQuery query) {
		Response<PageInfo<StockOptionBlacklistAdminDto>> response = reference.adminBlackRiskPagesByQuery(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockOptionAmountLimitDto modifyStockOptionLimit(String stockCode, String stockName, Boolean isGlobal,
			BigDecimal amountLimit) {
		Response<StockOptionAmountLimitDto> response = reference.modifyStockOptionLimit(stockCode, stockName, isGlobal,
				amountLimit);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockOptionQuoteDto modifyStockOptionQuote(String stockCode, String stockName, Integer cycle,
			BigDecimal rightMoneyRatio) {
		Response<StockOptionQuoteDto> response = reference.modifyStockOptionQuote(stockCode, stockName, cycle,
				rightMoneyRatio);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public String deleteStockOptionLimit(String stockCode) {
		Response<String> response = reference.deleteStockOptionLimit(stockCode);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public StockOptionAmountLimitDto fetchGlobalStockOptionLimit() {
		Response<StockOptionAmountLimitDto> response = reference.fetchGlobalStockOptionLimit();
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
