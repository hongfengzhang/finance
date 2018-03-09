package com.waben.stock.datalayer.promotion.repository.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.waben.stock.datalayer.promotion.entity.BenefitConfig;
import com.waben.stock.datalayer.promotion.entity.Organization;
import com.waben.stock.interfaces.enums.BenefitConfigType;

/**
 * 分成配置 Jpa
 * 
 * @author luomengan
 *
 */
public interface BenefitConfigRepository extends CustomJpaRepository<BenefitConfig, Long> {

	List<BenefitConfig> findByOrgAndResourceType(Organization org, Integer resourceType, Sort sort);

	List<BenefitConfig> findByOrgAndTypeAndResourceTypeAndResourceId(Organization org, BenefitConfigType type,
			Integer resourceType, Long resourceId);

}
