package com.waben.stock.datalayer.publisher.repository;

import com.waben.stock.datalayer.publisher.entity.CapitalAccount;

/**
 * 资金账号 Dao
 * 
 * @author luomengan
 *
 */
public interface CapitalAccountDao extends BaseDao<CapitalAccount, Long> {

	CapitalAccount findByPublisherSerialCode(String publisherSerialCode);
	
	CapitalAccount findByPublisherId(Long publisherId);

}
