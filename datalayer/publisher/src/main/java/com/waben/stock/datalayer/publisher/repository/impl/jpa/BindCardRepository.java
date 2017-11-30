package com.waben.stock.datalayer.publisher.repository.impl.jpa;

import java.util.List;

import com.waben.stock.datalayer.publisher.entity.BindCard;

/**
 * 绑卡 Jpa
 * 
 * @author luomengan
 *
 */
public interface BindCardRepository extends CustomJpaRepository<BindCard, Long> {

	List<BindCard> findByPublisherId(Long publisherId);

	BindCard findByPublisherIdAndBankCard(Long publisherId, String bankCard);

}
