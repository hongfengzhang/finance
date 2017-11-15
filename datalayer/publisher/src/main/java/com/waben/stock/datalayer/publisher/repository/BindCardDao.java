package com.waben.stock.datalayer.publisher.repository;

import java.util.List;

import com.waben.stock.datalayer.publisher.entity.BindCard;

/**
 * 绑卡 Dao
 * 
 * @author luomengan
 *
 */
public interface BindCardDao extends BaseDao<BindCard, Long> {

	BindCard findByPublisherSerialCodeAndBankCard(String serialCode, String bankCard);

	List<BindCard> findByPublisherSerialCode(String serialCode);

}
