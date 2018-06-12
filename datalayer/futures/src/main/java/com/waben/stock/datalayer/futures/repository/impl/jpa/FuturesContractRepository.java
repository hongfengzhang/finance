package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.waben.stock.datalayer.futures.entity.FuturesContract;

/**
 * 期货合约 Repository
 * 
 * @author sl
 *
 */
public interface FuturesContractRepository extends CustomJpaRepository<FuturesContract, Long> {

	@Transactional
	@Modifying(clearAutomatically = true)  
	@Query(value = "update FuturesContract sc set sc.enable=?1 where sc.id=?2") 
	int enable(Boolean current,Long id);
	
	@Query("select f from FuturesContract f where f.exchange.id=?1")
	List<FuturesContract> findByExchangId(Long exchangeId);
}
