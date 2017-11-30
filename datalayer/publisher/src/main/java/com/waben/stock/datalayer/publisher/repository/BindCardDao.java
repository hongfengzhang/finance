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

	BindCard retriveByPublisherIdAndBankCard(Long publisherId, String bankCard);

	List<BindCard> listByPublisherId(Long publisherId);

}
