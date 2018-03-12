package com.waben.stock.applayer.promotion.reference.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.OrganizationAccountReferenceFallback;
import com.waben.stock.interfaces.service.organization.OrganizationAccountInterface;

/**
 * 机构账户 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "organizationAccount", fallback = OrganizationAccountReferenceFallback.class, qualifier = "organizationAccountReference")
public interface OrganizationAccountReference extends OrganizationAccountInterface {

}
