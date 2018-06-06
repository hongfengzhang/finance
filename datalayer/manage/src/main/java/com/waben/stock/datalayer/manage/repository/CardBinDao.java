package com.waben.stock.datalayer.manage.repository;

import com.waben.stock.datalayer.manage.entity.CardBin;
import com.waben.stock.datalayer.manage.entity.Cnaps;

/**
 * 银行卡片 Dao
 * 
 * @author luomengan
 *
 */
public interface CardBinDao extends BaseDao<CardBin, Long> {

	CardBin retrieveByBankCard(String bankCard);

	Cnaps retriveByCnaps(String cnaps);

}
