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

	BindCard findByPublisherSerialCodeAndBankCard(String publisherSerialCode, String bankCard);

	List<BindCard> findByPublisherSerialCode(String serialCode);

}
