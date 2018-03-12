package com.waben.stock.datalayer.buyrecord.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.datalayer.buyrecord.reference.fallback.OrganizationSettlementReferenceFallback;
import com.waben.stock.interfaces.service.organization.OrganizationSettlementInterface;

/**
 * 机构结算 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "organizationSettlement", fallback = OrganizationSettlementReferenceFallback.class, qualifier = "organizationSettlementReference")
public interface OrganizationSettlementReference extends OrganizationSettlementInterface {

}
