package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.OrganizationAccountFlow;
import com.waben.stock.datalayer.promotion.repository.OrganizationAccountFlowDao;

/**
 * 机构账户流水 Service
 * 
 * @author luomengan
 *
 */
@Service
public class OrganizationAccountFlowService {

	@Autowired
	private OrganizationAccountFlowDao organizationAccountFlowDao;

	public OrganizationAccountFlow getOrganizationAccountFlowInfo(Long id) {
		return organizationAccountFlowDao.retrieve(id);
	}

	@Transactional
	public OrganizationAccountFlow addOrganizationAccountFlow(OrganizationAccountFlow organizationAccountFlow) {
		return organizationAccountFlowDao.create(organizationAccountFlow);
	}

	@Transactional
	public OrganizationAccountFlow modifyOrganizationAccountFlow(OrganizationAccountFlow organizationAccountFlow) {
		return organizationAccountFlowDao.update(organizationAccountFlow);
	}

	@Transactional
	public void deleteOrganizationAccountFlow(Long id) {
		organizationAccountFlowDao.delete(id);
	}
	
	@Transactional
	public void deleteOrganizationAccountFlows(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					organizationAccountFlowDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<OrganizationAccountFlow> organizationAccountFlows(int page, int limit) {
		return organizationAccountFlowDao.page(page, limit);
	}
	
	public List<OrganizationAccountFlow> list() {
		return organizationAccountFlowDao.list();
	}

}
