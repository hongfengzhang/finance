package com.waben.stock.datalayer.manage.repository.impl.jpa;

import com.waben.stock.datalayer.manage.entity.BankInfo;

/**
 * 银行信息 Jpa
 * 
 * @author luomengan
 *
 */
public interface BankInfoRepository extends CustomJpaRepository<BankInfo, Long> {

	BankInfo findByBankNameLike(String string);

}
