package com.waben.stock.collector.dao;

import java.util.List;

import org.springframework.data.domain.Page;

import com.waben.stock.collector.entity.CapitalFlowExtend;

/**
 * 资金流水扩展信息 Dao
 * 
 * @author luomengan
 *
 */
public interface CapitalFlowExtendDao {

	public CapitalFlowExtend createCapitalFlowExtend(CapitalFlowExtend capitalFlowExtend);

	public void deleteCapitalFlowExtendById(Long id);

	public CapitalFlowExtend updateCapitalFlowExtend(CapitalFlowExtend capitalFlowExtend);

	public CapitalFlowExtend retrieveCapitalFlowExtendById(Long id);

	public Page<CapitalFlowExtend> pageCapitalFlowExtend(int page, int limit);
	
	public List<CapitalFlowExtend> listCapitalFlowExtend();
	
	public CapitalFlowExtend getByDomainAndDataId(String domain, Long dataId);

}
