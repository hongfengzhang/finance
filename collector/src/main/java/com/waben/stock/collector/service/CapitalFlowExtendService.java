package com.waben.stock.collector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.collector.dao.CapitalFlowExtendDao;
import com.waben.stock.collector.entity.CapitalFlowExtend;

/**
 * 资金流水扩展信息 Service
 * 
 * @author luomengan
 *
 */
@Service
public class CapitalFlowExtendService {

	@Autowired
	private CapitalFlowExtendDao capitalFlowExtendDao;

	public CapitalFlowExtend getCapitalFlowExtendInfo(Long id) {
		return capitalFlowExtendDao.retrieveCapitalFlowExtendById(id);
	}

	@Transactional
	public CapitalFlowExtend addCapitalFlowExtend(CapitalFlowExtend capitalFlowExtend) {
		return capitalFlowExtendDao.createCapitalFlowExtend(capitalFlowExtend);
	}

	@Transactional
	public CapitalFlowExtend modifyCapitalFlowExtend(CapitalFlowExtend capitalFlowExtend) {
		return capitalFlowExtendDao.updateCapitalFlowExtend(capitalFlowExtend);
	}

	@Transactional
	public void deleteCapitalFlowExtend(Long id) {
		capitalFlowExtendDao.deleteCapitalFlowExtendById(id);
	}
	
	@Transactional
	public void deleteCapitalFlowExtends(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					capitalFlowExtendDao.deleteCapitalFlowExtendById(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<CapitalFlowExtend> capitalFlowExtends(int page, int limit) {
		return capitalFlowExtendDao.pageCapitalFlowExtend(page, limit);
	}
	
	public List<CapitalFlowExtend> list() {
		return capitalFlowExtendDao.listCapitalFlowExtend();
	}

}
