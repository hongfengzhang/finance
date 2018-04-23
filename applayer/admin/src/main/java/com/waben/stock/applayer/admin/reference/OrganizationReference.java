package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.OrganizationReferenceFallback;
import com.waben.stock.interfaces.service.organization.OrganizationInterface;

/**
 * 机构 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "organization", fallback = OrganizationReferenceFallback.class, qualifier = "organizationReference")
public interface OrganizationReference extends OrganizationInterface {

}
