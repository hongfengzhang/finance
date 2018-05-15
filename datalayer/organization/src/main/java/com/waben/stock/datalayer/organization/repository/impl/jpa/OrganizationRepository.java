package com.waben.stock.datalayer.organization.repository.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.waben.stock.datalayer.organization.entity.Organization;

/**
 * 机构 Jpa
 * 
 * @author luomengan
 *
 */
public interface OrganizationRepository extends CustomJpaRepository<Organization, Long> {

	List<Organization> findByParent(Organization parent);

	List<Organization> findByParent(Organization parent, Sort sort);

	Organization findByCode(String orgCode);

	Organization findOrganizationByName(String orgName);

	List<Organization> findByName(String orgName);

	List<Organization> findByLevel(Integer level);

	Page<Organization> findOrderById(Pageable page);

}
