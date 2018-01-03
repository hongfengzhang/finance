package com.waben.stock.datalayer.buyrecord.business;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.buyrecord.reference.StrategyTypeReference;
import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.pojo.Response;

@Service
public class StrategyTypeBusiness {
	
	@Autowired
    @Qualifier("strategyTypeFeignReference")
	private StrategyTypeReference reference;

	public StrategyTypeDto fetchById(Long strategyTypeId) {
		Response<StrategyTypeDto> response = reference.fetchById(strategyTypeId);
		if("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
	
}
