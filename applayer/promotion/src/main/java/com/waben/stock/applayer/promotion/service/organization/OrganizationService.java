package com.waben.stock.applayer.promotion.service.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.service.fallback.OrganizationServiceFallback;
import com.waben.stock.interfaces.service.organization.OrganizationInterface;

/**
 * 机构 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "organization", fallback = OrganizationServiceFallback.class, qualifier = "organizationReference")
public interface OrganizationService extends OrganizationInterface {

}
