package com.waben.stock.datalayer.publisher.repository;

import java.math.BigDecimal;
import java.util.Date;

import com.waben.stock.datalayer.publisher.entity.CapitalFlow;
import com.waben.stock.interfaces.enums.CapitalFlowType;

/**
 * 资金流水 Dao
 * 
 * @author luomengan
 *
 */
public interface CapitalFlowDao extends BaseDao<CapitalFlow, Long> {

	CapitalFlow create(Long publisherId, String publisherSerialCode, CapitalFlowType type, BigDecimal amount,
			Date occurrenceTime);
}
