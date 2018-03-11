package com.waben.stock.datalayer.organization.repository;

import java.util.List;

import com.waben.stock.datalayer.organization.entity.Organization;

/**
 * 机构 Dao
 * 
 * @author luomengan
 *
 */
public interface OrganizationDao extends BaseDao<Organization, Long> {

	List<Organization> listByParent(Organization parent);

	List<Organization> listByParentOrderByCodeDesc(Organization parent);

}
