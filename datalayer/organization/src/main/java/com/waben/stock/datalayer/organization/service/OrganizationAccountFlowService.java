package com.waben.stock.datalayer.organization.service;

import java.util.ArrayList;
import java.util.List;

import com.waben.stock.datalayer.organization.entity.Organization;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.organization.entity.OrganizationAccountFlow;
import com.waben.stock.datalayer.organization.repository.OrganizationAccountFlowDao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	public Page<OrganizationAccountFlow> pagesByQuery(final OrganizationAccountFlowQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<OrganizationAccountFlow> pages = organizationAccountFlowDao.page(new Specification<OrganizationAccountFlow>() {
			@Override
			public Predicate toPredicate(Root<OrganizationAccountFlow> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				Organization organization = new Organization();
				organization.setId(query.getOrgId());
				if (query.getOrgId()!=null){
					predicateList.add(criteriaBuilder.equal(root.get("org"), organization));
				}
				if (!StringUtils.isBlank(query.getFlowNo())){
					predicateList.add(criteriaBuilder.equal(root.get("flowNo"), query.getFlowNo()));
				}
				if (query.getStrategyTypeId()!=null){
					predicateList.add(criteriaBuilder.equal(root.get("resourceType"), query.getStrategyTypeId()));
				}
				return criteriaQuery.getRestriction();
			}
		},pageable);
		return pages;
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
