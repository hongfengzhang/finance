package com.waben.stock.collector.dao.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.waben.stock.collector.entity.CapitalFlowExtend;

/**
 * 资金流水扩展信息 Repository
 * 
 * @author luomengan
 *
 */
public interface CapitalFlowExtendRepository extends Repository<CapitalFlowExtend, Long> {

	CapitalFlowExtend save(CapitalFlowExtend capitalFlowExtend);

	void delete(Long id);

	Page<CapitalFlowExtend> findAll(Pageable pageable);
	
	List<CapitalFlowExtend> findAll();

	CapitalFlowExtend findById(Long id);

	CapitalFlowExtend findByDomainAndDataId(String domain, Long dataId);
	
}
