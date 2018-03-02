package com.waben.stock.datalayer.promotion.repository.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.waben.stock.datalayer.promotion.entity.Organization;

/**
 * 机构 Jpa
 * 
 * @author luomengan
 *
 */
public interface OrganizationRepository extends CustomJpaRepository<Organization, Long> {

	List<Organization> findByParent(Organization parent);

	List<Organization> findByParent(Organization parent, Sort sort);

}
