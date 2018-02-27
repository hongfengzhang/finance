package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.OrganizationCategory;
import com.waben.stock.datalayer.promotion.repository.OrganizationCategoryDao;

/**
 * 机构类别 Service
 * 
 * @author luomengan
 *
 */
@Service
public class OrganizationCategoryService {

	@Autowired
	private OrganizationCategoryDao organizationCategoryDao;

	public OrganizationCategory getOrganizationCategoryInfo(Long id) {
		return organizationCategoryDao.retrieve(id);
	}

	@Transactional
	public OrganizationCategory addOrganizationCategory(OrganizationCategory organizationCategory) {
		return organizationCategoryDao.create(organizationCategory);
	}

	@Transactional
	public OrganizationCategory modifyOrganizationCategory(OrganizationCategory organizationCategory) {
		return organizationCategoryDao.update(organizationCategory);
	}

	@Transactional
	public void deleteOrganizationCategory(Long id) {
		organizationCategoryDao.delete(id);
	}
	
	@Transactional
	public void deleteOrganizationCategorys(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					organizationCategoryDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<OrganizationCategory> organizationCategorys(int page, int limit) {
		return organizationCategoryDao.page(page, limit);
	}
	
	public List<OrganizationCategory> list() {
		return organizationCategoryDao.list();
	}

}
