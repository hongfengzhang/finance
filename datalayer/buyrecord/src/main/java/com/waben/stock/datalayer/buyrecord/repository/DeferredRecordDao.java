package com.waben.stock.datalayer.buyrecord.repository;

import com.waben.stock.datalayer.buyrecord.entity.DeferredRecord;

/**
 * 递延记录 Dao
 * 
 * @author luomengan
 *
 */
public interface DeferredRecordDao extends BaseDao<DeferredRecord, Long> {

	DeferredRecord retrieveByPublisherIdAndBuyRecordId(Long publisherId, Long buyRecordId);

}
