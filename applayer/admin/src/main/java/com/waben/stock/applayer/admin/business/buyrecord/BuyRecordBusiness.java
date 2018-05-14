package com.waben.stock.applayer.admin.business.buyrecord;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.admin.reference.BuyRecordReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.buyrecord.BuyRecordAdminQuery;

@Service
public class BuyRecordBusiness {

	@Autowired
	@Qualifier("buyRecordReference")
	private BuyRecordReference reference;

	@Autowired
	private StrategyTypeBusiness strategyTypeBusiness;

	public PageInfo<BuyRecordDto> adminPagesByQuery(BuyRecordAdminQuery query) {
		Response<PageInfo<BuyRecordDto>> response = reference.adminPagesByQuery(query);
		String code = response.getCode();
		if ("200".equals(code)) {
			List<BuyRecordDto> content = response.getResult().getContent();
			if (content != null && content.size() > 0) {
				Map<Long, String> strategyMap = strategyTypeBusiness.strategyNameMap();
				for (BuyRecordDto buyRecord : content) {
					buyRecord.setStrategyTypeName(strategyMap.get(buyRecord.getStrategyTypeId()));
				}
			}
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

}
