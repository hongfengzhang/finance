package com.waben.stock.datalayer.buyrecord.reference.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.datalayer.buyrecord.reference.InvestorReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;

@Component
public class InvestorReferenceFallback implements InvestorReference {

	@Override
	public Response<Integer> modify(InvestorDto investorDto) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public void delete(Long id) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<InvestorDto>> pagesByQuery(InvestorQuery investorQuery) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<InvestorDto> fetchById(Long id) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<InvestorDto> fetchByUserName(String username) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> stockApplyBuyIn(Long investor, SecuritiesStockEntrust securitiesStockEntrust,
			String tradeSession) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> voluntarilyStockApplyBuyIn(Long buyrecode) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> stockApplySellOut(Long investor, SecuritiesStockEntrust securitiesStockEntrust,
			String tradeSession) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<List<InvestorDto>> fetchAllInvestors() {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
