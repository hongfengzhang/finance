package com.waben.stock.collector.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.waben.stock.collector.dao.CapitalFlowExtendDao;
import com.waben.stock.collector.dao.impl.jpa.CapitalFlowExtendRepository;
import com.waben.stock.collector.entity.CapitalFlowExtend;

/**
 * 资金流水扩展信息 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class CapitalFlowExtendDaoImpl implements CapitalFlowExtendDao {

	@Autowired
	private CapitalFlowExtendRepository capitalFlowExtendRepository;

	@Override
	public CapitalFlowExtend createCapitalFlowExtend(CapitalFlowExtend capitalFlowExtend) {
		return capitalFlowExtendRepository.save(capitalFlowExtend);
	}

	@Override
	public void deleteCapitalFlowExtendById(Long id) {
		capitalFlowExtendRepository.delete(id);
	}

	@Override
	public CapitalFlowExtend updateCapitalFlowExtend(CapitalFlowExtend capitalFlowExtend) {
		return capitalFlowExtendRepository.save(capitalFlowExtend);
	}

	@Override
	public CapitalFlowExtend retrieveCapitalFlowExtendById(Long id) {
		return capitalFlowExtendRepository.findById(id);
	}

	@Override
	public Page<CapitalFlowExtend> pageCapitalFlowExtend(int page, int limit) {
		return capitalFlowExtendRepository.findAll(new PageRequest(page, limit));
	}
	
	@Override
	public List<CapitalFlowExtend> listCapitalFlowExtend() {
		return capitalFlowExtendRepository.findAll();
	}

	@Override
	public CapitalFlowExtend getByDomainAndDataId(String domain, Long dataId) {
		return capitalFlowExtendRepository.findByDomainAndDataId(domain, dataId);
	}

}
