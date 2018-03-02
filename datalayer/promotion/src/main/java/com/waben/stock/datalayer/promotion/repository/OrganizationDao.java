package com.waben.stock.datalayer.promotion.repository;

import java.util.List;

import com.waben.stock.datalayer.promotion.entity.Organization;

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
